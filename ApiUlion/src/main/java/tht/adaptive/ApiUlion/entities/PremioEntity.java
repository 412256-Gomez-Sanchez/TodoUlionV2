package tht.adaptive.ApiUlion.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@Document(collection = "premios")
public class PremioEntity {
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

    @Field(name="es_cantidad_por_mes")
    private Boolean esCantidadPorMes;
}
