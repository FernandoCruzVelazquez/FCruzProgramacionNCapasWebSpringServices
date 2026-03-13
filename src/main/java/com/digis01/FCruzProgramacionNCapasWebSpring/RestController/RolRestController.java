
package com.digis01.FCruzProgramacionNCapasWebSpring.RestController;

import com.digis01.FCruzProgramacionNCapasWebSpring.DAO.RolDAOJPAImplementation;
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
@RequestMapping("Api/Rol")
@Tag(name = "Rol", description = "Servicios para la gestion de los roles")
public class RolRestController {
    
    @Autowired
    private RolDAOJPAImplementation rolDAOJPAImplementation;
    
    @Operation(
        summary = "Optiene todos los roles",
        description = "Buscs en la base de datos todos los roles usando un GetAll en la tabla Rol"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Recupera los roles de la tabla"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping()
    public ResponseEntity ObtenerRol(){

        Result result = rolDAOJPAImplementation.GetAll();

        if(result.correct){
            return ResponseEntity.ok(result.objects);
        }else{
            return ResponseEntity.status(500).body(result.errorMessage);
        }
    }
    
}
