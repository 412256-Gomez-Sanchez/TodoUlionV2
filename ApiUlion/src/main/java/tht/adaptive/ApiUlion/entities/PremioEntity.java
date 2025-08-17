package tht.adaptive.ApiUlion.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import tht.adaptive.ApiUlion.DTOs.PremioDto;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@Document(collection = "premios")
public class PremioEntity {
    //private String premio;
    //private String empresa;//no esta si este objeto esta dentro de "EmpresaDto"
    //private String urlLogo;//no esta si este objeto esta dentro de "EmpresaDto"
    //private LocalDate fechaInicio;
    //private LocalDate fechaFin;
    //private int cantidad;
    //private Integer cantidadPorMes;//fijo, no cambia

    @Id
    private String id;

    private String empresa;

    private String premio;

    @Field(name="fecha_inicio")
    private LocalDate fechaInicio;

    @Field(name="fecha_fin")
    private LocalDate fechaFin;

    @Field(name="cantidad_disponible")
    private int cantidadDisponible;

    @Field(name="cantidad_por_mes")
    private Integer cantidadPorMes;//fijo, es para recetear 'cantidadDisponible'

    public PremioEntity(PremioDto premioDto){
        this.empresa=premioDto.getEmpresa();
        this.premio=premioDto.getPremio();
        this.fechaInicio=premioDto.getFechaInicio();
        this.fechaFin=premioDto.getFechaFin();
        this.cantidadDisponible = premioDto.getCantidadDisponible();
        if(premioDto.getCantidadPorMes()!=null){
            this.cantidadPorMes=premioDto.getCantidadPorMes();
        }
    }
}
