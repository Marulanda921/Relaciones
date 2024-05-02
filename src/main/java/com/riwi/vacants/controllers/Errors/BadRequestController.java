package com.riwi.vacants.controllers.Errors;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.riwi.vacants.utils.dto.Errors.BaseErrorResponse;
import com.riwi.vacants.utils.dto.Errors.ErrorResponse;
import com.riwi.vacants.utils.dto.Errors.ErrorsResponse;
import com.riwi.vacants.utils.exeptions.IdNotFoundException;




//decimos que estara ecuchando cualquier excepcion que se active
//Controlador de errores, estatus de error 
@RestControllerAdvice
@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class BadRequestController {
    
    //que metodos va a tener el metodo
    /*
     * para especificar cuando se va a disparar este metodo utilizamos 
     * la notacion ecxception hundler
     * ella recibe que tipo de excepcio es la que va a usar para disparar eyse metodo
     */
    @ExceptionHandler(IdNotFoundException.class)
    public BaseErrorResponse handleIdNotFound(IdNotFoundException exception){
        return ErrorResponse.builder()
        .message(exception.getMessage())
        .status(HttpStatus.BAD_REQUEST.name())
        .code(HttpStatus.BAD_REQUEST.value())
        .build();

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public BaseErrorResponse handleErrors(MethodArgumentNotValidException exception){
       List<String> errors = new ArrayList<>();
       
        exception.getAllErrors().forEach(error -> errors.add(error.getDefaultMessage()));
        return ErrorsResponse.builder()
         .errors(errors)
         .status(HttpStatus.BAD_REQUEST.name())
         .code(HttpStatus.BAD_REQUEST.value())
         .build();
    }
}
