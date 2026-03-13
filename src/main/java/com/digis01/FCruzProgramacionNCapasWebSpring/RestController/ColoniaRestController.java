package com.digis01.FCruzProgramacionNCapasWebSpring.RestController;

import com.digis01.FCruzProgramacionNCapasWebSpring.DAO.ColoniaDAOJPAImplementation;
import com.digis01.FCruzProgramacionNCapasWebSpring.JPA.Colonia;
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
@RequestMapping("Api/Colonia")
@Tag(name = "Colonia", description = "Servicios REST para la gestión y consulta de colonias")
public class ColoniaRestController {
    
    @Autowired
    private ColoniaDAOJPAImplementation coloniaDAOJPAImplementation;
    
    @Operation(
        summary = "Obtener colonias por municipio",
        description = "Recupera todas las colonias que pertenecen a un municipio específico utilizando su ID"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Lista de colonias obtenida correctamente",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Colonia.class),
                examples = @ExampleObject(
                    value = """
                    [
                      {
                        "idColonia": 1,
                        "nombre": "Centro",
                        "codigoPostal": "50000"
                      },
                      {
                        "idColonia": 2,
                        "nombre": "San Mateo",
                        "codigoPostal": "50010"
                      },
                      {
                        "idColonia": 3,
                        "nombre": "Universidad",
                        "codigoPostal": "50130"
                      }
                    ]
                    """
                )
            )
        ),
        @ApiResponse(
            responseCode = "404",
            description = "El municipio no existe o no tiene colonias asociadas"
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Error interno del servidor",
            content = @Content(
                examples = @ExampleObject(
                    value = """
                    {
                     "error": "Ocurrió un error al obtener las colonias"
                    }
                    """
                )
            )
        )
    })
    @GetMapping("/Municipio/{idMunicipio}")
    public ResponseEntity<?> getColoniaByMunicipios(
            @Parameter(
                description = "ID del municipio del cual se desean obtener las colonias",
                example = "1",
                required = true
            )
            @PathVariable int idMunicipio){

        Result result = coloniaDAOJPAImplementation.getColoniaByMunicipios(idMunicipio);

        if(result.correct){
            return ResponseEntity.ok(result.objects);
        }else{
            return ResponseEntity.status(500).body(result.errorMessage);
        }
    }
    
}
