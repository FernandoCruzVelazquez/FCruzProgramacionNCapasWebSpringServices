package com.digis01.FCruzProgramacionNCapasWebSpring.RestController;

import com.digis01.FCruzProgramacionNCapasWebSpring.DAO.EstadoDAOJPAImplementation;
import com.digis01.FCruzProgramacionNCapasWebSpring.JPA.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("Api/Estado")
public class EstadoRestController {
    
    @Autowired
    private EstadoDAOJPAImplementation estadoDAOJPAImplementation;
    
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
