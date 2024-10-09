package com.uncledavecode.products_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * Clase global de manejo de excepciones para capturar y procesar las excepciones
 * lanzadas en toda la aplicación.
 *
 * Utiliza {@link ControllerAdvice} para interceptar excepciones y aplicar
 * la lógica de manejo de errores de manera centralizada.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Maneja excepciones de tipo {@link MethodArgumentNotValidException} que son lanzadas
     * cuando la validación de los argumentos del método falla, como en el caso de los
     * parámetros de entrada de un controlador con anotaciones de validación.
     *
     * @param ex la excepción {@link MethodArgumentNotValidException} lanzada cuando la validación falla.
     * @return una respuesta {@link ResponseEntity} que contiene un mapa con los campos que fallaron la validación
     *         como clave y el mensaje de error correspondiente como valor. Se devuelve un estado HTTP 400 (Bad Request).
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}