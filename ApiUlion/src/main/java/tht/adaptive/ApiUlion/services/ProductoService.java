package tht.adaptive.ApiUlion.services;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import tht.adaptive.ApiUlion.DTOs.ProductoDto;
import tht.adaptive.ApiUlion.configs.exceptions.BusinessException;
import tht.adaptive.ApiUlion.entities.ProductoEntity;
import tht.adaptive.ApiUlion.repositories.ProductoRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductoService {

    private final ProductoRepository productoRepository;

    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    public void saveProducto(ProductoDto productoDto){
        //validacion de oferta
        if(productoDto.getOferta()!=null && productoDto.getOferta()<0){
            throw new BusinessException(HttpStatus.BAD_REQUEST,"el campo 'oferta' no puede ser negativo");
        }
        if(productoDto.getFechaInicio()!=null && productoDto.getFechaFin()!=null && productoDto.getFechaInicio().isAfter(productoDto.getFechaFin())){
            throw new BusinessException(HttpStatus.BAD_REQUEST,"la fechaFin no puede ser anterior a la fechaInicio");
        }
        if(productoDto.getFechaFin()!=null && productoDto.getFechaFin().isBefore(LocalDate.now())){
            throw new BusinessException(HttpStatus.BAD_REQUEST,"la fechaFin no puede ser anterior a la fecha actual");
        }


        ProductoEntity productoEntity;
        if(productoDto.getId()==null || productoDto.getId().isEmpty()){
            //save
            if(productoDto.getDescripcion()==null || productoDto.getDescripcion().isEmpty()){
                throw  new BusinessException(HttpStatus.BAD_REQUEST,"al guardar debe haber una descripcion del producto");
            }
            if(productoDto.getPrecio()==null){
                throw new BusinessException(HttpStatus.BAD_REQUEST,"al guardar debe haber un precio del producto");
            }
            int flag=0;
            if(productoDto.getOferta()!=null){
                flag++;
            }
            if(productoDto.getFechaInicio()!=null){
                flag++;
            }
            if(productoDto.getFechaFin()!=null){
                flag++;
            }
            if(flag!=3 && flag!=0){
                throw new BusinessException(HttpStatus.BAD_REQUEST,"al guardar con oferta los campos: oferta, fechaInicio y fechaFin, no pueden ser nulos");
            }
            productoEntity=new ProductoEntity(productoDto);
        }
        else{
            //update
            productoEntity=productoRepository.findById(productoDto.getId()).orElseThrow(()->
                    new BusinessException(HttpStatus.NOT_FOUND,"producto no encontrado"));

            if(productoDto.getDescripcion()!=null && !productoDto.getDescripcion().isEmpty()){
                productoEntity.setDescripcion(productoDto.getDescripcion());
            }
            if(productoDto.getPrecio()!=null){
                productoEntity.setPrecio(productoDto.getPrecio());
            }
            if(productoDto.getOferta()!=null){
                if(productoDto.getOferta()==0){//si la oferta es 0, se borra la oferta de la base de datos
                    productoEntity.setOferta(null);
                    productoEntity.setFechaInicio(null);
                    productoEntity.setFechaFin(null);
                }
                else{
                    productoEntity.setOferta(productoDto.getOferta());
                }
            }

            if(productoDto.getFechaInicio()!=null){
                productoEntity.setFechaInicio(productoDto.getFechaInicio());
            }
            if(productoDto.getFechaFin()!=null){
                productoEntity.setFechaFin(productoDto.getFechaFin());
            }
            //comprobacion: oferta sin fechas y fechas correctas
            if(productoEntity.getFechaInicio()==null && productoEntity.getOferta()!=null){
                throw new BusinessException(HttpStatus.BAD_REQUEST,"se esta intentando actualizar el campo 'oferta' sin pasar una fecha de inicio en la request, FALTA 'fechaInicio' EN LA BASE DE DATOS");
            }
            if(productoEntity.getFechaFin()==null && productoEntity.getOferta()!=null){
                throw new BusinessException(HttpStatus.BAD_REQUEST,"se esta intentando actualizar el campo 'oferta' sin pasar una fecha de fin en la request, FALTA 'fechaFin' EN LA BASE DE DATOS");
            }
            if(productoEntity.getFechaInicio()!=null && productoEntity.getFechaFin()!=null
                    && productoEntity.getFechaInicio().isAfter(productoEntity.getFechaFin())
                    && productoEntity.getOferta()!=null){
                throw new BusinessException(HttpStatus.CONFLICT,"la fecha de fin no puede ser anterior a la fecha de inicio, CONFLICTO EN LA BASE DE DATOS");
            }
        }
        productoRepository.save(productoEntity);
    }

    public List<ProductoDto> getAll(){
        List<ProductoDto> productoDtos = new ArrayList<>();
        List<ProductoEntity> productoEntities = productoRepository.findAll();
        for(ProductoEntity productoEntity:productoEntities){
            ProductoDto productoDto=new ProductoDto(productoEntity);
            productoDtos.add(productoDto);
        }
        return productoDtos;
    }

    public ProductoDto getById(String id){
        if(id==null || id.isEmpty()){
            throw new BusinessException(HttpStatus.BAD_REQUEST,"el 'id' no puede ser nulo ni estar vacio");
        }
        return new ProductoDto(productoRepository.findById(id).orElseThrow(()->
                new BusinessException(HttpStatus.NOT_FOUND,"no se encontro el producto con id: "+id)));
    }
}
