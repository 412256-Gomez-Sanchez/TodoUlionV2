package tht.adaptive.ApiUlion.controllers;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tht.adaptive.ApiUlion.DTOs.EmpresaDto;
import tht.adaptive.ApiUlion.DTOs.PremioDto;
import tht.adaptive.ApiUlion.DTOs.UsuarioDto;
import tht.adaptive.ApiUlion.services.EmpresaService;
import tht.adaptive.ApiUlion.services.UsuarioService;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("api/v2/empresas")
public class EmpresaController {
    private final UsuarioService usuarioService;

    public EmpresaController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping("/{id}/update/logo")//id corresponde al usuario que creo la empresa
    public void guardarLogo(@PathVariable String id,@RequestParam MultipartFile logo){
        usuarioService.guardarLogoEmpresa(id,logo);
    }

    @GetMapping("/premios")
    public List<EmpresaDto> getAll(){//devuelve una lista de empresas que adentro contiene una lista de premios
        //los premios que se devuelven son aquellos que:
        // 1. hay cantidad mayor a 0
        // 2. la fecha actual esta entre las fechas de "inicio" y "fin"
        return usuarioService.getAllPremios();
    }

    @PostMapping("/{id}/premios")
    public void createUpdatePremio(@PathVariable String id, @RequestBody PremioDto premioDto){
        usuarioService.createUpdatePremio(id,premioDto);
    }
}
