package tht.adaptive.ApiUlion.controllers;

import org.springframework.web.bind.annotation.*;
import tht.adaptive.ApiUlion.DTOs.TemaDto;
import tht.adaptive.ApiUlion.services.TemaService;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("api/v2/temas")
public class TemaController {

    private final TemaService temaService;

    public TemaController(TemaService temaService) {
        this.temaService = temaService;
    }

    @PostMapping
    public void create(@RequestBody TemaDto request){
        temaService.create(request);
    }

    @GetMapping
    public List<TemaDto> getAll(){
        return temaService.getAll(true);
    }

    @GetMapping("/administrador")
    public List<TemaDto> getAllAdmin(){
        return temaService.getAll(false);
    }

    @DeleteMapping("/administrador")
    public void delete(@RequestBody String tema){
        temaService.delete(tema);
    }
}
