package tht.adaptive.ApiUlion.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import tht.adaptive.ApiUlion.DTOs.PreguntaDto;
import tht.adaptive.ApiUlion.DTOs.RespuestaDto;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Document(collection = "preguntas")
public class PreguntaEntity {
    @Id
    private String id;//

    private String pregunta;//

    private Boolean estaDesactivada;//

    @Field(name="patrocinada_por")
    private String patrocinadaPor;

    private List<String> temas;

    private List<RespuestaEntity> respuestas = new ArrayList<>();

    private List<String> modificaciones;

    public PreguntaEntity(PreguntaDto dto){
        if(dto.getId()!=null){
            this.id=dto.getId();
        }
        this.pregunta=dto.getPregunta();
        for(RespuestaDto respuesta:dto.getRespuestas()){
            this.respuestas.add(new RespuestaEntity(respuesta));
        }
        if(dto.getEstaDesactivada()!=null && dto.getEstaDesactivada()){//evita que si es nulo o false se guarde en la BD
            this.estaDesactivada=true;
        }
        if(dto.getPatrocinadaPor()!=null && !dto.getPatrocinadaPor().isBlank()){//evita que si es nulo o esta vacio se guarde en la BD
            this.patrocinadaPor=dto.getPatrocinadaPor();
        }
        this.temas=dto.getTemas();
        this.modificaciones=dto.getModificaciones();
    }
}
