package com.digis01.FCruzProgramacionNCapasWebSpring.DAO;

import com.digis01.FCruzProgramacionNCapasWebSpring.JPA.Result;
import com.digis01.FCruzProgramacionNCapasWebSpring.JPA.Usuario;

public interface IUsuarioJPA {

    Result GetAll();
    Result Add(Usuario usuario);
    
    Result AddCM(Usuario usuarioML);
    Result GetById(int idUsuario);
    Result Update(Usuario usuario);
    Result UpdateFoto(int idUsuario, String foto);
    Result GetByFilter(Usuario usuario);
    Result Delete(int idUsuario);
    Result UpdateStatus(int idUsuario, int status);
    
}