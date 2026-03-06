package com.digis01.FCruzProgramacionNCapasWebSpring.DAO;

import com.digis01.FCruzProgramacionNCapasWebSpring.JPA.Result;
import com.digis01.FCruzProgramacionNCapasWebSpring.JPA.Direccion;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class DireccionDAOJPAImplementation implements IDireccionJPA {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    @Transactional
    public Result Update(Direccion direccionML) {

        Result result = new Result();

        try {

            com.digis01.FCruzProgramacionNCapasWebSpring.JPA.Direccion direccionJPA =
                    entityManager.find(
                            com.digis01.FCruzProgramacionNCapasWebSpring.JPA.Direccion.class,
                            direccionML.getIdDireccion()
                    );

            if (direccionJPA != null) {

                direccionJPA.setCalle(direccionML.getCalle());
                direccionJPA.setNumeroExterior(direccionML.getNumeroExterior());
                direccionJPA.setNumeroIInteriori(direccionML.getNumeroIInteriori());

                if (direccionML.getColonia() != null &&
                    direccionML.getColonia().getIdColonia() > 0) {

                    com.digis01.FCruzProgramacionNCapasWebSpring.JPA.Colonia coloniaJPA =
                            entityManager.find(
                                    com.digis01.FCruzProgramacionNCapasWebSpring.JPA.Colonia.class,
                                    direccionML.getColonia().getIdColonia()
                            );

                    direccionJPA.setColonia(coloniaJPA);
                }

                result.correct = true;

            } else {
                result.correct = false;
                result.errorMessage = "Dirección no encontrada";
            }

        } catch (Exception ex) {

            result.correct = false;
            result.errorMessage = ex.getMessage();
            result.ex = ex;
        }

        return result;
    }

    @Override
    @Transactional
    public Result DeleteDireccion(int idDireccion) {

        Result result = new Result();

        try {

            com.digis01.FCruzProgramacionNCapasWebSpring.JPA.Direccion direccionJPA =
                    entityManager.find(
                            com.digis01.FCruzProgramacionNCapasWebSpring.JPA.Direccion.class,
                            idDireccion
                    );

            if (direccionJPA != null) {

                // Romper relación bidireccional si existe
                if (direccionJPA.getUsuario() != null) {
                    direccionJPA.getUsuario().getDireccion().remove(direccionJPA);
                    direccionJPA.setUsuario(null);
                }

                entityManager.remove(direccionJPA);

                result.correct = true;

            } else {

                result.correct = false;
                result.errorMessage = "Dirección no encontrada";
            }

        } catch (Exception ex) {

            result.correct = false;
            result.errorMessage = ex.getMessage();
            result.ex = ex;
        }

        return result;
    }
}