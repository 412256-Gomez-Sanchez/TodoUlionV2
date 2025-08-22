package tht.adaptive.ApiUlion.services;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import tht.adaptive.ApiUlion.DTOs.UsuarioDto;
import tht.adaptive.ApiUlion.configs.exceptions.BusinessException;
import tht.adaptive.ApiUlion.entities.UsuarioEntity;
import tht.adaptive.ApiUlion.repositories.UsuarioRepository;

@Service
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final EmpresaService empresaService;

    public UsuarioService(UsuarioRepository usuarioRepository, EmpresaService empresaService) {
        this.usuarioRepository = usuarioRepository;
        this.empresaService = empresaService;
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

    //no debe usarse en un endpoint
    public UsuarioEntity getUsuarioById(String id){
        return usuarioRepository.findById(id).orElseThrow(()->
                new BusinessException(HttpStatus.NOT_FOUND,"usuario no encontrado"));
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
}
