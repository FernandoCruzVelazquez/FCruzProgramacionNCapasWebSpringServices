package com.digis01.FCruzProgramacionNCapasWebSpring.RestController;

import com.digis01.FCruzProgramacionNCapasWebSpring.DAO.MunicipioDAOJPAImplementation;
import com.digis01.FCruzProgramacionNCapasWebSpring.JPA.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("Api/Municipio")
public class MunicipioRestController {
    
    @Autowired
    private MunicipioDAOJPAImplementation municipioDAOJPAImplementation;
    
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
