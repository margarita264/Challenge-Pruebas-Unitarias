package blimop.tech.challenge.repository;

import blimop.tech.challenge.domain.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation>
    findByRoomIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(String roomId, LocalDate
            startDate, LocalDate endDate);
}
