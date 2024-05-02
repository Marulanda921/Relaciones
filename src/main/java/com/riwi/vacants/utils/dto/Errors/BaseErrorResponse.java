package com.riwi.vacants.utils.dto.Errors;
//nos permite generar constructires por medio de builder
import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;



@Data
//Crea un constructor con el super dentro 
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class BaseErrorResponse implements Serializable{
    private String status;
    private Integer code;
}
