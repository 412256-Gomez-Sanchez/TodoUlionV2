package tht.adaptive.ApiUlion.controllers;

import org.springframework.web.bind.annotation.*;
import tht.adaptive.ApiUlion.DTOs.PreguntaDto;
import tht.adaptive.ApiUlion.services.PreguntaService;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("api/v2/preguntas")
public class PreguntaController {

    private final PreguntaService preguntaService;

    public PreguntaController(PreguntaService preguntaService) {
        this.preguntaService = preguntaService;
    }

    @PostMapping("/administrador")
    public void createModify(@RequestBody PreguntaDto preguntaDto){
        System.out.println("pregunta creada o modificada");
        preguntaService.createModify(preguntaDto);
    }

    @PostMapping("/{tema}")
    public PreguntaDto getRandom(@PathVariable String tema, @RequestBody List<String> idsUsados){
        System.out.println("getRandom");
        return preguntaService.getRandom(tema,idsUsados);
    }
}
