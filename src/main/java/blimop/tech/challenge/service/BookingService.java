package blimop.tech.challenge.service;

import blimop.tech.challenge.domain.Reservation;
import blimop.tech.challenge.domain.Room;
import blimop.tech.challenge.repository.ReservationRepository;
import blimop.tech.challenge.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;
@Service
public class BookingService {
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private RoomRepository roomRepository;

    /**
     * permite guardar una reserva
     *
     * @param guestId   ID del huésped.
     * @param roomId    ID de la habitación.
     * @param startDate Fecha de inicio de la reserva.
     * @param endDate   Fecha de fin de la reserva.
     * @return Reservation (objeto): Detalles de la reserva creada.
     */
    public Reservation makeReservation(String guestId, String roomId, LocalDate
            startDate, LocalDate endDate) {
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("Start date must be before end date");
        }
        Optional<Room> room = roomRepository.findById(roomId);
        if (!room.isPresent()) {
            throw new IllegalArgumentException("Room does not exist");
        }
        boolean isAvailable =
                reservationRepository.findByRoomIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
                        roomId, endDate, startDate).isEmpty();
        if (!isAvailable) {
            throw new IllegalStateException("Room is not available for the given dates");
        }
        Reservation reservation = new Reservation();
        reservation.setGuestId(guestId);
        reservation.setRoomId(roomId);
        reservation.setStartDate(startDate);
        reservation.setEndDate(endDate);
        return reservationRepository.save(reservation);
    }

    /**
     * Permite cancelar una reserva
     *
     * @param reservationId ID de la reserva a cancelar.
     * @return boolean: Indicador de éxito o fracaso de la operación.
     */
    public boolean cancelReservation(Long reservationId) {
        Optional<Reservation> reservation =
                reservationRepository.findById(reservationId);
        if (!reservation.isPresent() ||
                reservation.get().getStartDate().isBefore(LocalDate.now())) {
            return false;
        }
        reservationRepository.deleteById(reservationId);
        return true;
    }

    /**
     * Permite consultar disponibilidad
     *
     * @param roomId    ID de la habitación.
     * @param startDate Fecha de inicio del rango.
     * @param endDate   Fecha de fin del rango.
     * @return boolean: Indicador de disponibilidad de la habitación.
     */
    public boolean checkAvailability(String roomId, LocalDate startDate, LocalDate
            endDate) {
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("Start date must be before end date");
        }

        Optional<Room> room = roomRepository.findById(roomId);
        if (!room.isPresent()) {
            throw new IllegalArgumentException("Room does not exist");
        }
        return reservationRepository.findByRoomIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
                        roomId, endDate, startDate).isEmpty();
    }
}
