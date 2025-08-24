package tht.adaptive.ApiUlion.DTOs;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import tht.adaptive.ApiUlion.entities.EmpresaEntity;
import tht.adaptive.ApiUlion.entities.PremioEntity;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EmpresaDto {
    private String nombre;
    private String urlLogo; //nulo al crear
    private String detalles;
    private List<PremioDto> premios;
    //hacer que se pueda recibir la imagen del logo y que se guarde un una carpeta equivalente a wwwroot

    public EmpresaDto(EmpresaEntity ee){
        this.nombre=ee.getNombre();
        this.urlLogo=ee.getNombreLogo();
        this.premios=new ArrayList<>();
        if(ee.getPremios()!=null){
            for(PremioEntity premioEntity:ee.getPremios()){
                premios.add(new PremioDto(premioEntity));
            }
        }

    }

}
