package hotelservice.service;

import hotelservice.dto.ClientDTO;
import hotelservice.dto.ReservationDTO;
import hotelservice.dto.RoomDTO;
import hotelservice.exceptions.RoomNotAvaibleException;
import hotelservice.exceptions.RoomNotFoundException;

import java.time.LocalDate;
import java.util.List;

public interface IReservationService {


    boolean isRoomAvaible(RoomDTO roomDTO, LocalDate checkINDate, LocalDate checkOUTDate);
    ReservationDTO makeResevation(ClientDTO clientDTO,RoomDTO roomDTO,LocalDate CheckINDate ,LocalDate CheckOUTDate) throws RoomNotFoundException, RoomNotAvaibleException;
    void cancelReservation(Long reservationId);

    List<ReservationDTO> getresarvationByClient(ClientDTO clientDTO);
    List<ReservationDTO> getReservationByRoom(RoomDTO roomDTO);


}
