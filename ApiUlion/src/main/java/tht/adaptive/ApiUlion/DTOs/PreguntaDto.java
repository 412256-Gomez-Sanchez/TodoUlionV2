package tht.adaptive.ApiUlion.DTOs;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Field;
import tht.adaptive.ApiUlion.entities.PreguntaEntity;
import tht.adaptive.ApiUlion.entities.RespuestaEntity;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PreguntaDto {
    private String id;//para modificar, evitar repeticiones al jugar

    private String pregunta;

    private List<RespuestaDto> respuestas = new ArrayList<>();

    private String patrocinadaPor;

    private List<String> temas;//nulo para el jugador

    private Boolean estaDesactivada;//nulo para el jugador

    private List<String> modificaciones;//nulo para el jugador

    public PreguntaDto(PreguntaEntity pe){
        this.id=pe.getId();
        this.pregunta=pe.getPregunta();
        for(RespuestaEntity respuesta : pe.getRespuestas()){
            respuestas.add(new RespuestaDto(respuesta));
        }
        if(pe.getPatrocinadaPor()!=null){
            this.patrocinadaPor=pe.getPatrocinadaPor();
        }

    }
}
