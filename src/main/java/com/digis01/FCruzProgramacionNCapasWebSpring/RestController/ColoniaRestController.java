package com.digis01.FCruzProgramacionNCapasWebSpring.RestController;

import com.digis01.FCruzProgramacionNCapasWebSpring.DAO.ColoniaDAOJPAImplementation;
import com.digis01.FCruzProgramacionNCapasWebSpring.JPA.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("Api/Colonia")
public class ColoniaRestController {
    
    @Autowired
    private ColoniaDAOJPAImplementation coloniaDAOJPAImplementation;
    
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
