package tht.adaptive.ApiUlion.DTOs.requests;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UsuarioRequest {//esta clase se usa tanto como para iniciar sesion como para crear un usuario

    private String nombre;
    private String contrasenia;
    private String telefono;
    private String email;
    private String responsabilidad;//ESTA VARIABLE NO DEBE SER COMPLETADA SI EL USUARIO NO TENDRA RESPONSABILIDAD
    //responsabilidades esperadas: administrador, editor de preguntas, editor parcial de preguntas
}
