package com.uncledavecode.notification_service.listeners;

import com.uncledavecode.notification_service.events.ProductEvent;
import com.uncledavecode.notification_service.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * Escucha eventos de productos desde el tema de Kafka 'product-topic'.
 * Procesa los mensajes recibidos y registra la información del evento en el log.
 */
@Component
@Slf4j
public class ProductEventListener {

    /**
     * Maneja las notificaciones de eventos de productos.
     *
     * Este método se invoca automáticamente cuando se recibe un mensaje en el
     * tema 'product-topic'. Convierte el mensaje JSON en un objeto {@link ProductEvent}
     * y registra su información.
     *
     * @param message El mensaje recibido desde Kafka, en formato JSON.
     */
    @KafkaListener(topics = "product-topic")
    public void handleOrdersNotifications(String message) {
        var productEvent = JsonUtils.fromJson(message, ProductEvent.class);

        log.info("Product event received: ID = {}, Name = {}, Status = {}",
                productEvent.getId(),
                productEvent.getName(),
                productEvent.getStatus());
    }
}
