package tht.adaptive.ApiUlion.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import tht.adaptive.ApiUlion.DTOs.RespuestaDto;

@Data
@NoArgsConstructor
@Document(collection = "respuestas")
public class RespuestaEntity {
    @Id
    private String id;

    private String respuesta;

    private Boolean esCorrecta;

    public RespuestaEntity(RespuestaDto dto){
        if(dto.getId()!=null){
            this.id=dto.getId();
        }
        this.respuesta=dto.getRespuesta();
        if(dto.getEsCorrecta()!=null && dto.getEsCorrecta()){//evita que si es nulo o false se guarde en la BD
            this.esCorrecta=true;
        }
    }
}
