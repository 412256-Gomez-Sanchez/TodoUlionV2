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
        if(request.getContrasenia().isBlank()){
            throw new BusinessException(HttpStatus.BAD_REQUEST,"contrasenia no puede ser nulo");
        }

        UsuarioEntity usuarioEntity;
        if(!request.getTelefono().isBlank()){
            System.out.println("buscado con telefono: "+request.getTelefono()+", contrasenia: "+request.getContrasenia());
            usuarioEntity = usuarioRepotisory.findByTelefonoAndContrasenia(request.getTelefono(), request.getContrasenia()).orElseThrow(()->
                    new BusinessException(HttpStatus.NOT_FOUND,"usuario no encontrado")
            );
        }
        else if(!request.getNombre().isBlank()){
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
        if(request.getNombre().isBlank() || request.getTelefono().isBlank() || request.getContrasenia().isBlank()){
            throw new BusinessException(HttpStatus.BAD_REQUEST,"nombre, telefono y contrasenia no pueden ser nulos");
        }
        if(request.getContrasenia().length()<8){
            throw new BusinessException(HttpStatus.BAD_REQUEST,"la contrasenia no puede tener menos de 8 caracteres");
        }
        if(request.getTelefono().length()>15){
            throw new BusinessException(HttpStatus.BAD_REQUEST,"el telefono no puede tener mas de 15 digitos");
        }
        //verificar que el telefono tenga solo numeros

        return new UsuarioResponse(usuarioRepotisory.save(new UsuarioEntity(request)));
    }
}
