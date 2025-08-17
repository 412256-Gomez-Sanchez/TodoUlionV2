package tht.adaptive.ApiUlion.controllers;

import org.springframework.web.bind.annotation.*;
import tht.adaptive.ApiUlion.DTOs.EmpresaDto;
import tht.adaptive.ApiUlion.DTOs.UsuarioDto;
import tht.adaptive.ApiUlion.services.EmpresaService;

@CrossOrigin
@RestController
@RequestMapping("api/v2/empresas")
public class EmpresaController {
    private final EmpresaService empresaService;

    public EmpresaController(EmpresaService empresaService) {
        this.empresaService = empresaService;
    }

//    @PostMapping
//    public UsuarioDto create(@RequestBody EmpresaDto empresaDto){
//        return empresaService.create(empresaDto);
//    }


}
