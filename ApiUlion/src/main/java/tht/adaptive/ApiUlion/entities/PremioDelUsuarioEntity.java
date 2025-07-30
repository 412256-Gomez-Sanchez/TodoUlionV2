package tht.adaptive.ApiUlion.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
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

    @Field(name = "id_premio")
    private String idPremio;

    private String duenio;

    private String codigo;

    @Field(name="fecha_comprado")
    private LocalDate fechaComprado;

    @Field(name="fecha_canjeado")
    private LocalDate fechaCanjeado;
}
