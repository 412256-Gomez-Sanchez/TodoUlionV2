package tht.adaptive.ApiUlion.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import tht.adaptive.ApiUlion.DTOs.requests.UsuarioRequest;

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
        if(!usuarioRequest.getEmail().isBlank()){
            this.eMail=usuarioRequest.getEmail();
        }
        if(!usuarioRequest.getResponsabilidad().isBlank()){
            this.responsabilidad=usuarioRequest.getResponsabilidad();
        }
    }
}
