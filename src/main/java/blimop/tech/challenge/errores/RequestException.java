package blimop.tech.challenge.errores;

import lombok.Data;

/**
 * Exception encargada de capturar las excepciones surgidas por una petición request
 */
@Data
public class RequestException extends RuntimeException{
    private final String code;
    public RequestException(String code, String message) {
        super(message);
        this.code = code;
    }
}
