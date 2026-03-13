package com.digis01.FCruzProgramacionNCapasWebSpring.RestController;

import com.digis01.FCruzProgramacionNCapasWebSpring.DAO.EstadoDAOJPAImplementation;
import com.digis01.FCruzProgramacionNCapasWebSpring.JPA.Estado;
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
@RequestMapping("Api/Estado")
@Tag(name = "Estado", description = "Servicios REST para la gestión y consulta de estados")
public class EstadoRestController {
    
    @Autowired
    private EstadoDAOJPAImplementation estadoDAOJPAImplementation;
    
    @Operation(
        summary = "Obtener estados por país",
        description = "Recupera todos los estados que pertenecen a un país específico utilizando su ID"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Lista de estados obtenida correctamente",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Estado.class),
                examples = @ExampleObject(
                    value = """
                    [
                      {
                        "idEstado": 1,
                        "nombre": "Estado de México"
                      },
                      {
                        "idEstado": 2,
                        "nombre": "Jalisco"
                      },
                      {
                        "idEstado": 3,
                        "nombre": "Nuevo León"
                      }
                    ]
                    """
                )
            )
        ),
        @ApiResponse(
            responseCode = "404",
            description = "El país no existe o no tiene estados asociados"
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Error interno del servidor",
            content = @Content(
                examples = @ExampleObject(
                    value = """
                    {
                     "error": "Ocurrió un error al obtener los estados"
                    }
                    """
                )
            )
        )
    })
    @GetMapping("/Pais/{idPais}")
    public ResponseEntity<?> getEstadosByPais(
            @Parameter(
                description = "ID del país del cual se desean obtener los estados",
                example = "1",
                required = true
            )
            @PathVariable int idPais){

        Result result = estadoDAOJPAImplementation.getEstadosByPais(idPais);

        if(result.correct){
            return ResponseEntity.ok(result.objects);
        }else{
            return ResponseEntity.status(500).body(result.errorMessage);
        }
    }
    
}
