package com.uncledavecode.products_service.model.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Unwrapped;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductRequest {

    private String sku;

    @NotBlank(message = "El nombre del producto no puede estar vacío.")
    @NotEmpty(message = "Se debe ingresar el nombre")
    private String name;

    @NotBlank(message = "La descripción del producto no puede estar vacía.")
    @NotEmpty(message = "Se debe poner una descripcion")
    private String description;

    @NotNull(message = "El precio del producto debe ser un valor positivo.")
    @Positive(message = "El precio del producto debe ser mayor que cero.")
    private Double price;

    @NotNull(message = "El estado del producto no puede ser nulo.")
    private Boolean status;
}
