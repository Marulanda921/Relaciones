package com.riwi.vacants.controllers;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.riwi.vacants.services.interfaces.IVacantsService;
import com.riwi.vacants.utils.dto.request.VacantRequest;
import com.riwi.vacants.utils.dto.response.VacantsResponse;

import lombok.AllArgsConstructor;

@RestController/*permite que sea un controlador y que pueda responde de forma JSON
 */
@AllArgsConstructor
@RequestMapping(path = "/vacants")
public class VacantController {


    @Autowired
    private final IVacantsService vacantsService;

    @GetMapping
    public ResponseEntity<Page<VacantsResponse>> getAll(
        @RequestParam(defaultValue = "1") int page,
        @RequestParam (defaultValue = "7") int size
    ){
        return  ResponseEntity.ok(this.vacantsService.getAll(page - 1, size));
    }

    @PostMapping

    //si no le envio los parametros obligatirios no servira
    public ResponseEntity<VacantsResponse> insert(@RequestBody
    @Validated VacantRequest vacant){
        return  ResponseEntity.ok( this.vacantsService.create(vacant));
    }


}
