package com.digis01.FCruzProgramacionNCapasWebSpring.DAO;

import com.digis01.FCruzProgramacionNCapasWebSpring.JPA.Result;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ColoniaDAOJPAImplementation implements IColoniaJPA {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Result getColoniaByMunicipios(int idMunicipio) {
        Result result = new Result();
        try {
            TypedQuery<com.digis01.FCruzProgramacionNCapasWebSpring.JPA.Colonia> query = entityManager.createQuery(
                "SELECT c FROM Colonia c WHERE c.municipio.idMunicipio = :id", 
                com.digis01.FCruzProgramacionNCapasWebSpring.JPA.Colonia.class);
            
            query.setParameter("id", idMunicipio);
            List<com.digis01.FCruzProgramacionNCapasWebSpring.JPA.Colonia> coloniasJPA = query.getResultList();

            result.objects = new ArrayList<>();
            for (com.digis01.FCruzProgramacionNCapasWebSpring.JPA.Colonia coloniaJPA : coloniasJPA) {
                result.objects.add(modelMapper.map(coloniaJPA, com.digis01.FCruzProgramacionNCapasWebSpring.JPA.Colonia.class));
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
    public Result getDireccionByCP(String codigoPostal) {
        Result result = new Result();
        try {
            String jpql = "SELECT c FROM Colonia c " +
                          "JOIN FETCH c.municipio m " +
                          "JOIN FETCH m.estado e " +
                          "JOIN FETCH e.pais p " +
                          "WHERE c.codigoPostal = :cp";

            TypedQuery<com.digis01.FCruzProgramacionNCapasWebSpring.JPA.Colonia> query = 
                entityManager.createQuery(jpql, com.digis01.FCruzProgramacionNCapasWebSpring.JPA.Colonia.class);
            
            query.setParameter("cp", codigoPostal);
            List<com.digis01.FCruzProgramacionNCapasWebSpring.JPA.Colonia> coloniasJPA = query.getResultList();

            result.objects = new ArrayList<>();
            for (com.digis01.FCruzProgramacionNCapasWebSpring.JPA.Colonia coloniaJPA : coloniasJPA) {
                result.objects.add(modelMapper.map(coloniaJPA, com.digis01.FCruzProgramacionNCapasWebSpring.JPA.Colonia.class));
            }
            
            result.correct = true;
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        return result;
    }
}
