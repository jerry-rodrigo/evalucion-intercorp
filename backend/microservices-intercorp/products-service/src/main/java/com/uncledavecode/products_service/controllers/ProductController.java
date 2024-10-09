package com.uncledavecode.products_service.controllers;

import com.uncledavecode.products_service.model.dtos.ProductRequest;
import com.uncledavecode.products_service.model.dtos.ProductResponse;
import com.uncledavecode.products_service.services.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * ProductController es el controlador REST que maneja las solicitudes relacionadas con los productos.
 * Ofrece operaciones CRUD para crear, obtener y actualizar productos utilizando programación reactiva.
 */
@RestController
@RequestMapping("/api/product")
//@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    /**
     * Endpoint para agregar un nuevo producto.
     *
     * @param productRequest el cuerpo de la solicitud con los detalles del producto a agregar.
     * @return un Mono<Void> que indica que la operación fue completada.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Void> addProduct(@Valid @RequestBody ProductRequest productRequest) {
        return this.productService.addProduct(productRequest);
    }

    /**
     * Endpoint para obtener todos los productos.
     *
     * @return un Flux<ProductResponse> que contiene todos los productos almacenados.
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Flux<ProductResponse> getAllProducts() {
        return this.productService.getAllProducts();
    }

    /**
     * Endpoint para actualizar un producto existente por ID.
     * Se pueden modificar todos los campos del producto excepto su ID.
     *
     * @param id el identificador del producto a actualizar.
     * @param productRequest el cuerpo de la solicitud con los nuevos detalles del producto.
     * @return un Mono<ProductResponse> que contiene los detalles del producto actualizado.
     */
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<ProductResponse> updateProductById(@PathVariable String id, @Valid @RequestBody ProductRequest productRequest) {
        return this.productService.updateProductById(id, productRequest);
    }
}