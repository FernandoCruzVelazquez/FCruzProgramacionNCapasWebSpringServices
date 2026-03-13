package com.digis01.FCruzProgramacionNCapasWebSpring.RestController;

import com.digis01.FCruzProgramacionNCapasWebSpring.DAO.DireccionDAOJPAImplementation;
import com.digis01.FCruzProgramacionNCapasWebSpring.JPA.Direccion;
import com.digis01.FCruzProgramacionNCapasWebSpring.JPA.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("Api/Direccion")
@Tag(name = "Direccion", description = "Servicios para la gestión de direcciones de los usuarios")
public class DireccionRestController {
    
    @Autowired
    private DireccionDAOJPAImplementation direccionDAOJPAImplementation;
    
     @Operation(
            summary = "Actualizar dirección",
            description = "Actualiza los datos de una dirección existente en el sistema"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200",
                description = "Dirección actualizada correctamente",
                content = @Content(schema = @Schema(implementation = Result.class))),
        @ApiResponse(responseCode = "404", description = "Dirección no encontrada"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PutMapping
    public Result ActualizarDireccion(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Objeto dirección con los nuevos datos",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = Direccion.class),
                            examples = @ExampleObject(
                                    value = """
                                {
                                 "idDireccion": 1,
                                 "calle": "Av. Reforma",
                                 "numeroExterior": "120",
                                 "numeroInterior": "2A",
                                 "colonia": {
                                   "idColonia": 5
                                 }
                                }
                                """
                            )
                    )
            )
            @RequestBody Direccion direccion){
        return direccionDAOJPAImplementation.Update(direccion);
    }
    
    @Operation(
            summary = "Eliminar dirección",
            description = "Elimina una dirección del sistema utilizando su ID"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200",
                description = "Dirección eliminada correctamente",
                content = @Content(schema = @Schema(implementation = Result.class))),
        @ApiResponse(responseCode = "404", description = "Dirección no encontrada"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @DeleteMapping("/{idDireccion}")
    public Result Eliminar(
            @Parameter(description = "ID de la dirección que se desea eliminar", example = "3")
            @PathVariable int idDireccion) {
        return direccionDAOJPAImplementation.DeleteDireccion(idDireccion);
    }
    
    @Operation(
            summary = "Agregar dirección",
            description = "Permite registrar una nueva dirección asociada a un usuario"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200",
                description = "Dirección agregada correctamente",
                content = @Content(schema = @Schema(implementation = Result.class))),
        @ApiResponse(responseCode = "400", description = "Datos inválidos"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping("/Add")
    public Result Add(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos de la dirección a registrar",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = Direccion.class),
                            examples = @ExampleObject(
                                    value = """
                                {
                                 "calle": "Insurgentes",
                                 "numeroExterior": "450",
                                 "numeroInterior": "3B",
                                 "usuario": {
                                   "idUsuario": 1
                                 },
                                 "colonia": {
                                   "idColonia": 10
                                 }
                                }
                                """
                            )
                    )
            )
            @RequestBody Direccion direccion) {
        return direccionDAOJPAImplementation.Add(direccion);
    }
    

}
