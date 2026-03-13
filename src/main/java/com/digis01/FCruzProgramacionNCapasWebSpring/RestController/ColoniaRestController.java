package com.digis01.FCruzProgramacionNCapasWebSpring.RestController;

import com.digis01.FCruzProgramacionNCapasWebSpring.DAO.ColoniaDAOJPAImplementation;
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
@RequestMapping("Api/Colonia")
@Tag(name = "Colonia", description = "Servicios para la gestion de las colonias")
public class ColoniaRestController {
    
    @Autowired
    private ColoniaDAOJPAImplementation coloniaDAOJPAImplementation;
    
    @Operation(
        summary = "Optiene todas las colonias",
        description = "Realiza una consulta para recuperar las colonias relacionados con el id del municipio"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Recupera las colonias relacionados con el municipio"),
        @ApiResponse(responseCode = "404", description = "Id del municipio inexixstente"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/Municipio/{idMunicipio}")
    public ResponseEntity<?> getColoniaByMunicipios(@PathVariable int idMunicipio){

        Result result = coloniaDAOJPAImplementation.getColoniaByMunicipios(idMunicipio);

        if(result.correct){
            return ResponseEntity.ok(result.objects);
        }else{
            return ResponseEntity.status(500).body(result.errorMessage);
        }
    }
    
}
