package blimop.tech.challenge.service.response;

import lombok.Builder;
import lombok.Data;

/**
 * Objeto retornado por la ControllerAdvice
 */
@Data
@Builder
public class ErrorDTO {
    private String code;
    private String message;

}