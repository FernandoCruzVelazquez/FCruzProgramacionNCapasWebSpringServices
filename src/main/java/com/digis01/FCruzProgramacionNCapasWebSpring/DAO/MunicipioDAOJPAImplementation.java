package com.digis01.FCruzProgramacionNCapasWebSpring.DAO;

import com.digis01.FCruzProgramacionNCapasWebSpring.JPA.Result;
import com.digis01.FCruzProgramacionNCapasWebSpring.JPA.Municipio;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class MunicipioDAOJPAImplementation implements IMunicipioJPA {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Result getMunicipioByEstado(int idEstado) {
        Result result = new Result();
        try {

            String jpql = "SELECT m FROM Municipio m " +
                          "JOIN FETCH m.estado e " +
                          "WHERE e.idEstado = :id";

            TypedQuery<Municipio> query = entityManager.createQuery(jpql, Municipio.class);
            query.setParameter("id", idEstado);

            List<Municipio> municipiosJPA = query.getResultList();
            result.objects = new ArrayList<>();

            for (Municipio municipioJPA : municipiosJPA) {
                result.objects.add(modelMapper.map(municipioJPA, Municipio.class));
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