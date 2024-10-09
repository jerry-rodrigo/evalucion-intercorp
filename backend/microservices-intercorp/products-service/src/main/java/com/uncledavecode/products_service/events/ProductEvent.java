package com.uncledavecode.products_service.events;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductEvent {
    private String id;
    private String sku;
    private String name;
    private String description;
    private Double price;
    private Boolean status;
}
