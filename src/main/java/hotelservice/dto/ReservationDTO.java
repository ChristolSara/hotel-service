package hotelservice.dto;

import hotelservice.enums.ReservationStatus;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ReservationDTO {

    private Long id;
    private LocalDate checkIN;
    private LocalDate checkOUT;
    private ReservationStatus status;
    private ClientDTO clientDTO;
    private RoomDTO roomDTO;


}
