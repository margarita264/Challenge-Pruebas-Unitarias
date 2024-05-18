package blimop.tech.challenge.controller;

import blimop.tech.challenge.domain.Reservation;
import blimop.tech.challenge.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/booking")
public class BookingController {
    @Autowired
    private BookingService bookingService;

    @PostMapping("/reserve")
    public Reservation makeReservation(@RequestParam String guestId, @RequestParam
    String roomId,
                                       @RequestParam String startDate, @RequestParam
                                       String endDate) {
        return bookingService.makeReservation(guestId, roomId,
                LocalDate.parse(startDate), LocalDate.parse(endDate));
    }

    @DeleteMapping("/cancel/{id}")
    public boolean cancelReservation(@PathVariable Long id) {
        return bookingService.cancelReservation(id);
    }

    @GetMapping("/availability")
    public boolean checkAvailability(@RequestParam String roomId, @RequestParam String
            startDate,
                                     @RequestParam String endDate) {
        return bookingService.checkAvailability(roomId, LocalDate.parse(startDate),
                LocalDate.parse(endDate));
    }
}
