package blimop.tech.challenge.service;

import blimop.tech.challenge.domain.Reservation;
import blimop.tech.challenge.domain.Room;

import java.time.LocalDate;
import java.util.Optional;

public class DatosBookingServiceTest {

    public final static LocalDate startDate = LocalDate.now();
    public final static LocalDate endDate = startDate.plusDays(1);
    public final static LocalDate yesterday = startDate.minusDays(1);
    public final static String roomId = "po9886ftcyfc5";
    public final static String guestId = "3u4h43bde3i";
    public final static String roomOcupadoId = "po3886ftcyfc5";

    public static Optional<Room> room() {
        Room room = new Room();
        room.setId(roomId);
        room.setType("Simple");
        return Optional.of(room);
    }

    public static Optional<Room> roomOcupado() {
        Room roomOcupado = new Room();
        roomOcupado.setId(roomId);
        roomOcupado.setType("Simple");
        return Optional.of(roomOcupado);
    }

    public static Reservation reserva() {
        Reservation reservation = new Reservation();
        //agregamos valores a varible reserva
        reservation.setGuestId(guestId);
        reservation.setRoomId(roomId);
        reservation.setStartDate(startDate);
        reservation.setEndDate(endDate);
        return reservation;
    }

    public static Optional<Reservation> reservation() {
        return Optional.of(reserva());
    }

    public static Optional<Reservation> reservationVencida() {
        //configura Mockito datos de una reserva vencida
        Reservation reservationVencida = new Reservation();
        reservationVencida.setId(2L);
        reservationVencida.setGuestId(roomId);
        reservationVencida.setRoomId(roomOcupadoId);
        reservationVencida.setStartDate(yesterday);
        reservationVencida.setEndDate(endDate);
        return Optional.of(reservationVencida);
    }
}
