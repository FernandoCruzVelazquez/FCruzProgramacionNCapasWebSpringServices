package com.digis01.FCruzProgramacionNCapasWebSpring.DAO;

import com.digis01.FCruzProgramacionNCapasWebSpring.JPA.Result;
import com.digis01.FCruzProgramacionNCapasWebSpring.JPA.Estado;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class EstadoDAOJPAImplementation implements IEstadoJPA {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Result getEstadosByPais(int idPais) {
        Result result = new Result();
        try {

            String jpql = "SELECT e FROM Estado e " +
                          "JOIN FETCH e.pais p " +
                          "WHERE p.idPais = :id";

            TypedQuery<Estado> query = entityManager.createQuery(jpql, Estado.class);
            query.setParameter("id", idPais);

            List<Estado> estadosJPA = query.getResultList();
            result.objects = new ArrayList<>();

            for (Estado estadoJPA : estadosJPA) {
                // Mapeamos cada entidad JPA a tu clase de negocio
                result.objects.add(modelMapper.map(estadoJPA, Estado.class));
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