package tht.adaptive.ApiUlion.services;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import tht.adaptive.ApiUlion.DTOs.PreguntaDto;
import tht.adaptive.ApiUlion.DTOs.RespuestaDto;
import tht.adaptive.ApiUlion.configs.exceptions.BusinessException;
import tht.adaptive.ApiUlion.entities.PreguntaEntity;
import tht.adaptive.ApiUlion.repositories.PreguntaRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PreguntaService {
    private final PreguntaRepository preguntaRepository;

    public PreguntaService(PreguntaRepository preguntaRepository) {
        this.preguntaRepository = preguntaRepository;
    }

    public void createModify(PreguntaDto request){
        if(request.getPregunta()==null || request.getPregunta().isBlank()){
            throw new BusinessException(HttpStatus.BAD_REQUEST,"la pregunta no puede estar vacia ni se nula");
        }
        if(request.getRespuestas()==null || request.getRespuestas().size()<2 || request.getRespuestas().size()>4){
            throw new BusinessException(HttpStatus.BAD_REQUEST,"la pregunta debe tener entre 2 y 4 respuestas");
        }
        int cantidadCorrectas=0;
        for(RespuestaDto respuesta: request.getRespuestas()){
            if(respuesta.getRespuesta()==null || respuesta.getRespuesta().isBlank()){
                throw new BusinessException(HttpStatus.BAD_REQUEST,"ninguna respuesta puede estar vacia ni ser nula");
            }
            if(respuesta.getEsCorrecta()!=null && respuesta.getEsCorrecta()){
                cantidadCorrectas++;
            }
        }
        if(cantidadCorrectas!=1){
            throw new BusinessException(HttpStatus.BAD_REQUEST,"solo 1 respuesta puede ser la correcta");
        }
        if(request.getTemas()==null || request.getTemas().isEmpty()){
            throw new BusinessException(HttpStatus.BAD_REQUEST,"la pregunta debe tener al menos 1 tema");
        }
        if(request.getModificaciones()==null || request.getModificaciones().isEmpty()){
            throw new BusinessException(HttpStatus.BAD_REQUEST,"se debe constatar quien hizo la ultima modificacion");
        }
        if(request.getId()!=null){
            PreguntaEntity preguntaEntity = preguntaRepository.findModificacionesById(request.getId()).orElseThrow(()->
                    new BusinessException(HttpStatus.NOT_FOUND,"pregunta no encontrada"));
            preguntaEntity.getModificaciones().add(request.getModificaciones().get(0));
            request.setModificaciones(preguntaEntity.getModificaciones());
        }

        preguntaRepository.save(new PreguntaEntity(request));
    }

    public PreguntaDto getRandom(String tema, List<String> idsUsados){
        Optional<PreguntaEntity> optional = preguntaRepository.findRandomByTemaSampleExcludingIds(tema, idsUsados);
        if(optional.isEmpty()){
            System.out.println("buscando sin idsUsados");
            return new PreguntaDto(preguntaRepository.findRandomByTemaSampleExcludingIds(tema, new ArrayList<>()).orElseThrow(()->
                    new BusinessException(HttpStatus.NOT_FOUND,"revisar que el tema exista")));
        }
        return new PreguntaDto(optional.get());
    }
}
