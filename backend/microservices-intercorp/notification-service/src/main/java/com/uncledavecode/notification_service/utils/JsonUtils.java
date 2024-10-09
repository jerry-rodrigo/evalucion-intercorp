package com.uncledavecode.notification_service.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Clase de utilidad para la serialización y deserialización de objetos JSON
 * utilizando la biblioteca Jackson.
 * Proporciona métodos para convertir objetos a su representación JSON
 * y para crear objetos a partir de cadenas JSON.
 */
public class JsonUtils {

    private static final Logger log = LoggerFactory.getLogger(JsonUtils.class);
    private static final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Deserializa una cadena JSON en un objeto del tipo especificado.
     *
     * @param json     La cadena JSON que se desea deserializar.
     * @param valueType La clase del tipo de objeto que se desea obtener.
     * @param <T>     El tipo del objeto resultante.
     * @return Un objeto del tipo especificado que representa los datos en la cadena JSON.
     * @throws RuntimeException Si ocurre un error durante la deserialización.
     */
    public static <T> T fromJson(String json, Class<T> valueType) {
        try {
            return objectMapper.readValue(json, valueType);
        } catch (Exception e) {
            log.error("Error deserializing JSON: {}", json, e);
            throw new RuntimeException("Error deserializing JSON", e);
        }
    }

    /**
     * Serializa un objeto en una cadena JSON.
     *
     * @param value El objeto que se desea serializar.
     * @return Una cadena JSON que representa el objeto.
     * @throws RuntimeException Si ocurre un error durante la serialización.
     */
    public static String toJson(Object value) {
        try {
            return objectMapper.writeValueAsString(value);
        } catch (Exception e) {
            log.error("Error serializing object to JSON: {}", value, e);
            throw new RuntimeException("Error serializing object to JSON", e);
        }
    }
}
