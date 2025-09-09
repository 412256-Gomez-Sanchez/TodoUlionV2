package tht.adaptive.ApiUlion.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.http.HttpStatus;
import tht.adaptive.ApiUlion.DTOs.UsuarioDto;
import tht.adaptive.ApiUlion.configs.exceptions.BusinessException;
import tht.adaptive.ApiUlion.services.EmpresaService;

import java.util.List;


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

    private EmpresaEntity empresa;

    @Field(name="premios_del_usuario_empresas")
    private List<EmpresaEntity> premiosDelUsuarioEmpresas;

    public UsuarioEntity(UsuarioDto usuarioDto){
        this.contrasenia= usuarioDto.getContrasenia();
        this.nombre= usuarioDto.getNombre();
        this.telefono= usuarioDto.getTelefono();
        if(usuarioDto.getEmail()!=null && !usuarioDto.getEmail().isBlank()){
            this.eMail= usuarioDto.getEmail();
        }
        if(usuarioDto.getResponsabilidad()!=null){
            //verifico que la responsabilidad sea alguna de las 3
            if(!usuarioDto.getResponsabilidad().equals("administrador")
                    &&!usuarioDto.getResponsabilidad().equals("editor de preguntas")
                    &&!usuarioDto.getResponsabilidad().equals("editor parcial de preguntas")){
                throw new BusinessException(HttpStatus.BAD_REQUEST,"la responsabilidad no puede ser otra mas que: administrador, editor de preguntas o editor parcial de preguntas");
            }
        }
    }
}
