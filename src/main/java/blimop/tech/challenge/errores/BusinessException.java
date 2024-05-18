package blimop.tech.challenge.errores;

import lombok.Data;
import org.springframework.http.HttpStatus;

/**
 * Exception personalizada que incluye el código de la exception a devolver y el HttpStatus
 * Esto permite que a donde se genere el problema se pueda especificar cuál será el código
 * Http a devolver
 * @jzapana
 */

@Data
public class BusinessException extends RuntimeException{
    private final String code;
    private final HttpStatus status;

    public BusinessException(String code, HttpStatus httpStatus, String message) {
        super(message);
        this.code = code;
        this.status = httpStatus;
    }
}
