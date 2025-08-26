package tht.adaptive.ApiUlion.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import tht.adaptive.ApiUlion.DTOs.ProductoDto;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@Document(collection="productos")
public class ProductoEntity {
    @Id
    private String id;

    private String descripcion;

    private int precio;

    private Integer oferta;//porcentaje de descuento

    @Field(name="fecha_inicio")
    private LocalDate fechaInicio;

    @Field(name="fecha_fin")
    private LocalDate fechaFin;

    public ProductoEntity(ProductoDto productoDto){
        if(productoDto.getId()!=null){
            this.id=productoDto.getId();
        }
        this.descripcion=productoDto.getDescripcion();
        this.precio=productoDto.getPrecio();

        if(productoDto.getOferta()!=null){
            this.oferta=productoDto.getOferta();
            this.fechaInicio=productoDto.getFechaInicio();
            this.fechaFin=productoDto.getFechaFin();
        }
    }
}
