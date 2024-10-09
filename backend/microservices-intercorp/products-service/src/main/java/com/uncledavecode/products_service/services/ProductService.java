package com.uncledavecode.products_service.services;

import com.uncledavecode.products_service.events.ProductEvent;
import com.uncledavecode.products_service.model.dtos.ProductRequest;
import com.uncledavecode.products_service.model.dtos.ProductResponse;
import com.uncledavecode.products_service.model.entities.Product;
import com.uncledavecode.products_service.repositories.ProductRepository;
import com.uncledavecode.products_service.utils.JsonUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Random;
import java.util.UUID;

/**
 * Servicio que gestiona la lógica de negocio para las operaciones CRUD relacionadas con los productos.
 * También maneja la publicación de eventos a Kafka cuando un producto es creado o actualizado.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;

    /**
     * Agrega un nuevo producto a la base de datos y publica un evento en Kafka.
     *
     * @param productRequest la solicitud de creación de producto, que contiene el nombre, descripción, precio y estado.
     * @return un Mono<Void> que indica que la operación fue completada.
     */
    public Mono<Void> addProduct(@Valid ProductRequest productRequest) {
        var product = Product.builder()
                .sku(generateSku())
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .status(productRequest.getStatus())
                .build();

        return productRepository.save(product)
                .doOnSuccess(savedProduct -> {
                    log.info("Product added: {}", savedProduct);

                    ProductEvent productEvent = new ProductEvent(
                            savedProduct.getId(),
                            savedProduct.getSku(),
                            savedProduct.getName(),
                            savedProduct.getDescription(),
                            savedProduct.getPrice(),
                            savedProduct.getStatus()
                    );
                    kafkaTemplate.send("product-topic", JsonUtils.toJson(productEvent));
                })
                .then();
    }

    /**
     * Obtiene todos los productos almacenados en la base de datos.
     *
     * @return un Flux<ProductResponse> que contiene los productos almacenados.
     */
    public Flux<ProductResponse> getAllProducts() {
        return productRepository.findAll()
                .map(this::mapToProductResponse);
    }

    /**
     * Actualiza un producto existente por su ID y publica un evento en Kafka.
     * Se generan nuevos valores para el SKU, nombre, descripción, precio y estado.
     *
     * @param id el identificador del producto que se va a actualizar.
     * @param productRequest la solicitud que contiene los nuevos detalles del producto.
     * @return un Mono<ProductResponse> que contiene el producto actualizado.
     * @throws RuntimeException si no se encuentra el producto con el ID proporcionado.
     */
    public Mono<ProductResponse> updateProductById(String id, @Valid ProductRequest productRequest) {
        return productRepository.findById(id)
                .flatMap(existingProduct -> {
                    existingProduct.setSku(generateSku());
                    existingProduct.setName(productRequest.getName());
                    existingProduct.setDescription(productRequest.getDescription());
                    existingProduct.setPrice(productRequest.getPrice());
                    existingProduct.setStatus(productRequest.getStatus());

                    return productRepository.save(existingProduct)
                            .doOnSuccess(updatedProduct -> {
                                log.info("Product updated: {}", updatedProduct);

                                ProductEvent productEvent = new ProductEvent(
                                        updatedProduct.getId(),
                                        updatedProduct.getSku(),
                                        updatedProduct.getName(),
                                        updatedProduct.getDescription(),
                                        updatedProduct.getPrice(),
                                        updatedProduct.getStatus()
                                );
                                kafkaTemplate.send("product-topic", JsonUtils.toJson(productEvent));
                            });
                })
                .map(this::mapToProductResponse)
                .switchIfEmpty(Mono.error(new RuntimeException("Product not found with id: " + id)));
    }

    /**
     * Mapea una entidad Product a un DTO ProductResponse.
     *
     * @param product el producto a mapear.
     * @return un ProductResponse con los detalles del producto.
     */
    private ProductResponse mapToProductResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .sku(product.getSku())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .status(product.getStatus())
                .build();
    }

    /**
     * Genera un SKU aleatorio de 6 caracteres alfanuméricos.
     *
     * @return un String que representa el SKU generado.
     */
    private String generateSku() {
        int length = 6;
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuilder sku = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            sku.append(characters.charAt(random.nextInt(characters.length())));
        }

        return sku.toString();
    }
}
