package com.digis01.FCruzProgramacionNCapasWebSpring.RestController;

import com.digis01.FCruzProgramacionNCapasWebSpring.DAO.EstadoDAOJPAImplementation;
import com.digis01.FCruzProgramacionNCapasWebSpring.JPA.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("Api/Estado")
@Tag(name = "Estado", description = "Servicios para la gestion de los estados")
public class EstadoRestController {
    
    @Autowired
    private EstadoDAOJPAImplementation estadoDAOJPAImplementation;
    
    @Operation(
        summary = "Optiene todos los estados",
        description = "Realiza una consulta para recuperar los estados relacionados con el id del pais"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Recupera los estados relacionados con el pais"),
        @ApiResponse(responseCode = "404", description = "Id del pais inexixstente"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/Pais/{idPais}")
    public ResponseEntity<?> getEstadosByPais(@PathVariable int idPais){

        Result result = estadoDAOJPAImplementation.getEstadosByPais(idPais);

        if(result.correct){
            return ResponseEntity.ok(result.objects);
        }else{
            return ResponseEntity.status(500).body(result.errorMessage);
        }
    }
    
}
