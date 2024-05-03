package com.riwi.vacants.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.riwi.vacants.services.interfaces.IVacantsService;
import com.riwi.vacants.utils.dto.Errors.ErrorResponse;
import com.riwi.vacants.utils.dto.Errors.ErrorsResponse;
import com.riwi.vacants.utils.dto.request.VacantRequest;
import com.riwi.vacants.utils.dto.response.VacantsResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;

import java.util.HashMap;
import java.util.Map;
@ApiResponse(responseCode = "400", description = "Cuando el request no es valido", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
@RestController/*permite que sea un controlador y que pueda responde de forma JSON
 */
@AllArgsConstructor
@RequestMapping(path = "/vacants")
public class VacantController {


    //Inyeccion de dependencias
    @Autowired
    private final IVacantsService vacantsService;

    @Operation(summary = "Sirve para mostrar todas las vacantes existentes")
    @ApiResponse(responseCode = "400", description = "Cuando el request no es valido", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorsResponse.class)))
    @GetMapping
    public ResponseEntity<Page<VacantsResponse>> getAll(
        @RequestParam(defaultValue = "1") int page,
        @RequestParam (defaultValue = "7") int size
    ){
        return  ResponseEntity.ok(this.vacantsService.getAll(page - 1, size));
    }

    @ApiResponse(responseCode = "400", description = "Cuando el id no es valido", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    @GetMapping(path = "/{id}")
    public ResponseEntity<VacantsResponse> get(@PathVariable Long id){
    return ResponseEntity.ok(this.vacantsService.getById(id));
    }

    @ApiResponse(responseCode = "400", description = "Cuando el request no es valido", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorsResponse.class)))
    @PostMapping
    //si no le envio los parametros obligatirios no servira
    public ResponseEntity<VacantsResponse> insert(@RequestBody
    @Validated VacantRequest vacant){
        return  ResponseEntity.ok( this.vacantsService.create(vacant));
    }

    @ApiResponse(responseCode = "400", description = "Cuando el id no es valido", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Map<String,String>> delete(@PathVariable Long id){
        //Creamos un mapa y lo retornamos

        Map<String,String> response = new HashMap<>();
        this.vacantsService.delete(id);
        //llenar mapa
        response.put("message", "Vacante eliminada correctamente.");
        return ResponseEntity.ok(response);
    }

    @ApiResponse(responseCode = "400", description = "Cuando el id no es valido", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    @PutMapping(path = "/{id}")
    public ResponseEntity<VacantsResponse> update(@PathVariable Long id, @Validated @RequestBody VacantRequest vacant){
        return  ResponseEntity.ok(this.vacantsService.update(vacant, id));
    }
}
