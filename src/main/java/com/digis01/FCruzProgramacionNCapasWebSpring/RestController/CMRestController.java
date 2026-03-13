package com.digis01.FCruzProgramacionNCapasWebSpring.RestController;

import com.digis01.FCruzProgramacionNCapasWebSpring.DAO.UsuarioDAOJPAImplementation;
import com.digis01.FCruzProgramacionNCapasWebSpring.JPA.Colonia;
import com.digis01.FCruzProgramacionNCapasWebSpring.JPA.Direccion;
import com.digis01.FCruzProgramacionNCapasWebSpring.JPA.Result;
import com.digis01.FCruzProgramacionNCapasWebSpring.JPA.Rol;
import com.digis01.FCruzProgramacionNCapasWebSpring.JPA.Usuario;
import com.digis01.FCruzProgramacionNCapasWebSpring.Util.SecurityHelper;
import com.digis01.FCruzProgramacionNCapasWebSpring.JPA.ErroresArchivo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("ApiCM")
@Tag(name = "Carga Masiva", description = "Servicios para la carga masiva de usuarios mediante archivos TXT o Excel")
public class CMRestController {
    
    @Autowired
    private Validator validator;
    @Autowired
    private UsuarioDAOJPAImplementation usuarioDAOJPAImplementation;
    
