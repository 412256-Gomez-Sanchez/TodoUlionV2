package tht.adaptive.ApiUlion.controllers;

import org.springframework.web.bind.annotation.*;
import tht.adaptive.ApiUlion.DTOs.TransaccionDto;
import tht.adaptive.ApiUlion.services.TransaccionService;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("api/v2/transacciones")
public class TransaccionController {

    private final TransaccionService transaccionService;

    public TransaccionController(TransaccionService transaccionService) {
        this.transaccionService = transaccionService;
    }

    @PostMapping("/administrador")
    public void saveProducto(@RequestBody TransaccionDto transaccionDto){
        transaccionService.saveProducto(transaccionDto);
    }

    @GetMapping
    public List<TransaccionDto> getAll(){
        return transaccionService.getAll();
    }

    @GetMapping("/{id}")
    public TransaccionDto getById(@PathVariable String id){
        return transaccionService.getById(id);
    }
}
