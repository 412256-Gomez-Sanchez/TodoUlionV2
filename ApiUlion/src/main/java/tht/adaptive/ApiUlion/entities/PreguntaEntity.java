package tht.adaptive.ApiUlion.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Data
@NoArgsConstructor
@Document(collection = "preguntas")
public class PreguntaEntity {
    @Id
    private String id;

    private String pregunta;

    private boolean estaActiva;

    @Field(name="patrocinada_por")
    private String patrocinadaPor;

    private List<String> temas;

    private List<RespuestaEntity> respuestas;
}
