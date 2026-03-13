package com.digis01.FCruzProgramacionNCapasWebSpring.RestController;

import com.digis01.FCruzProgramacionNCapasWebSpring.DAO.MunicipioDAOJPAImplementation;
import com.digis01.FCruzProgramacionNCapasWebSpring.JPA.Municipio;
import com.digis01.FCruzProgramacionNCapasWebSpring.JPA.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Tag(name = "Municipio", description = "Servicios REST para la gestión y consulta de municipios")
public class MunicipioRestController {
    
    @Autowired
    private MunicipioDAOJPAImplementation municipioDAOJPAImplementation;
    
    @Operation(
        summary = "Obtener municipios por estado",
        description = "Recupera todos los municipios que pertenecen a un estado específico utilizando su ID"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Lista de municipios obtenida correctamente",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Municipio.class),
                examples = @ExampleObject(
                    value = """
                    [
                      {
                        "idMunicipio": 1,
                        "nombre": "Toluca"
                      },
                      {
                        "idMunicipio": 2,
                        "nombre": "Metepec"
                      },
                      {
                        "idMunicipio": 3,
                        "nombre": "Naucalpan"
                      }
                    ]
                    """
                )
            )
        ),
        @ApiResponse(
            responseCode = "404",
            description = "El estado no existe o no tiene municipios asociados"
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Error interno del servidor",
            content = @Content(
                examples = @ExampleObject(
                    value = """
                    {
                     "error": "Ocurrió un error al obtener los municipios"
                    }
                    """
                )
            )
        )
    })
    @GetMapping("/Estado/{idEstado}")
    public ResponseEntity<?> getMunicipioByEstado(
            @Parameter(
                description = "ID del estado del cual se desean obtener los municipios",
                example = "1",
                required = true
            )
            @PathVariable int idEstado){

        Result result = municipioDAOJPAImplementation.getMunicipioByEstado(idEstado);

        if(result.correct){
            return ResponseEntity.ok(result.objects);
        }else{
            return ResponseEntity.status(500).body(result.errorMessage);
        }
    }
    
}
