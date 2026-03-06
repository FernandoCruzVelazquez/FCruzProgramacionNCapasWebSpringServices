package com.digis01.FCruzProgramacionNCapasWebSpring.DAO;

import com.digis01.FCruzProgramacionNCapasWebSpring.JPA.Result;

public interface IColoniaJPA {
    Result getColoniaByMunicipios(int IdMunicipio);
    
    Result getDireccionByCP(String CodigoPostal);
}
