package com.uncledavecode.products_service.repositories;

import com.uncledavecode.products_service.model.entities.Product;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

/**
 * Repositorio de productos que extiende de {@link ReactiveMongoRepository}
 * para interactuar con la base de datos MongoDB de manera reactiva.
 * <p>
 * Esta interfaz proporciona métodos CRUD estándar y puede ser extendida
 * para agregar métodos personalizados de consulta si es necesario.
 * </p>
 */
public interface ProductRepository extends ReactiveMongoRepository<Product, String> {

}
