package com.digis01.FCruzProgramacionNCapasWebSpring.Security;

import com.digis01.FCruzProgramacionNCapasWebSpring.JPA.Usuario;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class CustomUserDetails implements UserDetails {

    private Usuario usuario;

    public CustomUserDetails(Usuario usuario) {
        this.usuario = usuario;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public String getNombreCompleto() {
        return usuario.getNombre() + " " +
               usuario.getApellidoPaterno() + " " +
               usuario.getApellidosMaterno();
    }

    public String getRol() {
        return usuario.getRol().getNombreRol();
    }

    public String getUserName() {
        return usuario.getUserName();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(() -> "ROLE_" + usuario.getRol().getNombreRol());
    }

    @Override
    public String getPassword() {
        return usuario.getPassword();
    }

    @Override
    public String getUsername() {
        return usuario.getUserName();
    }

    @Override
    public boolean isEnabled() {
        return usuario.getStatus() == 1;
    }

    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
}
