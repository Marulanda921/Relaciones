package com.riwi.vacants.utils.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder //patorn de diseño para crear clases
@AllArgsConstructor
@NoArgsConstructor
public class CompanyRequest {
    @Size(min = 0, max = 40, message = "El nombre excede la cantidad de caracteres ")
    @NotBlank(message = "El nombre es requerido")
    private String name;
    @NotBlank(message = "la locacion es requerida")
    private String location;
    @Size(min = 0, max = 20, message = "El contacto supera la cantidad")
    @NotBlank(message = "el contacto es requerido")
    private String contact;
}
