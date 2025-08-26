package tht.adaptive.ApiUlion.controllers;

import org.springframework.web.bind.annotation.*;
import tht.adaptive.ApiUlion.DTOs.ProductoDto;
import tht.adaptive.ApiUlion.services.ProductoService;

@CrossOrigin
@RestController
@RequestMapping("api/v2/productos")
public class ProductoController {

    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @PostMapping("/administrador")
    public void saveProducto(@RequestBody ProductoDto productoDto){
        productoService.saveProducto(productoDto);
    }
}
