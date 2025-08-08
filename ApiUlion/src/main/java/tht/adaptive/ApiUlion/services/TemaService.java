package tht.adaptive.ApiUlion.services;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import tht.adaptive.ApiUlion.DTOs.TemaDto;
import tht.adaptive.ApiUlion.configs.exceptions.BusinessException;
import tht.adaptive.ApiUlion.entities.TemaEntity;
import tht.adaptive.ApiUlion.repositories.TemaRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class TemaService {
    private final TemaRepository temaRepository;

    public TemaService(TemaRepository temaRepository) {
        this.temaRepository = temaRepository;
    }

    public void create(TemaDto request){
        if(request.getTema()==null||request.getTema().isBlank()){
            throw new BusinessException(HttpStatus.BAD_REQUEST,"el tema no puede ser nulo ni estar vacio");
        }
        request.getTema().toUpperCase();
        TemaEntity temaEntity= new TemaEntity();
        temaEntity.setTema(request.getTema());

        if(request.getEsBuscable()==null || !request.getEsBuscable()){
            temaEntity.setEsBuscable(null);
        }//por default el tema es buscable (ver "TemaEntity")

        temaRepository.save(temaEntity);
    }

    public List<TemaDto> getAll(boolean filtrarNoBuscables){
        List<TemaDto> temaDtos=new ArrayList<>();
        List<TemaEntity> temaEntities = temaRepository.findAll();

        for(TemaEntity temaEntity:temaEntities){
            if(filtrarNoBuscables && temaEntity.getEsBuscable()!=null && temaEntity.getEsBuscable()){
                TemaDto temaDto=new TemaDto();
                temaDto.setTema(temaEntity.getTema());
                temaDtos.add(temaDto);
            }
            else if(!filtrarNoBuscables){
                TemaDto temaDto=new TemaDto();
                temaDto.setTema(temaEntity.getTema());
                temaDto.setEsBuscable(temaEntity.getEsBuscable());
                temaDtos.add(temaDto);
            }
        }
        return temaDtos;
    }

    public void delete(String tema){
        TemaEntity temaEntity = temaRepository.findById(tema).orElseThrow(()->
                new BusinessException(HttpStatus.NOT_FOUND,"el tema no existe"));
        temaRepository.delete(temaEntity);
    }
}
