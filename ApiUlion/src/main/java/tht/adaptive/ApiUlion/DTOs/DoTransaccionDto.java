package tht.adaptive.ApiUlion.DTOs;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DoTransaccionDto {
    private String idUsuario;
    private String idTransaccion;
}