    @Operation(
        summary = "Validar archivo de carga masiva",
        description = "Recibe un archivo TXT o Excel con usuarios, valida su estructura y datos, y devuelve una llave (key) para procesarlo posteriormente si no existen errores"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Archivo validado correctamente"),
        @ApiResponse(responseCode = "400", description = "Archivo inválido"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping("/validar")
    public Result validarYRegistrar(@RequestParam("archivo") MultipartFile archivo) {
        Result result = new Result();
        List<ErroresArchivo> listaErrores = new ArrayList<>();
        
        try {
            String rutaBase = System.getProperty("user.home") + File.separator + "Documents" + File.separator + "LogCM";
            File carpeta = new File(rutaBase);
            if (!carpeta.exists()) carpeta.mkdirs();

            String nombreUnico = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")) + "_" + archivo.getOriginalFilename();
            File destino = new File(carpeta, nombreUnico);
            archivo.transferTo(destino);

            List<Usuario> usuarios = procesarLectura(destino);
            
            listaErrores = validarDatos(usuarios);
            
            boolean esValido = listaErrores.isEmpty();
            String status = esValido ? "VALIDO" : "INVALIDO";
            
            String key = esValido ? SecurityHelper.encriptarAES(destino.getAbsolutePath()) : "-";

            registrarEnLog(key, destino.getAbsolutePath(), status);

            result.correct = esValido;
            result.object = key; 
            result.objects = new ArrayList<>(listaErrores);

        } catch (Exception e) {
            result.correct = false;
            result.errorMessage = e.getMessage();
        }
        return result;
    }

    private void registrarEnLog(String key, String ruta, String status) {
        String logRuta = System.getProperty("user.home") + File.separator + "Documents" + File.separator + "LogCM" + File.separator + "LOG_CargaMasica.txt";
        String fecha = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String linea = key + "|" + ruta + "|" + status + "|" + fecha + "|Carga inicial realizada";

        try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(logRuta, true)))) {
            out.println(linea);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    
    private List<Usuario> procesarLectura(File file) {
        String nombre = file.getName().toLowerCase();
        if (nombre.endsWith(".txt")) {
            return LecturaArchivoTxt(file);
        } else if (nombre.endsWith(".xlsx") || nombre.endsWith(".xls")) {
            return LecturaArchivoExcel(file);
        }
        return new ArrayList<>();
    }

    public List<Usuario> LecturaArchivoTxt(File file) {
        List<Usuario> usuarios = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            String linea;
            while ((linea = bufferedReader.readLine()) != null) {
                if (linea.trim().isEmpty()) continue;
                String[] datos = linea.split("\\|");
                if (datos.length < 16) continue;

                Usuario usuario = new Usuario();
                usuario.setNombre(datos[0].trim());
                usuario.setApellidoPaterno(datos[1].trim());
                usuario.setApellidosMaterno(datos[2].trim());
                usuario.setEmail(datos[3].trim());

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                usuario.setFechaNacimiento(sdf.parse(datos[4].trim()));

                usuario.setTelefono(datos[5].trim());
                usuario.setCelular(datos[6].trim());
                usuario.setUserName(datos[7].trim());
                usuario.setSexo(datos[8].trim());
                usuario.setPassword(datos[9].trim());
                usuario.setCURP(datos[10].trim());

                Rol rol = new Rol();
                rol.setIdRol(Integer.parseInt(datos[11].trim()));
                usuario.setRol(rol);

                Direccion direccion = new Direccion();
                direccion.setCalle(datos[12].trim());
                direccion.setNumeroExterior(datos[13].trim());
                direccion.setNumeroIInteriori(datos[14].trim());

                Colonia colonia = new Colonia();
                colonia.setIdColonia(Integer.parseInt(datos[15].trim()));
                direccion.setColonia(colonia);

                usuario.setDireccion(new ArrayList<>());
                usuario.getDireccion().add(direccion);
                usuarios.add(usuario);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return usuarios;
    }

    public List<Usuario> LecturaArchivoExcel(File file) {
        List<Usuario> usuarios = new ArrayList<>();
        try (Workbook workbook = new XSSFWorkbook(file)) {
            Sheet sheet = workbook.getSheetAt(0);
            DataFormatter formatter = new DataFormatter();
            for (int i = 1; i <= sheet.getLastRowNum(); i++) { // Empezamos en 1 para saltar encabezado
                Row row = sheet.getRow(i);
                if (row == null) continue;

                Usuario usuario = new Usuario();
                usuario.setNombre(formatter.formatCellValue(row.getCell(0)).trim());
                usuario.setApellidoPaterno(formatter.formatCellValue(row.getCell(1)).trim());
                usuario.setApellidosMaterno(formatter.formatCellValue(row.getCell(2)).trim());
                usuario.setEmail(formatter.formatCellValue(row.getCell(3)).trim());

                Cell celdaFecha = row.getCell(4);
                if (celdaFecha != null) {
                    if (celdaFecha.getCellType() == CellType.NUMERIC) {
                        usuario.setFechaNacimiento(celdaFecha.getDateCellValue());
                    } else {
                        usuario.setFechaNacimiento(new SimpleDateFormat("dd/MM/yyyy").parse(formatter.formatCellValue(celdaFecha)));
                    }
                }

                usuario.setTelefono(formatter.formatCellValue(row.getCell(5)).trim());
                usuario.setCelular(formatter.formatCellValue(row.getCell(6)).trim());
                usuario.setUserName(formatter.formatCellValue(row.getCell(7)).trim());
                usuario.setSexo(formatter.formatCellValue(row.getCell(8)).trim());
                usuario.setPassword(formatter.formatCellValue(row.getCell(9)).trim());
                usuario.setCURP(formatter.formatCellValue(row.getCell(10)).trim());

                Rol rol = new Rol();
                rol.setIdRol(Integer.parseInt(formatter.formatCellValue(row.getCell(11))));
                usuario.setRol(rol);

                Direccion direccion = new Direccion();
                direccion.setCalle(formatter.formatCellValue(row.getCell(12)).trim());
                direccion.setNumeroExterior(formatter.formatCellValue(row.getCell(13)).trim());
                direccion.setNumeroIInteriori(formatter.formatCellValue(row.getCell(14)).trim());

                Colonia colonia = new Colonia();
                colonia.setIdColonia(Integer.parseInt(formatter.formatCellValue(row.getCell(15))));
                direccion.setColonia(colonia);

                usuario.setDireccion(new ArrayList<>());
                usuario.getDireccion().add(direccion);
                usuarios.add(usuario);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return usuarios;
    }

    public List<ErroresArchivo> validarDatos(List<Usuario> usuarios) {
        List<ErroresArchivo> errores = new ArrayList<>();
        int numeroLinea = 2; 

        for (Usuario usuario : usuarios) {
            Set<ConstraintViolation<Usuario>> violaciones = validator.validate(usuario);

            for (ConstraintViolation<Usuario> violacion : violaciones) {
                ErroresArchivo error = new ErroresArchivo();
                error.setFila(numeroLinea);
                error.setDato(violacion.getPropertyPath().toString());
                error.setDescripcion(violacion.getMessage());
                errores.add(error);
            }
            numeroLinea++;
        }
        return errores;
    }
    
    @Operation(
        summary = "Procesar archivo validado",
        description = "Recibe la llave generada en la validación del archivo y registra los usuarios en la base de datos"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Archivo procesado correctamente"),
        @ApiResponse(responseCode = "404", description = "Archivo no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error al procesar el archivo")
    })
    @GetMapping("/procesar/{key}")
    public Result procesarArchivo(@PathVariable String key) {

        key = URLDecoder.decode(key, StandardCharsets.UTF_8);

        Result result = new Result();

        int correctos = 0;
        int incorrectos = 0;

        try {

            String rutaArchivo = SecurityHelper.desencriptarAES(key);

            File file = new File(rutaArchivo);

            List<Usuario> usuarios = procesarLectura(file);

            for (Usuario usuario : usuarios) {

                Result resultSet = usuarioDAOJPAImplementation.AddCM(usuario);

                if (resultSet.correct) {
                    correctos++;
                } else {
                    incorrectos++;
                }
            }

            result.correct = true;
            result.correctos = correctos;
            result.incorrectos = incorrectos;

        } catch (Exception e) {

            result.correct = false;
            result.errorMessage = e.getMessage();

        }

        return result;
    }
    


    
}
