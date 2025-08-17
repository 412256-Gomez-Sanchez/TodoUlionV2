package tht.adaptive.ApiUlion.services;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import tht.adaptive.ApiUlion.DTOs.EmpresaDto;
import tht.adaptive.ApiUlion.DTOs.PremioDto;
import tht.adaptive.ApiUlion.configs.exceptions.BusinessException;
import tht.adaptive.ApiUlion.entities.PremioEntity;
import tht.adaptive.ApiUlion.repositories.PremioRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class PremioService {
    private final PremioRepository premioRepository;

    //private String premio;
    //private String empresa;//no esta si este objeto esta dentro de "EmpresaDto"
    //private String urlLogo;//no esta si este objeto esta dentro de "EmpresaDto"
    //private LocalDate fechaInicio;
    //private LocalDate fechaFin;
    //private int cantidad;
    //private Integer cantidadPorMes;//fijo, no cambia
    int iOfError=0;//index del error, en caso de lista, facilita encontrar en donde esta el error;

    public PremioService(PremioRepository premioRepository) {
        this.premioRepository = premioRepository;

    }

//    public PremioDto create(PremioDto premioDto){
//        iOfError=0;
//        validateCreate(premioDto);
//
//    }

    public List<PremioDto> create(List<PremioDto> premioDtos){
        iOfError=0;
        List<PremioEntity> premioEntities=new ArrayList<>();
        for(PremioDto premioDto:premioDtos){
            validateCreate(premioDto);
            premioEntities.add(new PremioEntity(premioDto));
        }
        premioEntities = premioRepository.saveAll(premioEntities);

        for(int i=0;i<premioDtos.size()-1;i++){
            premioDtos.get(i).setId(premioEntities.get(i).getId());//le asigno el id al dto
        }

        return premioDtos;
    }

    private void validateCreate(PremioDto premioDto){

        if(premioDto.getPremio()==null || premioDto.getPremio().isEmpty()){
            throw new BusinessException(HttpStatus.BAD_REQUEST,"el premio no puede ser nulo ni estar vacio, index: "+iOfError);
        }
        if(premioDto.getEmpresa()==null || premioDto.getEmpresa().isEmpty()){
            throw new BusinessException(HttpStatus.BAD_REQUEST,"el premio debe tener una empresa asociada, index: "+iOfError);
        }
        if(premioDto.getUrlLogo()==null || premioDto.getUrlLogo().isEmpty()){
            throw new BusinessException(HttpStatus.BAD_REQUEST,"el premio debe contener un logo asociado de la empresa, index: "+iOfError);
        }
        if(premioDto.getFechaInicio()==null){
            throw new BusinessException(HttpStatus.BAD_REQUEST,"el premio debe tener una fecha de inicio, index: "+iOfError);
        }
        if(premioDto.getFechaFin()==null){
            throw new BusinessException(HttpStatus.BAD_REQUEST,"el premio debe tener una fecha de fin, index: "+iOfError);
        }
        if(premioDto.getFechaFin().isBefore(LocalDate.now()) || premioDto.getFechaFin().equals(LocalDate.now())){
            throw new BusinessException(HttpStatus.BAD_REQUEST,"el premio no puede vencer en una fecha anterior a mañana, index: "+iOfError);
        }
        if(premioDto.getFechaFin().isBefore(premioDto.getFechaInicio())|| premioDto.getFechaFin().equals(premioDto.getFechaInicio())){
            throw new BusinessException(HttpStatus.BAD_REQUEST,"el premio no puede vencer ni antes ni el mismo dia que su propia fecha de inicio, index: "+iOfError);
        }
        if(premioDto.getCantidadDisponible()<=0 || (premioDto.getCantidadPorMes()!=null && premioDto.getCantidadPorMes()<=0)){
            throw new BusinessException(HttpStatus.BAD_REQUEST,"el premio no puede contener una cantidad (disponible o por mes) menor a 1");
        }
        iOfError++;
    }

    public List<PremioDto> getPremiosByEmpresa(String nombreEmpresa){
        List<PremioEntity> premioEntities = premioRepository.findByEmpresa(nombreEmpresa);
        List<PremioDto> premioDtos=new ArrayList<>();
        for(PremioEntity premioEntity:premioEntities){
            premioDtos.add(new PremioDto( premioEntity));
        }
        return premioDtos;
    }
}
