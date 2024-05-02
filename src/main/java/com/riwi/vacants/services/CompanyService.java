package com.riwi.vacants.services;

import java.util.ArrayList;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.riwi.vacants.entity.Company;
import com.riwi.vacants.entity.Vacant;
import com.riwi.vacants.repos.CompanyRepository;
import com.riwi.vacants.services.interfaces.ICompanyService;
import com.riwi.vacants.utils.dto.request.CompanyRequest;
import com.riwi.vacants.utils.dto.response.CompanyResponse;
import com.riwi.vacants.utils.dto.response.VacantToCompanyResponse;
import com.riwi.vacants.utils.exeptions.IdNotFoundException;

import lombok.AllArgsConstructor;
@Service
@AllArgsConstructor
public class CompanyService implements ICompanyService{

    @Autowired
    private final CompanyRepository companyRepository;

    @Override
    public Page<CompanyResponse> getAll(int page, int size) {
        //1.configuramos la paginacion
        
        if (page<0) {
            page = 0;
        }
        PageRequest pagination = PageRequest.of(page, size);
        //2.llamamos el repositorio
        return  this.companyRepository.findAll(pagination).map(Company->this.entityToResponse(Company));
    }

    @Override
    public CompanyResponse create(CompanyRequest request) {
        //convertimos el request en la entidad
        Company company = this.requestToEntity(request, new Company());
        //agregamos la entidad en el repositorio y el retorno lo convertimos en respuesta
        return this.entityToResponse(this.companyRepository.save(company)); 
    }

    @Override
    public CompanyResponse update(CompanyRequest request, String id) {
       Company companyToUpdate = this.find(id);
        Company company = this.requestToEntity(request, companyToUpdate);
        return this.entityToResponse(this.companyRepository.save(company));

    }

    @Override
    public void delete(String id) {
        //Buscamos la compañia a la que corresponde el id y eliminamos
        Company company = this.find(id);
        //Eliminamos
        this.companyRepository.delete(company);
    }

    @Override
    public CompanyResponse getById(String id) {
        //buscamos la compañia con el id
        Company company = this.find(id);

        //convertimos la entidad al dto de respuesta y lo returnamos
        return this.entityToResponse(company);
    }

    //este metodo se encargara de convertir una entidad en el dto de respuesta de la entidad
    private CompanyResponse entityToResponse(Company entity){
        CompanyResponse response = new CompanyResponse();
        
        //beanUtils nos permite hacer una copia de una clase en otra, en este caso toda la entidad
        //de tipo company sera copiada con la informacion requerida por la varibale
        //tipo companyResponse

        BeanUtils.copyProperties(entity, response);

        //stream->convierte la lista en coleccion para poder iterarse
        //map -> itera toda la lista y retorna cambios
        //collect -> crea de nuevo toda la lista que se habia transformado en colecciones

        response.setVacants(entity.getVacants().stream()
        .map(vacant -> this.vacantToResponse(vacant))
        .collect(Collectors.toList()));
        return response;
    }

    private VacantToCompanyResponse vacantToResponse(Vacant entity){
        VacantToCompanyResponse response = new VacantToCompanyResponse();

        BeanUtils.copyProperties(entity, response);

        return response;
    }

    private Company requestToEntity (CompanyRequest request, Company company){
        company.setContact(request.getContact());
        company.setLocation(request.getLocation());
        company.setName(request.getName());
        company.setVacants(new ArrayList<>());
        return company;
    }

    private Company find(String id){
        return this.companyRepository.findById(id).orElseThrow(()-> new IdNotFoundException("Company"));
    }
    
}
