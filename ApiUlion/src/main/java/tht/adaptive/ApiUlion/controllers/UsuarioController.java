package tht.adaptive.ApiUlion.controllers;

import org.springframework.web.bind.annotation.*;
import tht.adaptive.ApiUlion.DTOs.requests.UsuarioRequest;
import tht.adaptive.ApiUlion.DTOs.responses.UsuarioResponse;
import tht.adaptive.ApiUlion.services.UsuarioService;

@RestController
@RequestMapping("api/v2/usuarios")
public class UsuarioController {
    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping("/login")
    public UsuarioResponse login(@RequestBody UsuarioRequest request){
        return usuarioService.login(request);//se permite el inicio de sesion con telefono y nombre de usuario
    }

    @PostMapping
    public UsuarioResponse signUp(@RequestBody UsuarioRequest request){
        return usuarioService.signIn(request);
    }
}
