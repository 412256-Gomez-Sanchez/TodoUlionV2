package tht.adaptive.ApiUlion.DTOs;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;
import tht.adaptive.ApiUlion.entities.ProductoEntity;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductoDto {
    private String id;
    private String descripcion;
    private Integer precio;

    private Integer oferta;//porcentaje de descuento
    private LocalDate fechaInicio;
    private LocalDate fechaFin;

    public ProductoDto(ProductoEntity pe){
        this.id=pe.getId();
        this.descripcion=pe.getDescripcion();
        this.precio=pe.getPrecio();
        if(pe.getOferta()!=null){
            this.oferta=pe.getOferta();
        }
        if(pe.getFechaInicio()!=null){
            this.fechaInicio=pe.getFechaInicio();
        }
        if(pe.getFechaFin()!=null){
            this.fechaFin=pe.getFechaFin();
        }

    }
}
