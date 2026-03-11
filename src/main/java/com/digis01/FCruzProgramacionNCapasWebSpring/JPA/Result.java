package com.digis01.FCruzProgramacionNCapasWebSpring.JPA;

import java.util.List;

public class Result {
    
    public boolean correct;
    public String errorMessage;
    public Exception ex;
    public Object object; 
    public List<Object> objects;
    
    public boolean isCorrect() {
        return correct;
    }

    public void setCorrect(boolean correct) {
        this.correct = correct;
    }
    
}
