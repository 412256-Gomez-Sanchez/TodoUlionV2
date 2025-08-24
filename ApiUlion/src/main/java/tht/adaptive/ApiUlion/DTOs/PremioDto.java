package tht.adaptive.ApiUlion.DTOs;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;
import tht.adaptive.ApiUlion.entities.PremioEntity;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PremioDto {
    private String id;
    private String premio;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private int cantidadDisponible;
    private Integer cantidadPorMes;//fijo, sirve para recetear 'cantidadDisponible'
    //datos de premiosDelUsuario
    private LocalDate fechaComprado;
    private LocalDate fechaReclamado;
    private String codigo;
    private String usuario;

    public PremioDto(PremioEntity pe){
        this.id=pe.getId();
        this.premio=pe.getPremio();
        this.fechaInicio=pe.getFechaInicio();
        this.fechaFin=pe.getFechaFin();
        this.cantidadDisponible=pe.getCantidadDisponible();
        this.cantidadPorMes=pe.getCantidadPorMes();
    }
}
