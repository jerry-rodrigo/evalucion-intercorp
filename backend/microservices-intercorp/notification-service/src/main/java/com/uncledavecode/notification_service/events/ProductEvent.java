package com.uncledavecode.notification_service.events;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Evento que representa un producto, utilizado para la notificación o transmisión de datos.
 * Esta clase se usa para comunicar la información del producto entre servicios a través de eventos.
 * <p>
 * Utiliza Lombok para generar automáticamente getters, setters, constructores, y el patrón builder.
 * </p>
 *
 * @see lombok.Data
 * @see lombok.Builder
 * @see lombok.NoArgsConstructor
 * @see lombok.AllArgsConstructor
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductEvent {

    /**
     * El identificador único del producto.
     */
    private String id;

    /**
     * El SKU (Stock Keeping Unit) del producto, utilizado para identificarlo en inventarios.
     */
    private String sku;

    /**
     * El nombre del producto.
     */
    private String name;

    /**
     * La descripción del producto.
     */
    private String description;

    /**
     * El precio del producto.
     */
    private Double price;

    /**
     * El estado del producto, indicando si está disponible (true) o no (false).
     */
    private Boolean status;
}
