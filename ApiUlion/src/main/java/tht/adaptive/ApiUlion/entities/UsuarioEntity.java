package tht.adaptive.ApiUlion.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.http.HttpStatus;
import tht.adaptive.ApiUlion.DTOs.requests.UsuarioRequest;
import tht.adaptive.ApiUlion.configs.exceptions.BusinessException;

@Data
@NoArgsConstructor
@Document(collection = "usuarios")
public class UsuarioEntity {
    @Id
    private String id;

    @Indexed(unique = true)
    private String nombre;

    private String contrasenia;

    private String eMail;

    @Indexed(unique = true)
    private String telefono;

    private int monedas=0;

    private String responsabilidad;//administrador, editor de preguntas, editor parcial de preguntas

    public UsuarioEntity(UsuarioRequest usuarioRequest){
        this.contrasenia= usuarioRequest.getContrasenia();
        this.nombre=usuarioRequest.getNombre();
        this.telefono= usuarioRequest.getTelefono();
        if(usuarioRequest.getEmail()!=null && !usuarioRequest.getEmail().isBlank()){
            this.eMail=usuarioRequest.getEmail();
        }
        if(usuarioRequest.getResponsabilidad()!=null){
            //verifico que la responsabilidad sea alguna de las 3
            if(!usuarioRequest.getResponsabilidad().equals("administrador")
                    &&!usuarioRequest.getResponsabilidad().equals("editor de preguntas")
                    &&!usuarioRequest.getResponsabilidad().equals("editor parcial de preguntas")){
                throw new BusinessException(HttpStatus.BAD_REQUEST,"la responsabilidad no puede ser otra mas que: administrador, editor de preguntas o editor parcial de preguntas");
            }
        }
    }
}
