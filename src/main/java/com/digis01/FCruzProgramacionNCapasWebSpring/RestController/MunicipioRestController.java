package com.digis01.FCruzProgramacionNCapasWebSpring.RestController;

import com.digis01.FCruzProgramacionNCapasWebSpring.DAO.MunicipioDAOJPAImplementation;
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
@RequestMapping("Api/Municipio")
@Tag(name = "Municipio", description = "Servicios para la gestion de los municipios")
public class MunicipioRestController {
    
    @Autowired
    private MunicipioDAOJPAImplementation municipioDAOJPAImplementation;
    
    @Operation(
        summary = "Optiene todos los municipios",
        description = "Reliza una consulta para recuperar los municipios relacionados con el id del Estado"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Recupera los municipios relacionados con el estado"),
        @ApiResponse(responseCode = "404", description = "Id del Estado inexixstente"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/Estado/{idEstado}")
    public ResponseEntity<?> getMunicipioByEstado(@PathVariable int idEstado){

        Result result = municipioDAOJPAImplementation.getMunicipioByEstado(idEstado);

        if(result.correct){
            return ResponseEntity.ok(result.objects);
        }else{
            return ResponseEntity.status(500).body(result.errorMessage);
        }
    }
    
}
