package tht.adaptive.ApiUlion.services;

import org.springframework.boot.autoconfigure.graphql.GraphQlProperties;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tht.adaptive.ApiUlion.DTOs.EmpresaDto;
import tht.adaptive.ApiUlion.DTOs.PremioDto;
import tht.adaptive.ApiUlion.DTOs.UsuarioDto;
import tht.adaptive.ApiUlion.compartidos.ManejarArchivo;
import tht.adaptive.ApiUlion.configs.exceptions.BusinessException;
import tht.adaptive.ApiUlion.entities.EmpresaEntity;
import tht.adaptive.ApiUlion.entities.UsuarioEntity;
import tht.adaptive.ApiUlion.repositories.EmpresaRepository;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class EmpresaService {
    private final EmpresaRepository empresaRepository;
    private final PremioService premioService;
    ManejarArchivo manejarArchivo=new ManejarArchivo();

    private final String carpetaLogos= "/logosEmpresas/";

    public EmpresaService(EmpresaRepository empresaRepository, PremioService premioService) {
        this.empresaRepository = empresaRepository;
        this.premioService = premioService;
    }

    public EmpresaEntity create(EmpresaDto empresaDto){
        if(empresaDto.getNombre()==null||empresaDto.getNombre().isEmpty()){
            throw new BusinessException(HttpStatus.BAD_REQUEST,"el nombre de la empresa no puede ser nulo ni estar en blanco");
        }
        if(empresaDto.getDetalles()==null||empresaDto.getDetalles().isEmpty()){
            throw new BusinessException(HttpStatus.BAD_REQUEST,"la empresa debe tener un detalle indicando como los jugadores se comunicaran a ella");
        }

        //crear empresa y despues crear usuario
        EmpresaEntity empresaEntity=new EmpresaEntity(empresaDto);

        if(empresaDto.getLogo()!=null && !empresaDto.getLogo().isEmpty()){
            try{
                empresaEntity.setNombreLogo(
                        manejarArchivo.guardar(empresaDto.getLogo(), Paths.get("/logosEmpresas/"))
                );
            }
            catch (IOException e){
                throw new BusinessException(HttpStatus.CONFLICT,"no se pudo guardar la imagen");
            }
        }

        return empresaEntity;
    }

    public EmpresaDto getByName(String nombreEmpresa){
        EmpresaDto empresaDto=new EmpresaDto(empresaRepository.findById(nombreEmpresa).orElseThrow(()->
                new BusinessException(HttpStatus.NOT_FOUND,"empresa no encontrada")));

        empresaDto.setPremios(premioService.getPremiosByEmpresa(nombreEmpresa));

        return empresaDto;
    }
}
