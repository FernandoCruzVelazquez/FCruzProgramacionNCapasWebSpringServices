package com.digis01.FCruzProgramacionNCapasWebSpring.DAO;

import com.digis01.FCruzProgramacionNCapasWebSpring.JPA.Direccion;
import com.digis01.FCruzProgramacionNCapasWebSpring.JPA.Result;

public interface IDireccionJPA {
    Result Update(Direccion direccion);
    Result DeleteDireccion(int idDireccion);
    Result Add(Direccion direccion);
}
