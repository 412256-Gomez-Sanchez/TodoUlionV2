package tht.adaptive.ApiUlion.controllers;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tht.adaptive.ApiUlion.DTOs.UsuarioDto;
import tht.adaptive.ApiUlion.services.UsuarioService;

@CrossOrigin
@RestController
@RequestMapping("api/v2/usuarios")
public class UsuarioController {
    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping("/login")
    public UsuarioDto login(@RequestBody UsuarioDto request){
        System.out.println("login");
        return usuarioService.login(request);//se permite el inicio de sesion con telefono y nombre de usuario
    }

    @PostMapping
    public UsuarioDto signUp(@RequestBody UsuarioDto request){
        System.out.println("singUp");
        return usuarioService.signIn(request);
    }
    
    @GetMapping("/{id}/monedas")
    public UsuarioDto getMonedas(@PathVariable String id){
        System.out.println("getMonedas");
        return usuarioService.getMonedas(id);
    }

    @PostMapping("/{id}/update/logo")
    public void guardarLogo(@PathVariable String id,@RequestParam MultipartFile logo){
        usuarioService.guardarLogoEmpresa(id,logo);
    }
}
