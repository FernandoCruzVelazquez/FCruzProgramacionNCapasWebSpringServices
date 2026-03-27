package com.digis01.FCruzProgramacionNCapasWebSpring.RestController;

import com.digis01.FCruzProgramacionNCapasWebSpring.DAO.UsuarioDAOJPAImplementation;
import com.digis01.FCruzProgramacionNCapasWebSpring.JPA.Result;
import com.digis01.FCruzProgramacionNCapasWebSpring.JPA.Usuario;
import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/Usuario")
public class UsuarioController {

    @Autowired
    private UsuarioDAOJPAImplementation usuarioDAOJPAImplementation;

    @GetMapping("/perfil")
    public ResponseEntity<Usuario> perfil(Principal principal) {

        String username = principal.getName(); 

        Result result = usuarioDAOJPAImplementation.GetByUsername(username);

        if (result.object == null) {
            return ResponseEntity.notFound().build();
        }

        Usuario usuario = (Usuario) result.object;

        return ResponseEntity.ok(usuario);
    }
}   

