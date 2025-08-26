package tht.adaptive.ApiUlion.DTOs;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

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
}
