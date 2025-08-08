package tht.adaptive.ApiUlion.DTOs;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EmpresaDto {
    private String id;
    private String nombre;
    private String logoUrl;
    private List<PremioDto> premios;
    //poner un campo de usuarios asociados?
}
