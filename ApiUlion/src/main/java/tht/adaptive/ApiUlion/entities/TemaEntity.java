package tht.adaptive.ApiUlion.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@NoArgsConstructor
@Document(collection = "temas")
public class TemaEntity {
    @Id
//    @Indexed(unique = true)
    private String tema;
    //cultura general, patrocinada, arte, historia, etc.

    @Field(name="es_buscable")
    private Boolean esBuscable=true;
}
