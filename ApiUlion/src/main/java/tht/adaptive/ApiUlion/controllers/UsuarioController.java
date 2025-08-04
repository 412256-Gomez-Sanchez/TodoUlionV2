package tht.adaptive.ApiUlion.controllers;

import org.springframework.web.bind.annotation.*;
import tht.adaptive.ApiUlion.DTOs.requests.UsuarioRequest;
import tht.adaptive.ApiUlion.DTOs.responses.UsuarioResponse;
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
    public UsuarioResponse login(@RequestBody UsuarioRequest request){
        System.out.println("login");
        return usuarioService.login(request);//se permite el inicio de sesion con telefono y nombre de usuario
    }

    @PostMapping
    public UsuarioResponse signUp(@RequestBody UsuarioRequest request){
        System.out.println("singUp");
        return usuarioService.signIn(request);
    }
    
    @GetMapping("/{id}/monedas")
    public UsuarioResponse getMonedas(@PathVariable String id){
        System.out.println("getMonedas");
        return usuarioService.getMonedas(id);
    }
}
