package com.riwi.vacants.services;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.riwi.vacants.entity.Company;
import com.riwi.vacants.entity.Vacant;
import com.riwi.vacants.repos.CompanyRepository;
import com.riwi.vacants.repos.VacantRepository;
import com.riwi.vacants.services.interfaces.IVacantsService;
import com.riwi.vacants.utils.dto.request.VacantRequest;
import com.riwi.vacants.utils.dto.response.CompanyToVacantResponse;
import com.riwi.vacants.utils.dto.response.VacantsResponse;
import com.riwi.vacants.utils.enums.StatusVacant;
import com.riwi.vacants.utils.exeptions.IdNotFoundException;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class VacantService implements IVacantsService {

    @Autowired
    private final VacantRepository vacantRepository;
    private final CompanyRepository companyRepository;

    /*un service, no puede inyectar otro servicio */
    @Override
    public VacantsResponse create(VacantRequest request) {
    //   validar que el id si corresponda o sea valido

    /*Buscamos la compañia que corresponda con el id que se encuentra dentro del request 
     *
    */
     Company company = this.companyRepository.findById(request.getCompanyId()).orElseThrow(()-> new IdNotFoundException("Company"));
        
     /*Convertimos el request en una isntancia de vacante */
     Vacant vacant =  this.requestTOVacant(request, new Vacant());
        
     //guardamos en la BD y convertimos en la nueva entidad de repsuesta al DTO
     vacant.setCompany(company);
        return this.entityTResponse(this.vacantRepository.save(vacant));
    }

    @Override
    public void delete(Long id) {
        Vacant vacant = this.find(id);
        this.vacantRepository.delete(vacant);
    }

    @Override
    public Page<VacantsResponse> getAll(int page, int size) {
        if (page < 0) page = 0;
        
        PageRequest pagination = PageRequest.of(page, size);
        
        return this.vacantRepository.findAll(pagination).map(vacant -> this.entityTResponse(vacant));
    }

    @Override
    public VacantsResponse getById(Long id) {
        return this.entityTResponse(this.find(id));
    }

    @Override
    public VacantsResponse update(VacantRequest request, Long id) {

        //Buscamos la vacante
        Vacant vacant = this.find(id);
        Company company = this.companyRepository.findById(request.getCompanyId()).orElseThrow(()-> new IdNotFoundException("Company"));
        //Validamos la compañia
        //Convertimos el dto  de equest
        vacant = this.requestTOVacant(request, vacant);

        //Agregamos la vacante
        vacant.setCompany(company);

        //Agregamos el nuevo estatus
        vacant.setStatus(request.getStatus());

        //Guardamos en la BD y retornamos el dto de respuesta
        return this.entityTResponse(this.vacantRepository.save(vacant));
    }

    private VacantsResponse entityTResponse(Vacant entity){
        /*Creamos instacion del dto de vacante */
        VacantsResponse response = new VacantsResponse();

        /*Copiar toda la entidad en el dto */
        BeanUtils.copyProperties(entity, response);

        /*Creamos una intancia del dto de compañia dentro de la vacante */
        CompanyToVacantResponse companyDto = new CompanyToVacantResponse();
        
        /*Copio todas las pripiedades de la entidad que se encuentra dentro
         * de la entidad vacante en el dto de respuesta
         * 
         */
        BeanUtils.copyProperties(entity.getCompany(), companyDto);

        /*Agrego el dto lleno a la respuesta */
        response.setCompany(companyDto);

        return response;

    }
    /*obtenemos la vacante donde queremos guardar los datos de request  */
    private Vacant requestTOVacant(VacantRequest request, Vacant entity ){
        entity.setTitle(request.getTitle());
        entity.setDescription(request.getDescription());
        entity.setStatus(StatusVacant.ACTIVE);
        return entity;
    }


    private Vacant find(Long id) {
        return this.vacantRepository.findById(id)
                .orElseThrow(()-> new IdNotFoundException("Vacant"));
    }

}
