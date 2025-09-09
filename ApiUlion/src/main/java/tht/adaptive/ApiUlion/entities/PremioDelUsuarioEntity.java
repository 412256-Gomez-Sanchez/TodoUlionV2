package tht.adaptive.ApiUlion.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@Document(collection = "premios_del_usuario")
public class PremioDelUsuarioEntity {
    @Id
    private String id;

    private PremioEntity premio;

    private String codigo;

    @Field(name="fecha_comprado")
    private LocalDate fechaComprado;

    @Field(name="fecha_canjeado")
    private LocalDate fechaCanjeado;
}
