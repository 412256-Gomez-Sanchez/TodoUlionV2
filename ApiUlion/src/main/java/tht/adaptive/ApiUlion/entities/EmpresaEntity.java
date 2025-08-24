package tht.adaptive.ApiUlion.entities;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import tht.adaptive.ApiUlion.DTOs.EmpresaDto;

import java.util.List;

@Data
@NoArgsConstructor
@Document(collection="empresas")
public class EmpresaEntity {

    private String nombre;

    @Field(name="nombre_logo")
    private String nombreLogo;

    private String detalles;//para que las empresas especifiquen como los clientes puede comunicarse con ellas

    private List<PremioEntity> premios;

    public EmpresaEntity(EmpresaDto ed){
        this.nombre=ed.getNombre();
        this.detalles=ed.getDetalles();
    }
}
