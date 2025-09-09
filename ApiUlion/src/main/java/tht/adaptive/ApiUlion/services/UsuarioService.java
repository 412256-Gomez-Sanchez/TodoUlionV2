package tht.adaptive.ApiUlion.services;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;
import tht.adaptive.ApiUlion.DTOs.*;
import tht.adaptive.ApiUlion.compartidos.GenerarString;
import tht.adaptive.ApiUlion.configs.exceptions.BusinessException;
import tht.adaptive.ApiUlion.entities.*;
import tht.adaptive.ApiUlion.repositories.UsuarioRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final EmpresaService empresaService;
    private final PremioService premioService;
    private final TransaccionService transaccionService;

    public UsuarioService(UsuarioRepository usuarioRepository, EmpresaService empresaService, PremioService premioService, TransaccionService transaccionService) {
        this.usuarioRepository = usuarioRepository;
        this.empresaService = empresaService;
        this.premioService = premioService;
        this.transaccionService = transaccionService;
    }

    public UsuarioDto login(UsuarioDto request){
        if(request.getContrasenia()==null || request.getContrasenia().isBlank()){
            throw new BusinessException(HttpStatus.BAD_REQUEST,"contrasenia no puede ser nulo");
        }

        UsuarioEntity usuarioEntity;
        if(request.getTelefono()!=null && !request.getTelefono().isBlank()){
            System.out.println("buscado con telefono: "+request.getTelefono()+", contrasenia: "+request.getContrasenia());
            usuarioEntity = usuarioRepository.findByTelefonoAndContrasenia(request.getTelefono(), request.getContrasenia()).orElseThrow(()->
                    new BusinessException(HttpStatus.NOT_FOUND,"usuario no encontrado")
            );
        }
        else if(request.getNombre()!=null && !request.getNombre().isBlank()){
            System.out.println("buscado con nombre: "+request.getNombre()+", contrasenia: "+request.getContrasenia());
            usuarioEntity = usuarioRepository.findByNombreAndContrasenia(request.getNombre(), request.getContrasenia()).orElseThrow(()->
                new BusinessException(HttpStatus.NOT_FOUND,"usuario no encontrado")
            );
        }
        else{
            throw new BusinessException(HttpStatus.BAD_REQUEST,"telefono o nombre debe no ser nulo");
        }
        
        return new UsuarioDto(usuarioEntity);
    }

    public UsuarioDto signIn(UsuarioDto request){
        //verifico que ningun campo critico venga nulo
        if(request.getNombre()==null || request.getTelefono()==null|| request.getContrasenia()==null
                ||request.getNombre().isBlank() || request.getTelefono().isBlank() || request.getContrasenia().isBlank()){
            throw new BusinessException(HttpStatus.BAD_REQUEST,"nombre, telefono y contrasenia no pueden ser nulos ni estar vacios");
        }
        //verifico que el tamaño de la contraseña sea valido
        if(request.getContrasenia().length()<8){
            throw new BusinessException(HttpStatus.BAD_REQUEST,"la contrasenia no puede tener menos de 8 caracteres");
        }
        //verifico que el tamaño del numero sea valido
        if(request.getTelefono().length()>15){
            throw new BusinessException(HttpStatus.BAD_REQUEST,"el telefono no puede tener mas de 15 digitos");
        }
        //verificar que el telefono tenga solo numeros
        UsuarioEntity usuarioEntity=new UsuarioEntity(request);
        if(request.getEmpresa()!=null){
            usuarioEntity.setEmpresa(empresaService.create(request.getEmpresa()));
        }
        return new UsuarioDto(usuarioRepository.save(usuarioEntity));
    }

    public UsuarioDto getMonedas(String id){
        UsuarioDto usuarioDto = new UsuarioDto();
        usuarioDto.setMonedas(usuarioRepository.findById(id).orElseThrow(()->
                new BusinessException(HttpStatus.NOT_FOUND,"el usuario no existe")).getMonedas()
        );
        return usuarioDto;
    }

    public void guardarLogoEmpresa(String id, MultipartFile logo){
        if(id==null || id.isEmpty()){
            throw new BusinessException(HttpStatus.BAD_REQUEST,"el id del usuario no puede ser nulo ni estar vacio");
        }
        UsuarioEntity usuarioEntity=usuarioRepository.findById(id).orElseThrow(()->
                new BusinessException(HttpStatus.NOT_FOUND,"usuario no encontrado"));

        usuarioEntity.getEmpresa().setNombreLogo(empresaService.guardarLogo(logo));

        usuarioRepository.save(usuarioEntity);
    }

    public void createUpdatePremio(String id, PremioDto premioDto){
        premioService.createUpdateValidation(premioDto);

        UsuarioEntity usuarioEntity = usuarioRepository.findById(id).orElseThrow(()->
                new BusinessException(HttpStatus.NOT_FOUND,"usuario no encontrado"));

        if(usuarioEntity.getEmpresa()==null){
            throw new BusinessException(HttpStatus.BAD_REQUEST,"el usuario no tiene una empresa vinculada");
        }
        //verificar si el dto viene con id, si tiene, hay que actualizar
        if(premioDto.getId()==null || premioDto.getId().isEmpty()){
            //crear
            if(usuarioEntity.getEmpresa().getPremios()==null){
                usuarioEntity.getEmpresa().setPremios(new ArrayList<>());
            }
            usuarioEntity.getEmpresa().getPremios().add(new PremioEntity(premioDto));
        }
        else{
            //actualizar
            boolean flag=false;
            for(PremioEntity premioEntity:usuarioEntity.getEmpresa().getPremios()){
                if(premioEntity.getId().equals(premioDto.getId())){
                    premioEntity.setPremio(premioDto.getPremio());
                    premioEntity.setCantidadDisponible(premioDto.getCantidadDisponible());
                    premioEntity.setCantidadPorMes(premioDto.getCantidadPorMes());
                    premioEntity.setFechaInicio(premioDto.getFechaInicio());
                    premioEntity.setFechaFin(premioDto.getFechaFin());
                    flag=true;
                    break;
                }
            }
            if(!flag){
                throw new BusinessException(HttpStatus.BAD_REQUEST,"no se encontro el premio a actualizar");
            }
        }
        usuarioRepository.save(usuarioEntity);
    }

    public List<EmpresaDto> getAllPremios(){
        List<UsuarioEntity> usuarioEntities=usuarioRepository.findByEmpresaPremiosNotEmpty(LocalDate.now());
        List<EmpresaDto> empresaDtos=new ArrayList<>();
        for(UsuarioEntity usuario:usuarioEntities){
            empresaDtos.add(new EmpresaDto(usuario.getEmpresa()));
        }

        return empresaDtos;
    }

    public UsuarioEntity getRandomEmpresaWithRandomPremio(){
        return usuarioRepository.findRandomEmpresaWithRandomPremio(LocalDate.now()).orElseThrow(()->
                new BusinessException(HttpStatus.NOT_FOUND,"No existen premios en la base de datos"));
    }

    public void doTransaccion(DoTransaccionDto doTransaccionDto){
        TransaccionDto transaccionDto=transaccionService.getById(doTransaccionDto.getIdTransaccion());
        UsuarioEntity usuarioEntity=usuarioRepository.findById(doTransaccionDto.getIdUsuario()).orElseThrow(()->
                new BusinessException(HttpStatus.NOT_FOUND,"usuario no encontrado"));

        int valorFinal=0;
        if(transaccionDto.getOferta()!=null){
            valorFinal=transaccionDto.getPrecio()*(transaccionDto.getOferta()/100);
        }
        else{
            valorFinal=transaccionDto.getPrecio();
        }

        if(valorFinal+usuarioEntity.getMonedas()<0){
            throw new BusinessException(HttpStatus.PAYMENT_REQUIRED,"el usuario no tiene las monedas suficientes");
        }

        usuarioEntity.setMonedas(usuarioEntity.getMonedas()+valorFinal);
        //elegir premio random y asignarlo a la lista del usuario
        if(transaccionDto.getId().equals("68bf48c180abc5781ddf1ec2"))//el string corresponde al id del tipo transaccion de premio
        {
            UsuarioEntity usuarioEmpresaEntity = getRandomEmpresaWithRandomPremio();
            usuarioEmpresaEntity.getEmpresa().getPremios().get(0).setCantidadDisponible(usuarioEmpresaEntity.getEmpresa().getPremios().get(0).getCantidadDisponible()-1);
            usuarioRepository.save(usuarioEmpresaEntity);
            //convierto en nulos los valores que no me interesan guardar
            usuarioEmpresaEntity.getEmpresa().getPremios().get(0).setFechaInicio(null);
            usuarioEmpresaEntity.getEmpresa().getPremios().get(0).setCantidadPorMes(null);
            usuarioEmpresaEntity.getEmpresa().getPremios().get(0).setCantidadDisponible(null);

            PremioDelUsuarioEntity pdue=new PremioDelUsuarioEntity();
            pdue.setFechaComprado(LocalDate.now());
            pdue.setCodigo(GenerarString.generateRandomString(6));
            pdue.setPremio(usuarioEmpresaEntity.getEmpresa().getPremios().get(0));

            boolean flag=false;
            if(usuarioEntity.getPremiosDelUsuarioEmpresas()==null) {
                usuarioEntity.setPremiosDelUsuarioEmpresas(new ArrayList<>());
            }
            else{
                for(EmpresaEntity ee:usuarioEntity.getPremiosDelUsuarioEmpresas()){
                    if(ee.getId().equals(usuarioEmpresaEntity.getEmpresa().getId())){
                        ee.getPremiosDelUsuario().add(pdue);
                        flag=true;
                        break;
                    }
                }
            }
            if(!flag){
                usuarioEntity.getPremiosDelUsuarioEmpresas().add(usuarioEmpresaEntity.getEmpresa());
            }
        }
        usuarioRepository.save(usuarioEntity);

    }

}
