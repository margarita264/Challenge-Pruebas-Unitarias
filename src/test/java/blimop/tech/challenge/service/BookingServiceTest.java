package blimop.tech.challenge.service;

import blimop.tech.challenge.domain.Reservation;
import blimop.tech.challenge.repository.ReservationRepository;
import blimop.tech.challenge.repository.RoomRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.Optional;
import java.util.logging.Logger;

import static blimop.tech.challenge.service.DatosBookingServiceTest.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class BookingServiceTest {

    private static final Logger logger = Logger.getLogger(BookingServiceTest.class.getName());

    @InjectMocks
    private BookingService bookingService;
    @Mock
    private ReservationRepository reservationRepository;
    @Mock
    private RoomRepository roomRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        //configura Mockito para obtener datos de un room
        when(roomRepository.findById(roomId)).thenReturn(room());

        //configura Mockito para obtener datos de un roomOcupado
        when(roomRepository.findById(roomOcupadoId)).thenReturn(roomOcupado());

        //configura Mockito para obtener una lista de reservas vacía
        when(reservationRepository.findByRoomIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
                roomId, endDate, startDate)).thenReturn(Collections.emptyList());

        //configura Mockito para que devuelva una lista que contiene una reserva.
        when(reservationRepository.findByRoomIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
                roomOcupadoId, endDate, startDate)).thenReturn(Collections.singletonList(new Reservation()));

        //configura Mockito datos de una reserva no vencida
        when(reservationRepository.findById(1L)).thenReturn(reservation());

        //configura Mockito datos de una reserva vencida
        when(reservationRepository.findById(2L)).thenReturn(reservationVencida());

    }

    /**
     * Valida que se pueda realizar una reserva cuando la habitación está disponible
     * y las fechas son válidas.
     *
     * @autor Zerpa Margarita
     * @since 17/05/2024
     */
    @Test
    void makeReservationExitosa() {
        logger.info("Inciando test makeReservationExitosa()");

        Reservation savedReservation = reserva();
        when(reservationRepository.save(reserva())).thenReturn(savedReservation);

        Reservation newReserva = bookingService.makeReservation(guestId, roomId, startDate, endDate);

        assertEquals(savedReservation.getId(), newReserva.getId());
        assertEquals(savedReservation.getGuestId(), newReserva.getGuestId());
        assertEquals(savedReservation.getRoomId(), newReserva.getRoomId());
        assertEquals(savedReservation.getStartDate(), newReserva.getStartDate());
        assertEquals(savedReservation.getEndDate(), newReserva.getEndDate());
        //verificamos que solo se haya instanciado una vez el método save
        verify(reservationRepository, times(1)).save(any(Reservation.class));
        logger.info("Finalizando test makeReservationExitosa()");
    }

    /**
     * Validar Intento de realizar una reserva donde la fecha de inicio es posterior a la fecha de fin.
     *
     * @autor Zerpa Margarita
     * @since 17/05/2024
     */
    @Test
    void makeReservationFechaInvalida() {
        logger.info("Inciando test makeReservationFechaInvalida()");
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            bookingService.makeReservation(guestId, roomId, startDate, yesterday);
        });

        assertEquals("Start date must be before end date", exception.getMessage());
        logger.info("Finalizando test makeReservationFechaInvalida()");
    }

    /**
     * Validar Intento de una reserva para una habitación que ya está reservada
     * en el rango de fechas solicitado.
     *
     * @autor Zerpa Margarita
     * @since 17/05/2024
     */
    @Test
    void makeReservationHabitacionOcupada() {
        logger.info("Inciando test makeReservationHabitacionOcupada()");
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            bookingService.makeReservation(guestId, roomOcupadoId, startDate, endDate);
        });
        assertEquals("Room is not available for the given dates", exception.getMessage());
        logger.info("Finalizando test makeReservationHabitacionOcupada()");
    }

    /**
     * Validar que se pueda cancelar una reserva existente cuya fecha de inicio no ha pasado.
     *
     * @autor Zerpa Margarita
     * @since 17/05/2024
     */
    @Test
    void cancelReservationExitosa() {
        logger.info("Inciando test cancelReservationExitosa()");
        assertTrue(bookingService.cancelReservation(1L));
        //verificamos que solo se haya instanciado una vez el método delete
        verify(reservationRepository, times(1)).deleteById(1L);
        logger.info("Finalizando test cancelReservationExitosa()");
    }

    /**
     * Validar Intento de cancelar una reserva cuya fecha de inicio ya ha pasado
     *
     * @autor Zerpa Margarita
     * @since 17/05/2024
     */
    @Test
    void cancelReservationFechaPasada() {
        logger.info("Inciando test cancelReservationFechaPasada()");
        assertFalse(bookingService.cancelReservation(2L));
        logger.info("Finalizando test cancelReservationFechaPasada()");
    }

    /**
     * Validar la disponibilidad de una habitación en un rango de fechas en la que no hay reservas.
     *
     * @autor Zerpa Margarita
     * @since 17/05/2024
     */
    @Test
    void checkAvailabilityExitosa() {
        logger.info("Inicindo test cancelReservationFechaPasada()");
        assertTrue(bookingService.checkAvailability(roomId, startDate, endDate));
        logger.info("Finalizando test cancelReservationFechaPasada()");
    }

    /**
     * Validar que una habitación no esté disponible en un rango de fechas en el que ya está reservada.
     *
     * @autor Zerpa Margarita
     * @since 17/05/2024
     */
    @Test
    void checkAvailabilityReservado() {
        logger.info("Inicindo test checkAvailabilityReservado()");
        assertFalse(bookingService.checkAvailability(roomOcupadoId, startDate, endDate));
        logger.info("Finalizando test checkAvailabilityReservado()");
    }

    /**
     * Validar rango de fechas incorrectas al verificar la disponibilidad.
     *
     * @autor Zerpa Margarita
     * @since 17/05/2024
     */
    @Test
    void checkAvailabilityFechaIncorrecta() {
        logger.info("Inicindo test checkAvailabilityFechaIncorrecta()");
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            assertFalse(bookingService.checkAvailability(roomId, startDate, yesterday));
        });

        assertEquals("Start date must be before end date", exception.getMessage());
        logger.info("Finalizando test checkAvailabilityFechaIncorrecta()");
    }

    /**
     * Validar room inexistente al verificar la disponibilidad.
     *
     * @autor Zerpa Margarita
     * @since 17/05/2024
     */
    @Test
    void checkAvailabilityRoomInexistente() {
        logger.info("Inicindo test checkAvailabilityRoomInexistente()");
        when(roomRepository.findById("p33886f6cyfc5")).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            assertFalse(bookingService.checkAvailability("p33886f6cyfc5", startDate, endDate));
        });

        assertEquals("Room does not exist", exception.getMessage());
        logger.info("Finalizando test checkAvailabilityRoomInexistente()");

    }
}