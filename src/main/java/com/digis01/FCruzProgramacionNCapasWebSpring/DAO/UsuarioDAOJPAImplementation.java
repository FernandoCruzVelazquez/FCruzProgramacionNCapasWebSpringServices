package com.digis01.FCruzProgramacionNCapasWebSpring.DAO;


import com.digis01.FCruzProgramacionNCapasWebSpring.JPA.Result;
import com.digis01.FCruzProgramacionNCapasWebSpring.JPA.Usuario;
import com.digis01.FCruzProgramacionNCapasWebSpring.JPA.Direccion;
import com.digis01.FCruzProgramacionNCapasWebSpring.JPA.Rol;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class UsuarioDAOJPAImplementation implements IUsuarioJPA {

    @PersistenceContext
    private EntityManager entityManager; 
    @Autowired
    private ModelMapper modelMapper;
    
    @Override
    public Result GetAll() {
        Result result = new Result();

        try {

            TypedQuery<Usuario> queryUsuario = entityManager.createQuery("FROM Usuario", Usuario.class);

            List<Usuario> usuariosJPA = queryUsuario.getResultList();

            result.objects = new ArrayList<>(); 

            for (Usuario usuarioJPA : usuariosJPA) {

                Usuario usuarioML = modelMapper.map(usuarioJPA,Usuario.class);

                result.objects.add(usuarioML); 
            }

            result.correct = true;

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }

        return result;
    }

    @Override
    @Transactional
    public Result Add(Usuario usuarioML) {

        Result result = new Result();

        try {

            Usuario usuarioJPA = modelMapper.map(usuarioML, Usuario.class);

            if (usuarioML.getRol() != null && usuarioML.getRol().getIdRol() > 0) {

                Rol rolJPA = entityManager.find(Rol.class,usuarioML.getRol().getIdRol() );

                usuarioJPA.setRol(rolJPA);
            }

            if (usuarioJPA.getDireccion() != null) {
                for (Direccion direccion : usuarioJPA.getDireccion()) {

                    direccion.setUsuario(usuarioJPA); 
                }
            }

            
            entityManager.persist(usuarioJPA);

            result.correct = true;

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }

        return result;
    }
    
    @Override
    @Transactional
    public Result AddCM(Usuario usuarioML) {

        Result result = new Result();

        try {

            Usuario usuarioJPA = modelMapper.map(usuarioML, Usuario.class);

            usuarioJPA.setStatus(1);

            if (usuarioML.getRol() != null && usuarioML.getRol().getIdRol() > 0) {

                Rol rolJPA = entityManager.find(Rol.class, usuarioML.getRol().getIdRol());

                usuarioJPA.setRol(rolJPA);
            }

            if (usuarioJPA.getDireccion() != null) {
                for (Direccion direccion : usuarioJPA.getDireccion()) {

                    direccion.setUsuario(usuarioJPA);
                }
            }

            entityManager.persist(usuarioJPA);

            result.correct = true;

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }

        return result;
    }
    
    @Override
    public Result GetById(int idUsuario) {
        Result result = new Result();
        try {
            
            TypedQuery<Usuario> query = entityManager.createQuery(
                "SELECT usuario FROM Usuario usuario " +
                "LEFT JOIN FETCH usuario.rol rol " +
                "LEFT JOIN FETCH usuario.direccion direccion " +
                "LEFT JOIN FETCH direccion.colonia colonia " +
                "LEFT JOIN FETCH colonia.municipio municipio " +
                "LEFT JOIN FETCH municipio.estado estado " +
                "LEFT JOIN FETCH estado.pais " +
                "WHERE usuario.idUsuario = :id",
                Usuario.class);
            query.setParameter("id", idUsuario);Usuario usuarioJPA = query.getSingleResult();

            Usuario usuarioML =  modelMapper.map(usuarioJPA, Usuario.class);

            if (usuarioML.getDireccion() != null) {
                for (Direccion d : usuarioML.getDireccion()) {
                    d.setUsuario(null); 
                }
            }

            result.object = usuarioML;
            result.correct = true;

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = "No se encontró el usuario";
        }
        return result;
    }
    
    @Override
    @Transactional
    public Result Update(Usuario usuarioML) {

        Result result = new Result();

        try {

            Usuario usuarioJPA = entityManager.find(Usuario.class, usuarioML.getIdUsuario());

            usuarioJPA.setUserName(usuarioML.getUserName());
            usuarioJPA.setNombre(usuarioML.getNombre());
            usuarioJPA.setApellidoPaterno(usuarioML.getApellidoPaterno());
            usuarioJPA.setApellidosMaterno(usuarioML.getApellidosMaterno());
            usuarioJPA.setEmail(usuarioML.getEmail());
            usuarioJPA.setTelefono(usuarioML.getTelefono());
            usuarioJPA.setCelular(usuarioML.getCelular());
            usuarioJPA.setSexo(usuarioML.getSexo());
            usuarioJPA.setCURP(usuarioML.getCURP());

            Rol rol = entityManager.find(Rol.class, usuarioML.getRol().getIdRol());
            usuarioJPA.setRol(rol);

            entityManager.merge(usuarioJPA);

            result.correct = true;

        } catch (Exception ex) {

            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
        }

        return result;
    }
    
    @Override
    @Transactional
    public Result UpdateFoto(int idUsuario, String foto) {
        Result result = new Result();
        try {
            Usuario usuarioJPA = entityManager.find(Usuario.class, idUsuario);

            if (usuarioJPA != null) {
                usuarioJPA.setFoto(foto);
                result.correct = true;
            } else {
                result.correct = false;
                result.errorMessage = "Usuario no encontrado";
            }
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

            String jpql = "SELECT DISTINCT u FROM Usuario u " +
                          "LEFT JOIN FETCH u.rol r " +
                          "LEFT JOIN FETCH u.direccion d " +
                          "LEFT JOIN FETCH d.colonia c " +
                          "LEFT JOIN FETCH c.municipio m " +
                          "LEFT JOIN FETCH m.estado e " +
                          "LEFT JOIN FETCH e.pais p " +
                          "WHERE 1=1 ";

            if (usuarioBusqueda.getNombre() != null && !usuarioBusqueda.getNombre().trim().isEmpty()) {
                jpql += "AND LOWER(u.nombre) LIKE LOWER(:nombre) ";
            }

            if (usuarioBusqueda.getApellidoPaterno() != null && !usuarioBusqueda.getApellidoPaterno().trim().isEmpty()) {
                jpql += "AND LOWER(u.apellidoPaterno) LIKE LOWER(:apellidoPaterno) ";
            }

            if (usuarioBusqueda.getApellidosMaterno() != null && !usuarioBusqueda.getApellidosMaterno().trim().isEmpty()) {
                jpql += "AND LOWER(u.apellidosMaterno) LIKE LOWER(:apellidoMaterno) ";
            }

            if (usuarioBusqueda.getRol() != null &&
                usuarioBusqueda.getRol().getIdRol() != null &&
                usuarioBusqueda.getRol().getIdRol() > 0) {

                jpql += "AND r.idRol = :idRol ";
            }

            TypedQuery<Usuario> query =
                    entityManager.createQuery(jpql, Usuario.class);

            if (usuarioBusqueda.getNombre() != null && !usuarioBusqueda.getNombre().trim().isEmpty()) {
                query.setParameter("nombre", "%" + usuarioBusqueda.getNombre().trim() + "%");
            }

            if (usuarioBusqueda.getApellidoPaterno() != null && !usuarioBusqueda.getApellidoPaterno().trim().isEmpty()) {
                query.setParameter("apellidoPaterno", "%" + usuarioBusqueda.getApellidoPaterno().trim() + "%");
            }

            if (usuarioBusqueda.getApellidosMaterno() != null && !usuarioBusqueda.getApellidosMaterno().trim().isEmpty()) {
                query.setParameter("apellidoMaterno", "%" + usuarioBusqueda.getApellidosMaterno().trim() + "%");
            }

            if (usuarioBusqueda.getRol() != null &&
                usuarioBusqueda.getRol().getIdRol() != null &&
                usuarioBusqueda.getRol().getIdRol() > 0) {

                query.setParameter("idRol", usuarioBusqueda.getRol().getIdRol());
            }

            List<Usuario> usuariosJPA = query.getResultList();

            result.objects = new ArrayList<>();

            for (Usuario usuarioJPA : usuariosJPA) {

                Usuario usuarioML =  modelMapper.map(usuarioJPA,Usuario.class);

                result.objects.add(usuarioML);
            }

            result.correct = true;

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getMessage();
            result.ex = ex;
        }

        return result;
    }
    
    @Override
    @Transactional
    public Result Delete(int idUsuario) {

        Result result = new Result();

        try {

            Usuario usuarioJPA = entityManager.find(Usuario.class, idUsuario);

            if (usuarioJPA != null) {
                entityManager.remove(usuarioJPA);
                result.correct = true;
            } else {
                result.correct = false;
                result.errorMessage = "Usuario no encontrado";
            }

        } catch (Exception ex) {

            result.correct = false;
            result.errorMessage = ex.getMessage();
            result.ex = ex;
        }

        return result;
    }

    @Override
    @Transactional
    public Result UpdateStatus(int idUsuario, int status) {
        Result result = new Result();
        try {
            Usuario usuarioJPA = entityManager.find(Usuario.class, idUsuario);
            if (usuarioJPA != null) {
                usuarioJPA.setStatus(status); // Actualizamos a 1 o 0
                result.correct = true;
            } else {
                result.correct = false;
                result.errorMessage = "No se encontró el usuario para actualizar el status.";
            }
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getMessage();
        }
        return result;
    }
    
    @Override
    @Transactional
    public Result GetByUsername(String Username) {
        Result result = new Result();

        try {
            String jpql = "SELECT u FROM Usuario u " +
                          "LEFT JOIN FETCH u.rol " +
                          "WHERE u.userName = :pUsername";

            TypedQuery<Usuario> queryUsuario =
                    entityManager.createQuery(jpql, Usuario.class);

            queryUsuario.setParameter("pUsername", Username);

            Usuario usuarioJPA = queryUsuario.getSingleResult();

            Usuario usuarioML =
                    modelMapper.map(usuarioJPA, Usuario.class);

            result.object = usuarioML;
            result.correct = true;

        } catch (jakarta.persistence.NoResultException nrEx) {
            result.correct = false;
            result.errorMessage = "Usuario no encontrado en la base de datos.";
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }

        return result;
    }
    
}