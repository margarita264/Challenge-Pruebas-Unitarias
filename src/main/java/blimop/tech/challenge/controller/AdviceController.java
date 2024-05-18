package blimop.tech.challenge.controller;

import blimop.tech.challenge.errores.RequestException;
import blimop.tech.challenge.service.response.ErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author jzapana
 * @RestControllerAdvice Tiene como responsabilidad capturar cualquier exception del tipo Runtime
 * o las que excepciones personalizadas
 */
@RestControllerAdvice
public class AdviceController {

    /**
     * Se indica que capture las excepciones del tipo RuntimeException
     * Va a devolver el código P-500 y el mensaje recibido por la exception
     *
     * @param ex exception recibida
     * @return
     */
    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<ErrorDTO> runtimeExceptionHandler(RuntimeException ex) {
        ErrorDTO error = ErrorDTO.builder().code("P-500").message(ex.getMessage()).build();

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    /**
     * Se indica que capture las excepciones del tipo RequestException
     * Va a devolver el código y el mensaje recibidos desde la RequestException
     *
     * @param ex exception recibida
     * @return
     */
    @ExceptionHandler(value = RequestException.class)
    public ResponseEntity<ErrorDTO> requestExceptionHandler(RequestException ex) {
        ErrorDTO error = ErrorDTO.builder().code(ex.getCode()).message(ex.getMessage()).build();

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
