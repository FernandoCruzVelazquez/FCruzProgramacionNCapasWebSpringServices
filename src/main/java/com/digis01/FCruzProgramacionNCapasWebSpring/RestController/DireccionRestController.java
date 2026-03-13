package com.digis01.FCruzProgramacionNCapasWebSpring.RestController;

import com.digis01.FCruzProgramacionNCapasWebSpring.DAO.DireccionDAOJPAImplementation;
import com.digis01.FCruzProgramacionNCapasWebSpring.JPA.Direccion;
import com.digis01.FCruzProgramacionNCapasWebSpring.JPA.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
        description = "Permite actualizar la información de una dirección existente"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Dirección actualizada correctamente"),
        @ApiResponse(responseCode = "404", description = "Dirección no encontrada"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PutMapping
    public Result ActualizarDireccion(@RequestBody Direccion direccion){
        return direccionDAOJPAImplementation.Update(direccion);
    }
    
    @Operation(
        summary = "Eliminar dirección",
        description = "Elimina una dirección del sistema utilizando su ID"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Dirección eliminada correctamente"),
        @ApiResponse(responseCode = "404", description = "Dirección no encontrada"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @DeleteMapping("/{idDireccion}")
    public Result Eliminar(@PathVariable int idDireccion) {
        return direccionDAOJPAImplementation.DeleteDireccion(idDireccion);
    }
    
    @Operation(
        summary = "Agregar dirección",
        description = "Permite registrar una nueva dirección en el sistema"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Dirección agregada correctamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping("/Add")
    public Result Add(@RequestBody Direccion direccion) {
        return direccionDAOJPAImplementation.Add(direccion);
    }
    

}
