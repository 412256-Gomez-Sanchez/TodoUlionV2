package tht.adaptive.ApiUlion.services;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import tht.adaptive.ApiUlion.DTOs.requests.UsuarioRequest;
import tht.adaptive.ApiUlion.DTOs.responses.UsuarioResponse;
import tht.adaptive.ApiUlion.configs.exceptions.BusinessException;
import tht.adaptive.ApiUlion.entities.UsuarioEntity;
import tht.adaptive.ApiUlion.repositories.UsuarioRepotisory;

@Service
public class UsuarioService {
    private final UsuarioRepotisory usuarioRepotisory;

    public UsuarioService(UsuarioRepotisory usuarioRepotisory) {
        this.usuarioRepotisory = usuarioRepotisory;
    }

    public UsuarioResponse login(UsuarioRequest request){
        if(request.getContrasenia()==null || request.getContrasenia().isBlank()){
            throw new BusinessException(HttpStatus.BAD_REQUEST,"contrasenia no puede ser nulo");
        }

        UsuarioEntity usuarioEntity;
        if(request.getTelefono()!=null && !request.getTelefono().isBlank()){
            System.out.println("buscado con telefono: "+request.getTelefono()+", contrasenia: "+request.getContrasenia());
            usuarioEntity = usuarioRepotisory.findByTelefonoAndContrasenia(request.getTelefono(), request.getContrasenia()).orElseThrow(()->
                    new BusinessException(HttpStatus.NOT_FOUND,"usuario no encontrado")
            );
        }
        else if(request.getNombre()!=null && !request.getNombre().isBlank()){
            System.out.println("buscado con nombre: "+request.getNombre()+", contrasenia: "+request.getContrasenia());
            usuarioEntity=usuarioRepotisory.findByNombreAndContrasenia(request.getNombre(), request.getContrasenia()).orElseThrow(()->
                new BusinessException(HttpStatus.NOT_FOUND,"usuario no encontrado")
            );
        }
        else{
            throw new BusinessException(HttpStatus.BAD_REQUEST,"telefono o nombre debe no ser nulo");
        }

        return new UsuarioResponse(usuarioEntity);

    }

    public UsuarioResponse signIn(UsuarioRequest request){
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

        //verifico si existe el campo responsabilidad
        if(request.getResponsabilidad()!=null){
            //verifico que la responsabilidad sea alguna de las 3
            if(!request.getResponsabilidad().equals("administrador")
                    &&!request.getResponsabilidad().equals("editor de preguntas")
                    &&!request.getResponsabilidad().equals("editor parcial de preguntas")){
                throw new BusinessException(HttpStatus.BAD_REQUEST,"la responsabilidad no puede ser otra mas que: administrador, editor de preguntas o editor parcial de preguntas");
            }
        }
        return new UsuarioResponse(usuarioRepotisory.save(new UsuarioEntity(request)));
    }
}
