package tht.adaptive.ApiUlion.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@Document(collection = "respuestas")
public class RespuestaEntity {
    @Id
    private String id;

    private String respuesta;

    private boolean esCorrecta;
}
