package tht.adaptive.ApiUlion.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import tht.adaptive.ApiUlion.DTOs.TransaccionDto;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@Document(collection="transacciones")
public class TransaccionEntity {
    @Id
    private String id;

    private String descripcion;

    private int precio;

    private Integer oferta;//porcentaje de descuento

    @Field(name="fecha_inicio")
    private LocalDate fechaInicio;

    @Field(name="fecha_fin")
    private LocalDate fechaFin;

    public TransaccionEntity(TransaccionDto transaccionDto){
        if(transaccionDto.getId()!=null){
            this.id= transaccionDto.getId();
        }
        this.descripcion= transaccionDto.getDescripcion();
        this.precio= transaccionDto.getPrecio();

        if(transaccionDto.getOferta()!=null){
            this.oferta= transaccionDto.getOferta();
            this.fechaInicio= transaccionDto.getFechaInicio();
            this.fechaFin= transaccionDto.getFechaFin();
        }
    }
}
