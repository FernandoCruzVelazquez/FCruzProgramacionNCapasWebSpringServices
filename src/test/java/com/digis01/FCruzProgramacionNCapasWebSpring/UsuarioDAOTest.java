package com.digis01.FCruzProgramacionNCapasWebSpring;

import com.digis01.FCruzProgramacionNCapasWebSpring.DAO.UsuarioDAOJPAImplementation;
import com.digis01.FCruzProgramacionNCapasWebSpring.JPA.Result;
import com.digis01.FCruzProgramacionNCapasWebSpring.JPA.Usuario;
import com.digis01.FCruzProgramacionNCapasWebSpring.JPA.Rol;

import java.text.SimpleDateFormat;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.Date;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UsuarioDAOTest {

    @Autowired
    private UsuarioDAOJPAImplementation usuarioDAO;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    void AddTest() throws Exception {

        Usuario usuario = new Usuario();

        usuario.setNombre("Pedro");
        usuario.setApellidoPaterno("Perez");
        usuario.setApellidosMaterno("Morales");
        usuario.setEmail("pedro@test.com");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        usuario.setFechaNacimiento(sdf.parse("2000-05-10"));

        usuario.setTelefono("7221234567");
        usuario.setCelular("7229876543");
        usuario.setUserName("pedro123");
        usuario.setSexo("M");
        usuario.setPassword("Password1@");
        usuario.setCURP("HEGC000510HDFRRN09");
        usuario.setStatus(1);

        Rol rol = new Rol();
        rol.setIdRol(1); 
        usuario.setRol(rol);

        Result result = usuarioDAO.Add(usuario);

        Assertions.assertTrue(result.correct);

        Usuario usuarioBD = entityManager.find(Usuario.class, usuario.getIdUsuario());

        Assertions.assertNotNull(usuarioBD);
    }
    
    @Test
    void UpdateStatusTest() {

        Usuario usuario = new Usuario();

        usuario.setNombre("Ana");
        usuario.setApellidoPaterno("Martinez");
        usuario.setApellidosMaterno("Lopez");
        usuario.setEmail("ana" + System.currentTimeMillis() + "@test.com");

        usuario.setFechaNacimiento(new Date());
        usuario.setTelefono("7221234567");
        usuario.setCelular("7221234567");
        usuario.setUserName("ana" + System.currentTimeMillis());
        usuario.setSexo("F");
        usuario.setPassword("Password1@");
        usuario.setCURP("HEGC990101MDFRRN04");
        usuario.setStatus(1);

        Rol rol = new Rol();
        rol.setIdRol(1);
        usuario.setRol(rol);

        usuarioDAO.Add(usuario);

        Result result = usuarioDAO.UpdateStatus(usuario.getIdUsuario(), 0);

        Assertions.assertTrue(result.correct);

        Usuario usuarioBD = entityManager.find(Usuario.class, usuario.getIdUsuario());

        Assertions.assertEquals(0, usuarioBD.getStatus());
    }
    
    @Test
    void DeleteTest() throws Exception {

        Usuario usuario = new Usuario();

        usuario.setNombre("Carlos");
        usuario.setApellidoPaterno("Ramirez");
        usuario.setApellidosMaterno("Lopez");
        usuario.setEmail("carlos" + System.currentTimeMillis() + "@test.com");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        usuario.setFechaNacimiento(sdf.parse("1995-03-03"));

        usuario.setTelefono("7221234567");
        usuario.setCelular("7221234567");
        usuario.setUserName("carlos" + System.currentTimeMillis());
        usuario.setSexo("M");
        usuario.setPassword("Password1@");
        usuario.setCURP("HEGC950303HDFRRN03");
        usuario.setStatus(1);

        Rol rol = new Rol();
        rol.setIdRol(1);
        usuario.setRol(rol);

        usuarioDAO.Add(usuario);

        Result resultDelete = usuarioDAO.Delete(usuario.getIdUsuario());

        Assertions.assertTrue(resultDelete.correct);

        Usuario usuarioBD = entityManager.find(Usuario.class, usuario.getIdUsuario());

        Assertions.assertNull(usuarioBD);
    }
}