package com.digis01.FCruzProgramacionNCapasWebSpring.RestController;

import com.digis01.FCruzProgramacionNCapasWebSpring.DAO.UsuarioDAOJPAImplementation;

import com.digis01.FCruzProgramacionNCapasWebSpring.JPA.Result;
import com.digis01.FCruzProgramacionNCapasWebSpring.JPA.Usuario;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.Base64;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("Api/Usuario")
@Tag(name = "Usuario",description = "API REST para la gestión de usuarios del sistema. Permite registrar, consultar, actualizar, eliminar y administrar el estado y fotografía de los usuarios."
)public class UserRestController {
    
    @Autowired
    private UsuarioDAOJPAImplementation usuarioDAOJPAImplementation;
    
    @Operation(
        summary = "Obtener todos los usuarios",
        description = "Devuelve una lista completa de los usuarios registrados en el sistema junto con su información básica."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Lista de usuarios obtenida correctamente",
            content = @Content(
                mediaType = "application/json",
                array = @ArraySchema(schema = @Schema(implementation = Usuario.class))
            )
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Error interno al obtener los usuarios",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(example = "{\"error\":\"Error al consultar la base de datos\"}")
            )
        )
    })
    @GetMapping
    @PreAuthorize("hasRole('Administrador')")
    public ResponseEntity<Object> obtenerUsuarios() {
        Result result = usuarioDAOJPAImplementation.GetAll();

        if (result.correct) {
            return ResponseEntity.ok(result.objects);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result.errorMessage);
        }
    }
    
    @Operation(
        summary = "Registrar un nuevo usuario",
        description = "Crea un registro de usuario en el sistema. Permite adjuntar una imagen de perfil que se procesa y almacena en formato Base64 dentro del objeto usuario."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "201", 
            description = "Usuario creado exitosamente",
            content = @Content(schema = @Schema(implementation = Result.class))
        ),
        @ApiResponse(
            responseCode = "400", 
            description = "Datos de entrada inválidos o formato de imagen no soportado",
            content = @Content(schema = @Schema(implementation = Result.class))
        ),
        @ApiResponse(
            responseCode = "500", 
            description = "Error interno al procesar el registro o la conversión de la imagen",
            content = @Content(schema = @Schema(implementation = Result.class))
        )
    })
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Result> Add(
            @Parameter(description = "Objeto JSON con la información del usuario (Nombre, Email, etc.)", required = true)
            @RequestPart("datos") Usuario usuario,
            @Parameter(description = "Archivo de imagen (jpg, png) para el perfil del usuario")
            @RequestPart(name = "imagen", required = false) MultipartFile imagen) {
        
        Result result = new Result();
        try {
            if (imagen != null && !imagen.isEmpty()) {
                byte[] bytes = imagen.getBytes();
                String base64 = Base64.getEncoder().encodeToString(bytes);
                usuario.setFoto("data:" + imagen.getContentType() + ";base64," + base64);
            }

            result = usuarioDAOJPAImplementation.Add(usuario);

            return ResponseEntity.ok(result);

        } catch (Exception e) {
            result.correct = false;
            result.errorMessage = "Error en el servidor: " + e.getMessage();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
        }
    }
    
    @Operation(
        summary = "Consultar usuario por ID",
        description = "Busca un usuario específico en la base de datos utilizando su identificador único proporcionado en la URL."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Registro localizado con éxito",
            content = @Content(mediaType = "application/json", 
            schema = @Schema(implementation = Usuario.class))
        ),
        @ApiResponse(
            responseCode = "404", 
            description = "No se encontró un usuario con el ID proporcionado",
            content = @Content(mediaType = "application/json",
            schema = @Schema(example = "{\"correct\": false, \"errorMessage\": \"El ID no existe\"}"))
        ),
        @ApiResponse(
            responseCode = "500", 
            description = "Error inesperado al consultar el registro",
            content = @Content(schema = @Schema(implementation = Result.class))
        )
    })
    @GetMapping("/GetById/{idUsuario}")
    public ResponseEntity ObtenerUsuarioById(
            @Parameter(description = "Identificador numérico del usuario", example = "5")
            @PathVariable int idUsuario) {

        Result result = usuarioDAOJPAImplementation.GetById(idUsuario);

        if (result.correct) {
            return ResponseEntity.ok(result.object);
        } else {
            return ResponseEntity.status(404).body(result.errorMessage);
        }
    }
    
    @Operation(
        summary = "Búsqueda avanzada de usuarios",
        description = "Permite realizar búsquedas dinámicas filtrando por múltiples atributos del usuario (ej. nombre, apellido, rol). Si un campo se envía vacío, se ignorará en el filtro."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Búsqueda realizada con éxito",
            content = @Content(mediaType = "application/json", 
            array = @ArraySchema(schema = @Schema(implementation = Usuario.class)))
        ),
        @ApiResponse(
            responseCode = "204", 
            description = "No se encontraron coincidencias para los filtros proporcionados"
        ),
        @ApiResponse(
            responseCode = "500", 
            description = "Error interno durante el procesamiento de la consulta dinámica",
            content = @Content(mediaType = "application/json", 
            schema = @Schema(implementation = Result.class))
        )
    })
    @PostMapping("/GetByFilter")
    public ResponseEntity<?> GetByFilter(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Criterios de búsqueda. Envíe solo los campos que desea filtrar.",
                required = true,
                content = @Content(schema = @Schema(implementation = Usuario.class))
            )
            @RequestBody Usuario usuario){

        Result result = usuarioDAOJPAImplementation.GetByFilter(usuario);

        if(result.correct){
            return ResponseEntity.ok(result.objects);
        }else{
            return ResponseEntity.status(500).body(result.errorMessage);
        }
    }
    
    @Operation(
        summary = "Consultar detalle integral del usuario",
        description = "Proporciona un objeto de transferencia 'Result' que contiene la entidad Usuario y metadatos de la operación. Útil para integraciones que requieren validación lógica en el cuerpo de la respuesta."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Respuesta procesada (verificar campo 'correct' para éxito lógico)",
            content = @Content(mediaType = "application/json", 
            schema = @Schema(implementation = Result.class))
        )
    })
    @GetMapping("/Detalle/{id}")
    public Result detalleUsuario(
            @Parameter(description = "Identificador único del usuario", example = "10", required = true)
            @PathVariable int id){

        Result result = usuarioDAOJPAImplementation.GetById(id);

        return result;
    }
    
    @Operation(
        summary = "Actualización completa de usuario",
        description = "Sincroniza el estado de un usuario existente en la base de datos con la información proporcionada. Es obligatorio incluir el ID del usuario dentro del cuerpo de la petición."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Proceso de actualización finalizado (Verificar campo 'correct' para confirmar la persistencia)",
            content = @Content(mediaType = "application/json", 
            schema = @Schema(implementation = Result.class))
        ),
        @ApiResponse(
            responseCode = "400", 
            description = "Datos de entrada mal formados o inconsistentes",
            content = @Content(schema = @Schema(hidden = true))
        )
    })
    @PutMapping("/Update")
    public Result Update(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Estructura del usuario con los datos actualizados. El campo idUsuario es obligatorio.",
                required = true,
                content = @Content(schema = @Schema(implementation = Usuario.class))
            )
            @RequestBody Usuario usuario){
        return usuarioDAOJPAImplementation.Update(usuario);
    }
    
    @Operation(
        summary = "Actualizar fotografía de perfil",
        description = "Permite reemplazar la imagen de un usuario existente. El archivo se procesa, se valida el formato y se almacena como una cadena Base64. Formatos soportados: JPEG y PNG."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Operación procesada",
            content = @Content(mediaType = "application/json", 
            schema = @Schema(implementation = Result.class))
        ),
        @ApiResponse(
            responseCode = "500", 
            description = "Error crítico en el servidor o durante la lectura del archivo",
            content = @Content(mediaType = "application/json", 
            schema = @Schema(implementation = Result.class))
        )
    })
    @PostMapping("/Foto/{id}")
    public Result actualizarFoto(
            @Parameter(description = "ID del usuario a quien se le actualizará la foto", example = "1", required = true)
            @PathVariable int id,
            @Parameter(description = "Archivo binario de la imagen")
            @RequestParam("imagen") MultipartFile foto) {
        
        Result result = new Result();
        try {
            if (foto.isEmpty()) {
                result.correct = false;
                result.errorMessage = "El archivo está vacío";
                return result;
            }

            String tipo = foto.getContentType();
            if ("image/jpeg".equals(tipo) || "image/png".equals(tipo)) {

                byte[] bytes = foto.getBytes();
                String base64 = Base64.getEncoder().encodeToString(bytes);

                String imagenFinal = "data:" + tipo + ";base64," + base64;

                return usuarioDAOJPAImplementation.UpdateFoto(id, imagenFinal);
            } else {
                result.correct = false;
                result.errorMessage = "Formato no permitido. Solo JPG y PNG.";
            }

        } catch (Exception e) {
            result.correct = false;
            result.errorMessage = "Error al procesar la imagen: " + e.getMessage();
            result.ex = e;
        }
        return result;
    }
    
    @Operation(
        summary = "Eliminación de usuario",
        description = "Remueve permanentemente el registro del usuario de la base de datos utilizando su identificador único."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Operación procesada",
            content = @Content(mediaType = "application/json", 
            schema = @Schema(implementation = Result.class))
        )
    })
    @DeleteMapping("/{idUsuario}")
    public Result Eliminar(
            @Parameter(description = "ID del usuario a eliminar", example = "15", required = true)
            @PathVariable int idUsuario) {
        return usuarioDAOJPAImplementation.Delete(idUsuario);
    }
    
    @Operation(
        summary = "Cambiar estado de activación",
        description = "Permite modificar el estado operativo del usuario (Ej: Activo/Inactivo) sin eliminar el registro."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Estado actualizado exitosamente",
            content = @Content(mediaType = "application/json", 
            schema = @Schema(implementation = Result.class))
        ),
        @ApiResponse(
            responseCode = "400", 
            description = "Valor de status no permitido"
        )
    })
    @PutMapping("/UpdateStatus/{idUsuario}/{status}")
    public Result updateStatus(
            @Parameter(description = "ID del usuario a modificar", example = "1", required = true)
            @PathVariable int idUsuario,
            @Parameter(description = "Nuevo estado (1: Activo, 0: Inactivo)", example = "1", required = true)
            @PathVariable int status) {
        
        return usuarioDAOJPAImplementation.UpdateStatus(idUsuario, status);
    }
    
    
}
