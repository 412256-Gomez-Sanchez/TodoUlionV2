package tht.adaptive.ApiUlion.DTOs.responses;

import lombok.Data;
import lombok.NoArgsConstructor;
import tht.adaptive.ApiUlion.entities.UsuarioEntity;

@Data
@NoArgsConstructor
public class UsuarioResponse {//se utiliza al iniciar sesion y al actualizar las monedas en front
    private String id;
    private int monedas;

    public UsuarioResponse(UsuarioEntity usuarioEntity){
        this.id= usuarioEntity.getId();
        this.monedas= usuarioEntity.getMonedas();
    }
}
