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
    //private String premio;
    //private String empresa;//no esta si este objeto esta dentro de "EmpresaDto"
    //private String urlLogo;//no esta si este objeto esta dentro de "EmpresaDto"
    //private LocalDate fechaInicio;
    //private LocalDate fechaFin;
    //private int cantidad;
    //private Integer cantidadPorMes;//fijo, no cambia

    public void createUpdateValidation(PremioDto premioDto){
        if(premioDto.getPremio()==null || premioDto.getPremio().isEmpty()){
            throw new BusinessException(HttpStatus.BAD_REQUEST,"el string 'premio' no puede ser nulo ni estar vacio");
        }
        if(premioDto.getFechaInicio()==null){
            throw new BusinessException(HttpStatus.BAD_REQUEST,"el premio debe tener una fecha de inicio");
        }
        if(premioDto.getFechaFin()==null){
            throw new BusinessException(HttpStatus.BAD_REQUEST,"el premio debe tener una fecha de fin");
        }
        if(premioDto.getFechaFin().isBefore(LocalDate.now()) || premioDto.getFechaFin().equals(LocalDate.now())){
            throw new BusinessException(HttpStatus.BAD_REQUEST,"el premio no puede vencer en una fecha anterior a mañana");
        }
        if(premioDto.getFechaFin().isBefore(premioDto.getFechaInicio())|| premioDto.getFechaFin().equals(premioDto.getFechaInicio())){
            throw new BusinessException(HttpStatus.BAD_REQUEST,"el premio no puede vencer ni antes ni el mismo dia que su propia fecha de inicio");
        }
        if(premioDto.getCantidadDisponible()<=0 || (premioDto.getCantidadPorMes()!=null && premioDto.getCantidadPorMes()<=0)){
            throw new BusinessException(HttpStatus.BAD_REQUEST,"el premio no puede contener una cantidad (disponible o por mes) menor a 1");
        }
    }

}
