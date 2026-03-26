package com.digis01.FCruzProgramacionNCapasWebSpring.Service;

import com.digis01.FCruzProgramacionNCapasWebSpring.JPA.Result;
import com.digis01.FCruzProgramacionNCapasWebSpring.JPA.Usuario;


import com.digis01.FCruzProgramacionNCapasWebSpring.DAO.UsuarioDAOJPAImplementation;
import com.digis01.FCruzProgramacionNCapasWebSpring.Security.CustomUserDetails;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetail implements UserDetailsService{
    
    private final UsuarioDAOJPAImplementation usuarioDAOJPAImplementation;
    
    public UserDetail( UsuarioDAOJPAImplementation usuarioDAOJPAImplementation){
        this.usuarioDAOJPAImplementation = usuarioDAOJPAImplementation;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Result result = usuarioDAOJPAImplementation.GetByUsername(username);

        if (result.object == null) {
            throw new UsernameNotFoundException("Usuario no encontrado: " + username);
        }

        Usuario usuario = (Usuario) result.object;

        return new CustomUserDetails(usuario);
    }
    
}