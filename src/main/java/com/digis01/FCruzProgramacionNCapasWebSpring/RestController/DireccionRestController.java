package com.digis01.FCruzProgramacionNCapasWebSpring.RestController;

import com.digis01.FCruzProgramacionNCapasWebSpring.DAO.DireccionDAOJPAImplementation;
import com.digis01.FCruzProgramacionNCapasWebSpring.JPA.Direccion;
import com.digis01.FCruzProgramacionNCapasWebSpring.JPA.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("Api/Direccion")
public class DireccionRestController {
    
    @Autowired
    private DireccionDAOJPAImplementation direccionDAOJPAImplementation;
    
    @GetMapping
    public Result ActualizarDireccion(@RequestBody Direccion direccion){
        return direccionDAOJPAImplementation.Update(direccion);
    }
    

}
