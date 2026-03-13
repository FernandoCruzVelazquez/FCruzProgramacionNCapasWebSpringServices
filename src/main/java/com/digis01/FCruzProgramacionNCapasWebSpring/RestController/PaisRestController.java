package com.digis01.FCruzProgramacionNCapasWebSpring.RestController;

import com.digis01.FCruzProgramacionNCapasWebSpring.DAO.PaisDAOJPAImplementation;
import com.digis01.FCruzProgramacionNCapasWebSpring.JPA.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("Api/Pais")
@Tag(name = "Pais", description = "Servicios para la gestion de los paises")
public class PaisRestController {
    
    @Autowired
    private PaisDAOJPAImplementation paisDAOJPAImplementation;
    
    @Operation(
        summary = "Optiene todos los paises",
        description = "Buscs en la base de datos todos los paises usando un GetAll en la tabla Pais"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Recupera los paises de la tabla Pais"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping
    public ResponseEntity ObtenerPaises(){

        Result result = paisDAOJPAImplementation.GetAll();

        if(result.correct){
            return ResponseEntity.ok(result.objects);
        }else{
            return ResponseEntity.status(500).body(result.errorMessage);
        }
    }
    
}
