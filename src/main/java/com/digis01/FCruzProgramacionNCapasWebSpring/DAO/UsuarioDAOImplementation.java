package com.digis01.FCruzProgramacionNCapasWebSpring.DAO;

import com.digis01.FCruzProgramacionNCapasWebSpring.JPA.Colonia;
import com.digis01.FCruzProgramacionNCapasWebSpring.JPA.Direccion;
import com.digis01.FCruzProgramacionNCapasWebSpring.JPA.Estado;
import com.digis01.FCruzProgramacionNCapasWebSpring.JPA.Municipio;
import com.digis01.FCruzProgramacionNCapasWebSpring.JPA.Pais;
import com.digis01.FCruzProgramacionNCapasWebSpring.JPA.Result;
import com.digis01.FCruzProgramacionNCapasWebSpring.JPA.Rol;
import com.digis01.FCruzProgramacionNCapasWebSpring.JPA.Usuario;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class UsuarioDAOImplementation implements IUsuarioJPA {
    
    @Autowired
    private JdbcTemplate JdbcTemplate;
    
    @Override
    public Result GetAll(){
        Result result = new Result();
        
        JdbcTemplate.execute("{CALL UsuarioGetAll(?)}", (CallableStatementCallback<Boolean>) callableStament ->{
        
            callableStament.registerOutParameter(1, java.sql.Types.REF_CURSOR);
            callableStament.execute();
            
            ResultSet resultSet = (ResultSet) callableStament.getObject(1);
            result.objects = new ArrayList<>();
            
            while(resultSet.next()){
                int idUsuario = resultSet.getInt("idUsuario");
                if (!result.objects.isEmpty() && idUsuario == ((Usuario) (result.objects.get(result.objects.size() - 1))).getIdUsuario()){
                    
                    //Se agrega las direccion condo la lista no es vacia y el id concide con un usuario que ya esta en la lista
                    Direccion direccion = new Direccion();
                    direccion.setIdDireccion(resultSet.getInt("IdDireccion"));
                    direccion.setCalle(resultSet.getString("Calle"));
                    direccion.setNumeroExterior(resultSet.getString("NumeroExterior"));
                    direccion.setNumeroIInteriori(resultSet.getString("NumeroIInteriori"));

                    Colonia colonia = new Colonia();
                    colonia.setIdColonia(resultSet.getInt("IdColonia"));
                    colonia.setNombre(resultSet.getString("NombreColonia"));
                    colonia.setCodigoPostal(resultSet.getString("CodigoPostal"));

                    Municipio municipio = new Municipio();
                    municipio.setIdMunicipio(resultSet.getInt("IdMunicipio"));
                    municipio.setNombre(resultSet.getString("NombreMunicipio"));

                    Estado estado = new Estado();
                    estado.setIdEstado(resultSet.getInt("IdEstado"));
                    estado.setNombre(resultSet.getString("NombreEstado"));

                    Pais pais = new Pais();
                    pais.setIdPais(resultSet.getInt("IdPais"));
                    pais.setNombre(resultSet.getString("NombrePais"));

                    estado.setPais(pais);
                    municipio.setEstado(estado);
                    colonia.setMunicipio(municipio);
                    direccion.setColonia(colonia);
                    
                    ((Usuario)(result.objects.get(result.objects.size()-1))).direccion.add(direccion);
                }else{
                    
                    Usuario usuario = new Usuario();
                    usuario.setIdUsuario(resultSet.getInt("IdUsuario"));
                    usuario.setSexo(resultSet.getString("Sexo"));
                    usuario.setNombre(resultSet.getString("NombreUsuario"));
                    usuario.setApellidoPaterno(resultSet.getString("ApellidoPaterno"));
                    usuario.setApellidosMaterno(resultSet.getString("ApellidosMaterno"));
                    usuario.setUserName(resultSet.getString("UserName"));
                    usuario.setPassword(resultSet.getString("Password"));
                    usuario.setCURP(resultSet.getString("CURP"));
                    usuario.setEmail(resultSet.getString("Email"));
                    usuario.setFechaNacimiento(resultSet.getDate("FechaNacimiento"));
                    usuario.setCelular(resultSet.getString("Celular"));
                    usuario.setTelefono(resultSet.getString("Telefono"));
                    usuario.setFoto(resultSet.getString("Foto"));
                    usuario.setStatus(resultSet.getInt("Status"));
                    
                    usuario.Rol = new Rol();
                    usuario.Rol.setIdRol(resultSet.getInt("IdRol"));
                    usuario.Rol.setNombreRol(resultSet.getString("NombreRol"));
                    
                    
                    Direccion direccion = new Direccion();
                    direccion.setIdDireccion(resultSet.getInt("IdDireccion"));
                    direccion.setCalle(resultSet.getString("Calle"));
                    direccion.setNumeroExterior(resultSet.getString("NumeroExterior"));
                    direccion.setNumeroIInteriori(resultSet.getString("NumeroIInteriori"));

                    Colonia colonia = new Colonia();
                    colonia.setIdColonia(resultSet.getInt("IdColonia"));
                    colonia.setNombre(resultSet.getString("NombreColonia"));
                    colonia.setCodigoPostal(resultSet.getString("CodigoPostal"));

                    Municipio municipio = new Municipio();
                    municipio.setIdMunicipio(resultSet.getInt("IdMunicipio"));
                    municipio.setNombre(resultSet.getString("NombreMunicipio"));

                    Estado estado = new Estado();
                    estado.setIdEstado(resultSet.getInt("IdEstado"));
                    estado.setNombre(resultSet.getString("NombreEstado"));

                    Pais pais = new Pais();
                    pais.setIdPais(resultSet.getInt("IdPais"));
                    pais.setNombre(resultSet.getString("NombrePais"));
                    
                    

                    estado.setPais(pais);
                    municipio.setEstado(estado);
                    colonia.setMunicipio(municipio);
                    direccion.setColonia(colonia);

                    usuario.getDireccion().add(direccion);

                    
                    result.objects.add(usuario);
                    
                }
            }
            return true;
        });
        return result;
    }
    
    @Override
    public Result Add(Usuario usuario) {

        Result result = new Result();

        try {

            JdbcTemplate.update("CALL UsuarioDireccionAddSP(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)",

                usuario.getNombre(),
                usuario.getApellidoPaterno(),
                usuario.getApellidosMaterno(),
                usuario.getEmail(),
                usuario.getPassword(),
                usuario.getSexo(),
                usuario.getTelefono(),
                usuario.getCelular(),
                usuario.getCURP(),
                usuario.getUserName(),
                usuario.getFechaNacimiento(),
                usuario.getRol().getIdRol(),
                usuario.getFoto(),

                usuario.getDireccion().get(0).getCalle(),
                usuario.getDireccion().get(0).getNumeroIInteriori(),
                usuario.getDireccion().get(0).getNumeroExterior(),
                usuario.getDireccion().get(0).getColonia().getIdColonia()
            );

            result.correct = true;

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getMessage();
            ex.printStackTrace();
        }

        return result;
    }
    
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result AddAll(List<Usuario> usuarios) {
        Result result = new Result();

        try {
            JdbcTemplate.batchUpdate("CALL UsuarioDireccionAddSP(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
                usuarios,
                usuarios.size(),
                (ps, usuario) -> {
                    ps.setString(1, usuario.getNombre());
                    ps.setString(2, usuario.getApellidoPaterno());
                    ps.setString(3, usuario.getApellidosMaterno());
                    ps.setString(4, usuario.getEmail());
                    ps.setString(5, usuario.getPassword());
                    ps.setString(6, usuario.getSexo());
                    ps.setString(7, usuario.getTelefono());
                    ps.setString(8, usuario.getCelular());
                    ps.setString(9, usuario.getCURP());
                    ps.setString(10, usuario.getUserName());

                    if (usuario.getFechaNacimiento() != null) {
                        ps.setDate(11, new java.sql.Date(usuario.getFechaNacimiento().getTime()));
                    } else {
                        ps.setNull(11, java.sql.Types.DATE);
                    }

                    ps.setInt(12, usuario.getRol().getIdRol());
                    ps.setString(13, usuario.getFoto());
                    ps.setString(14, usuario.getDireccion().get(0).getCalle());
                    ps.setObject(15, usuario.getDireccion().get(0).getNumeroIInteriori(), java.sql.Types.NUMERIC);
                    ps.setObject(16, usuario.getDireccion().get(0).getNumeroExterior(), java.sql.Types.NUMERIC);

                    ps.setInt(17, usuario.getDireccion().get(0).getColonia().getIdColonia());
                });

            result.correct = true;

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = "Ocurrió un error de base de datos al registrar los usuarios. Verifique los datos.";
            System.err.println("Error técnico: " + ex.getMessage());
        }

        return result;
    }
    
    @Override
    public Result Update(Usuario usuario) {

        Result result = new Result();

        try {

            JdbcTemplate.update("CALL UsuarioUpdateSP(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                    usuario.getIdUsuario(),
                    usuario.getUserName(),
                    usuario.getNombre(),
                    usuario.getApellidoPaterno(),
                    usuario.getApellidosMaterno(),
                    usuario.getFechaNacimiento(),
                    usuario.getPassword(),
                    usuario.getEmail(),
                    usuario.getSexo(),
                    usuario.getTelefono(),
                    usuario.getCelular(),
                    usuario.getCURP(),
                    usuario.getRol().getIdRol()
            );

            result.correct = true;

        } catch (Exception ex) {

            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }

        return result;
    }
    
    @Override
    public Result UpdateFoto(int idUsuario, String foto) {

        Result result = new Result();

        try {

            JdbcTemplate.update("CALL UsuarioUpdateFotoSP(?,?)",idUsuario,foto);

            result.correct = true;

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getMessage();
        }

        return result;
    }

    @Override
    public Result Delete(int idUsuario) {

        Result result = new Result();

        try {

            JdbcTemplate.update("CALL UsuarioDeleteSP(?)", idUsuario);

            result.correct = true;

        } catch (Exception ex) {

            result.correct = false;
            result.errorMessage = ex.getMessage();
        }

        return result;
    }
    
    

    @Override
    public Result GetById(int idUsuario) {
        Result result = new Result();
        try {
            JdbcTemplate.execute("{CALL UsuarioGetByIdSP(?, ?)}", (CallableStatementCallback<Boolean>) callableStament -> {
                callableStament.setInt(1, idUsuario);
                callableStament.registerOutParameter(2, java.sql.Types.REF_CURSOR);
                callableStament.execute();

                ResultSet rs = (ResultSet) callableStament.getObject(2);
                if (rs.next()) {
                    Usuario usuario = new Usuario();
                    usuario.setIdUsuario(rs.getInt("IdUsuario"));
                    usuario.setNombre(rs.getString("Nombreusuario"));
                    usuario.setApellidoPaterno(rs.getString("ApellidoPaterno"));
                    usuario.setApellidosMaterno(rs.getString("ApellidosMaterno"));
                    usuario.setUserName(rs.getString("Username"));
                    usuario.setEmail(rs.getString("Email"));
                    usuario.setSexo(rs.getString("Sexo"));
                    usuario.setCURP(rs.getString("CURP"));
                    usuario.setTelefono(rs.getString("Telefono"));
                    usuario.setCelular(rs.getString("Celular"));
                    usuario.setStatus(rs.getInt("Status"));

                    Rol rol = new Rol();
                    rol.setIdRol(rs.getInt("idrol"));
                    rol.setNombreRol(rs.getString("NombreROl"));
                    usuario.setRol(rol);

                    result.object = usuario; // Guardamos el usuario encontrado
                    result.correct = true;
                } else {
                    result.correct = false;
                    result.errorMessage = "No se encontró el usuario";
                }
                return true;
            });
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getMessage();
        }
        return result;
    }
    
    @Override
    public Result GetByFilter(Usuario usuarioBusqueda) {
        Result result = new Result();

        try {
            result.objects = JdbcTemplate.execute("{CALL BuscarUsuariosSP(?, ?, ?, ?, ?)}", (CallableStatementCallback<ArrayList<Object>>) callableStament -> {

                callableStament.setString(1, (usuarioBusqueda.getNombre() == null || usuarioBusqueda.getNombre().trim().isEmpty()) ? null : usuarioBusqueda.getNombre());
                callableStament.setString(2, (usuarioBusqueda.getApellidoPaterno() == null || usuarioBusqueda.getApellidoPaterno().trim().isEmpty()) ? null : usuarioBusqueda.getApellidoPaterno());
                callableStament.setString(3, (usuarioBusqueda.getApellidosMaterno() == null || usuarioBusqueda.getApellidosMaterno().trim().isEmpty()) ? null : usuarioBusqueda.getApellidosMaterno());

                if (usuarioBusqueda.getRol() != null && usuarioBusqueda.getRol().getIdRol() != null) {

                    if (usuarioBusqueda.getRol().getIdRol() > 0) {
                        callableStament.setInt(4, usuarioBusqueda.getRol().getIdRol());
                    } else {
                        callableStament.setNull(4, java.sql.Types.NUMERIC);
                    }

                } else {
                    callableStament.setNull(4, java.sql.Types.NUMERIC);
                }
                
                callableStament.registerOutParameter(5, java.sql.Types.REF_CURSOR);

                callableStament.execute();

                try (ResultSet resultSet = (ResultSet) callableStament.getObject(5)) {
                    return mapResultSetToUsuarios(resultSet);
                }
            });

            result.correct = true;

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = "Error en DAO: " + ex.getMessage();
            ex.printStackTrace();
        }

        return result;
    }

    private ArrayList<Object> mapResultSetToUsuarios(ResultSet resultSet) throws java.sql.SQLException {
        ArrayList<Object> usuarios = new ArrayList<>();

        while (resultSet.next()) {
            int idUsuarioActual = resultSet.getInt("IdUsuario");

            if (!usuarios.isEmpty() && idUsuarioActual == ((Usuario) usuarios.get(usuarios.size() - 1)).getIdUsuario()) {

                Usuario usuarioExistente = (Usuario) usuarios.get(usuarios.size() - 1);
                usuarioExistente.getDireccion().add(crearDireccionDesdeRS(resultSet));

            } else {
                Usuario usuario = new Usuario();
                usuario.setIdUsuario(idUsuarioActual);
                usuario.setNombre(resultSet.getString("NombreUsuario"));
                usuario.setApellidoPaterno(resultSet.getString("ApellidoPaterno"));
                usuario.setApellidosMaterno(resultSet.getString("ApellidosMaterno"));
                usuario.setUserName(resultSet.getString("UserName"));
                usuario.setEmail(resultSet.getString("Email"));
                usuario.setSexo(resultSet.getString("Sexo"));
                usuario.setCURP(resultSet.getString("CURP"));
                usuario.setTelefono(resultSet.getString("Telefono"));
                usuario.setCelular(resultSet.getString("Celular"));
                usuario.setFechaNacimiento(resultSet.getDate("FechaNacimiento"));
                usuario.setFoto(resultSet.getString("Foto"));

                usuario.Rol = new Rol();
                usuario.Rol.setIdRol(resultSet.getInt("IdRol"));
                usuario.Rol.setNombreRol(resultSet.getString("NombreRol"));

                usuario.setDireccion(new ArrayList<>());
                usuario.getDireccion().add(crearDireccionDesdeRS(resultSet));

                usuarios.add(usuario);
            }
        }
        return usuarios;
    }

    private Direccion crearDireccionDesdeRS(ResultSet rs) throws java.sql.SQLException {
        Direccion direccion = new Direccion();
        direccion.setIdDireccion(rs.getInt("IdDireccion"));
        direccion.setCalle(rs.getString("Calle"));
        direccion.setNumeroExterior(rs.getString("NumeroExterior"));
        direccion.setNumeroIInteriori(rs.getString("NumeroIInteriori"));

        Colonia colonia = new Colonia();
        colonia.setIdColonia(rs.getInt("IdColonia"));
        colonia.setNombre(rs.getString("NombreColonia"));
        colonia.setCodigoPostal(rs.getString("CodigoPostal"));

        Municipio municipio = new Municipio();
        municipio.setIdMunicipio(rs.getInt("IdMunicipio"));
        municipio.setNombre(rs.getString("NombreMunicipio"));

        Estado estado = new Estado();
        estado.setIdEstado(rs.getInt("IdEstado"));
        estado.setNombre(rs.getString("NombreEstado"));

        Pais pais = new Pais();
        pais.setIdPais(rs.getInt("IdPais"));
        pais.setNombre(rs.getString("NombrePais"));

        estado.setPais(pais);
        municipio.setEstado(estado);
        colonia.setMunicipio(municipio);
        direccion.setColonia(colonia);

        return direccion;
    }
    
    
    @Override
    public Result UpdateStatus(int idUsuario, int status) {

        Result result = new Result();

        try {

            JdbcTemplate.update("CALL StatusUpdateSP(?, ?)",
                    status,
                    idUsuario
            );

            result.correct = true;

        } catch (Exception ex) {

            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;

        }

        return result;
    }
  
    
}
