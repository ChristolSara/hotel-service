package hotelservice.service;

import hotelservice.dto.ClientDTO;
import hotelservice.dto.ReservationDTO;
import hotelservice.dto.RoomDTO;
import hotelservice.exceptions.*;

import java.time.LocalDate;
import java.util.List;

public interface IReservationService {


    boolean isRoomAvaible(RoomDTO roomDTO, LocalDate checkINDate, LocalDate checkOUTDate);
    ReservationDTO makeResevation(ClientDTO clientDTO,RoomDTO roomDTO,LocalDate CheckINDate ,LocalDate CheckOUTDate) throws RoomNotFoundException, RoomNotAvaibleException;
    void cancelReservation(Long reservationId) throws ResrvationNotFoundexception;

    List<ReservationDTO> getresarvationByClient(ClientDTO clientDTO) throws ClientNotFoundException, ClientSaveException;
    List<ReservationDTO> getReservationByRoom(RoomDTO roomDTO);


}
