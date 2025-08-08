package tht.adaptive.ApiUlion.entities;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@NoArgsConstructor
@Document(collection="empresas")
public class EmpresaEntity {
    @Id
    private String id;

    private String nombre;

    @Field(name="logo_url")
    private String logoUrl;

    @Field(name="usuariosAsociados")
    private List<String> usuariosAsociados;
}
