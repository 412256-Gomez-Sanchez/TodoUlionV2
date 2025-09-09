package tht.adaptive.ApiUlion.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import tht.adaptive.ApiUlion.DTOs.PremioDto;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@Document(collection = "premios")
public class PremioEntity {

    @Id
    private String id=new ObjectId().toString();

    private String premio;

    @Field(name="fecha_inicio")
    private LocalDate fechaInicio;

    @Field(name="fecha_fin")
    private LocalDate fechaFin;

    @Field(name="cantidad_disponible")
    private Integer cantidadDisponible;

    @Field(name="cantidad_por_mes")
    private Integer cantidadPorMes;//fijo, es para recetear 'cantidadDisponible'

    public PremioEntity(PremioDto premioDto){
        if(premioDto.getId()!=null && !premioDto.getId().isEmpty()){
            this.id=premioDto.getId();
        }

        this.premio=premioDto.getPremio();
        this.fechaInicio=premioDto.getFechaInicio();
        this.fechaFin=premioDto.getFechaFin();
        this.cantidadDisponible = premioDto.getCantidadDisponible();

        if(premioDto.getCantidadPorMes()!=null){
            this.cantidadPorMes=premioDto.getCantidadPorMes();
        }
    }

}
