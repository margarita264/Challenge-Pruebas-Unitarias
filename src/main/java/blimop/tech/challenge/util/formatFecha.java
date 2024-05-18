package blimop.tech.challenge.util;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class formatFecha {

    /**
     * Convierte una fecha String en un LocalDateTime
     * @param dateString fecha String
     * @return fecha formateada en LocalDateTime
     */
    public static LocalDateTime parseStringLocalDateTime(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.parse(dateString, formatter);
    }

    /**
     * Convierte una fecha String en un LocalDate
     * @param dateString fecha String
     * @return fecha formateada en LocalDate
     */
    public static LocalDate parseStringLocalDate(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(dateString, formatter);
    }
}
