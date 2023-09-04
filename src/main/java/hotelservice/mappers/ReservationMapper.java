package hotelservice.mappers;

import hotelservice.dto.ReservationDTO;
import hotelservice.models.Reservation;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service @AllArgsConstructor
public class ReservationMapper {

    private final ClientMapper clientMapper;
    private final RoomMapper roomMapper;

    public ReservationDTO fromReservation(Reservation reservation){
        ReservationDTO reservationDTO= new ReservationDTO();
        BeanUtils.copyProperties(reservation,reservationDTO);
        reservationDTO.setClientDTO(clientMapper.fromClient(reservation.getClient()));
        reservationDTO.setRoomDTO(roomMapper.fromRoom(reservation.getRoom()));

        return reservationDTO;
    }
    public Reservation fromReservationDTO(ReservationDTO reservationDTO){
        Reservation reservation= new Reservation();
        BeanUtils.copyProperties(reservationDTO,reservationDTO);
        reservation.setClient(clientMapper.fromClientDTO(reservationDTO.getClientDTO()));
        reservation.setRoom(roomMapper.fromRoomDTO(reservationDTO.getRoomDTO()));
        return  reservation;
    }
}
