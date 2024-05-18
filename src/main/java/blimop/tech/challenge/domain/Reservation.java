package blimop.tech.challenge.domain;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
@Data
@Entity
@Table(name = "reservations")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String guestId;
    private String roomId;
    private LocalDate startDate;
    private LocalDate endDate;

}