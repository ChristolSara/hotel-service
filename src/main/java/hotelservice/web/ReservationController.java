package hotelservice.web;

import hotelservice.dto.ClientDTO;
import hotelservice.dto.ReservationDTO;
import hotelservice.dto.RoomDTO;
import hotelservice.exceptions.*;
import hotelservice.models.Reservation;
import hotelservice.repository.ReservationRepository;
import hotelservice.service.ReservationServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/reservations")
@CrossOrigin("*")
public class ReservationController {
    private final ReservationServiceImpl reservationService;

    @PostMapping("/check-availability")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public boolean   isRoomAvailable(@RequestBody RoomDTO  roomDTO,
                                     @RequestParam @DateTimeFormat(iso=DateTimeFormat.ISO.DATE) LocalDate checkINDate,
                                     @RequestParam @DateTimeFormat(iso=DateTimeFormat.ISO.DATE)  LocalDate checkOUTDate ){
        return reservationService.isRoomAvaible(roomDTO,checkINDate,checkOUTDate);
    }


    @PostMapping("/make-reservation")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ReservationDTO makeReservation(@RequestBody ReservationDTO reservationRequestDTO) throws RoomNotFoundException, RoomNotFoundException, RoomNotAvaibleException {
        return reservationService.makeResevation(
                reservationRequestDTO.getClientDTO(),
                reservationRequestDTO.getRoomDTO(),
                reservationRequestDTO.getCheckIN(),
                reservationRequestDTO.getCheckOUT()
        );
    }

    @DeleteMapping("/cancel-reservation/{reservationId}")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public void cancelReservation(@PathVariable Long reservationId) throws ResrvationNotFoundexception {
        reservationService.cancelReservation(reservationId);
    }

    @GetMapping("/client/{email}")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public List<ReservationDTO> getReservationByClient(@PathVariable String email) throws ClientNotFoundException, ClientSaveException{
        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setEmail(email);
        return  reservationService.getresarvationByClient(clientDTO);
    }


    @GetMapping("/room/{roomNumber}")
    @PreAuthorize("hasAuthority('SCOPE_USER")
    public List<ReservationDTO> getReservationByRoom(@PathVariable String roomNumber){
        RoomDTO roomDTO= new RoomDTO();
        roomDTO.setRoomNumber(roomNumber);
        return reservationService.getReservationByRoom(roomDTO);
    }



}
