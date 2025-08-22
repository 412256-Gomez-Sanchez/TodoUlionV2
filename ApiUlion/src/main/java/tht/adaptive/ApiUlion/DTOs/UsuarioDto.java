package tht.adaptive.ApiUlion.DTOs;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;
import tht.adaptive.ApiUlion.entities.UsuarioEntity;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UsuarioDto {//esta clase se usa tanto como para iniciar sesion como para crear un usuario
    private String id;
    private String nombre;
    private String contrasenia;
    private String telefono;
    private Integer monedas;
    private EmpresaDto empresa;
    private String email;//puede ser nulo
    private String responsabilidad;//ESTA VARIABLE NO DEBE SER COMPLETADA SI EL USUARIO NO TENDRA RESPONSABILIDAD
    //responsabilidades esperadas: administrador, editor de preguntas, editor parcial de preguntas

    public UsuarioDto(UsuarioEntity ue){
        this.id=ue.getId();
        this.monedas=ue.getMonedas();
        if(ue.getEmpresa()!=null){
            this.empresa=new EmpresaDto(ue.getEmpresa());
        }
    }
}
