package tht.adaptive.ApiUlion.DTOs;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PremioDto {
    private String id;
    private String premio;
    private String empresa;//no esta si este objeto esta dentro de "EmpresaDto"
    private String urlLogo;//no esta si este objeto esta dentro de "EmpresaDto"
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private int cantidad;
    private Boolean esCantidadPorMes;
    //datos de premiosDelUsuario
    private LocalDate fechaComprado;
    private LocalDate fechaReclamado;
    private String codigo;
    private String usuario;
}
