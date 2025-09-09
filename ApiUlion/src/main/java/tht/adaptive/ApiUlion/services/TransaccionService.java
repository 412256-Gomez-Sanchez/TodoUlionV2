package tht.adaptive.ApiUlion.services;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import tht.adaptive.ApiUlion.DTOs.TransaccionDto;
import tht.adaptive.ApiUlion.configs.exceptions.BusinessException;
import tht.adaptive.ApiUlion.entities.TransaccionEntity;
import tht.adaptive.ApiUlion.repositories.TransaccionRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class TransaccionService {

    private final TransaccionRepository transaccionRepository;

    public TransaccionService(TransaccionRepository transaccionRepository) {
        this.transaccionRepository = transaccionRepository;
    }

    public void saveProducto(TransaccionDto transaccionDto){
        //validacion de oferta
        if(transaccionDto.getOferta()!=null && transaccionDto.getOferta()<0){
            throw new BusinessException(HttpStatus.BAD_REQUEST,"el campo 'oferta' no puede ser negativo");
        }
        if(transaccionDto.getFechaInicio()!=null && transaccionDto.getFechaFin()!=null && transaccionDto.getFechaInicio().isAfter(transaccionDto.getFechaFin())){
            throw new BusinessException(HttpStatus.BAD_REQUEST,"la fechaFin no puede ser anterior a la fechaInicio");
        }
        if(transaccionDto.getFechaFin()!=null && transaccionDto.getFechaFin().isBefore(LocalDate.now())){
            throw new BusinessException(HttpStatus.BAD_REQUEST,"la fechaFin no puede ser anterior a la fecha actual");
        }


        TransaccionEntity transaccionEntity;
        if(transaccionDto.getId()==null || transaccionDto.getId().isEmpty()){
            //save
            if(transaccionDto.getDescripcion()==null || transaccionDto.getDescripcion().isEmpty()){
                throw  new BusinessException(HttpStatus.BAD_REQUEST,"al guardar debe haber una descripcion del producto");
            }
            if(transaccionDto.getPrecio()==null){
                throw new BusinessException(HttpStatus.BAD_REQUEST,"al guardar debe haber un precio del producto");
            }
            int flag=0;
            if(transaccionDto.getOferta()!=null){
                flag++;
            }
            if(transaccionDto.getFechaInicio()!=null){
                flag++;
            }
            if(transaccionDto.getFechaFin()!=null){
                flag++;
            }
            if(flag!=3 && flag!=0){
                throw new BusinessException(HttpStatus.BAD_REQUEST,"al guardar con oferta los campos: oferta, fechaInicio y fechaFin, no pueden ser nulos");
            }
            transaccionEntity =new TransaccionEntity(transaccionDto);
        }
        else{
            //update
            transaccionEntity = transaccionRepository.findById(transaccionDto.getId()).orElseThrow(()->
                    new BusinessException(HttpStatus.NOT_FOUND,"producto no encontrado"));

            if(transaccionDto.getDescripcion()!=null && !transaccionDto.getDescripcion().isEmpty()){
                transaccionEntity.setDescripcion(transaccionDto.getDescripcion());
            }
            if(transaccionDto.getPrecio()!=null){
                transaccionEntity.setPrecio(transaccionDto.getPrecio());
            }
            if(transaccionDto.getOferta()!=null){
                if(transaccionDto.getOferta()==0){//si la oferta es 0, se borra la oferta de la base de datos
                    transaccionEntity.setOferta(null);
                    transaccionEntity.setFechaInicio(null);
                    transaccionEntity.setFechaFin(null);
                }
                else{
                    transaccionEntity.setOferta(transaccionDto.getOferta());
                }
            }

            if(transaccionDto.getFechaInicio()!=null){
                transaccionEntity.setFechaInicio(transaccionDto.getFechaInicio());
            }
            if(transaccionDto.getFechaFin()!=null){
                transaccionEntity.setFechaFin(transaccionDto.getFechaFin());
            }
            //comprobacion: oferta sin fechas y fechas correctas
            if(transaccionEntity.getFechaInicio()==null && transaccionEntity.getOferta()!=null){
                throw new BusinessException(HttpStatus.BAD_REQUEST,"se esta intentando actualizar el campo 'oferta' sin pasar una fecha de inicio en la request, FALTA 'fechaInicio' EN LA BASE DE DATOS");
            }
            if(transaccionEntity.getFechaFin()==null && transaccionEntity.getOferta()!=null){
                throw new BusinessException(HttpStatus.BAD_REQUEST,"se esta intentando actualizar el campo 'oferta' sin pasar una fecha de fin en la request, FALTA 'fechaFin' EN LA BASE DE DATOS");
            }
            if(transaccionEntity.getFechaInicio()!=null && transaccionEntity.getFechaFin()!=null
                    && transaccionEntity.getFechaInicio().isAfter(transaccionEntity.getFechaFin())
                    && transaccionEntity.getOferta()!=null){
                throw new BusinessException(HttpStatus.CONFLICT,"la fecha de fin no puede ser anterior a la fecha de inicio, CONFLICTO EN LA BASE DE DATOS");
            }
        }
        transaccionRepository.save(transaccionEntity);
    }

    public List<TransaccionDto> getAll(){
        List<TransaccionDto> transaccionDtos = new ArrayList<>();
        List<TransaccionEntity> productoEntities = transaccionRepository.findAll();
        for(TransaccionEntity transaccionEntity :productoEntities){
            TransaccionDto transaccionDto =new TransaccionDto(transaccionEntity);
            transaccionDtos.add(transaccionDto);
        }
        return transaccionDtos;
    }

    public TransaccionDto getById(String id){
        if(id==null || id.isEmpty()){
            throw new BusinessException(HttpStatus.BAD_REQUEST,"el 'id' no puede ser nulo ni estar vacio");
        }
        return new TransaccionDto(transaccionRepository.findById(id).orElseThrow(()->
                new BusinessException(HttpStatus.NOT_FOUND,"no se encontro el producto con id: "+id)));
    }
}
