package com.digis01.FCruzProgramacionNCapasWebSpring.RestController;

import com.digis01.FCruzProgramacionNCapasWebSpring.DAO.PaisDAOJPAImplementation;
import com.digis01.FCruzProgramacionNCapasWebSpring.JPA.Pais;
import com.digis01.FCruzProgramacionNCapasWebSpring.JPA.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Tag(name = "País", description = "Servicios REST para la gestión y consulta de países")
public class PaisRestController {
    
    @Autowired
    private PaisDAOJPAImplementation paisDAOJPAImplementation;
    
    @Operation(
        summary = "Obtener todos los países",
        description = "Recupera todos los países registrados en la base de datos desde la tabla Pais"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Lista de países obtenida correctamente",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Pais.class),
                examples = @ExampleObject(
                    value = """
                    [
                      {
                        "idPais": 1,
                        "nombre": "México"
                      },
                      {
                        "idPais": 2,
                        "nombre": "Estados Unidos"
                      },
                      {
                        "idPais": 3,
                        "nombre": "Canadá"
                      }
                    ]
                    """
                )
            )
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Error interno del servidor",
            content = @Content(
                mediaType = "application/json",
                examples = @ExampleObject(
                    value = """
                {
                 "error": "Ocurrió un error al obtener los países"
                }
                """
                )
            )
        )
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
