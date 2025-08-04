package tht.adaptive.ApiUlion.DTOs;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;
import tht.adaptive.ApiUlion.entities.RespuestaEntity;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RespuestaDto {
    private String id;

    private String respuesta;

    private Boolean esCorrecta;

    private String idJugador;//se usa para sumarle monedas al jugador

    public RespuestaDto(RespuestaEntity re){
        this.id=re.getId();
        this.respuesta=re.getRespuesta();
        if(re.getEsCorrecta()!=null && re.getEsCorrecta()){
            this.esCorrecta=true;
        }

    }
}
