package com.aluracursos.forohubchallenge.escudo.errores;

public class IntegrityValidation extends RuntimeException{
    public IntegrityValidation(String msg){
        super(msg);
    }
}
