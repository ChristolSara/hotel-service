package hotelservice.service;

import hotelservice.dto.ClientDTO;
import hotelservice.dto.ReservationDTO;
import hotelservice.dto.RoomDTO;
import hotelservice.exceptions.RoomNotAvaibleException;
import hotelservice.exceptions.RoomNotFoundException;
import hotelservice.mappers.ClientMapper;
import hotelservice.mappers.ReservationMapper;
import hotelservice.mappers.RoomMapper;
import hotelservice.models.Client;
import hotelservice.models.Reservation;
import hotelservice.models.Room;
import hotelservice.repository.ClientRepository;
import hotelservice.repository.ReservationRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class ReservationServiceImpl implements IReservationService{
    private ReservationMapper reservationMapper;
    private ReservationRepository reservationRepository;
    private ClientMapper clientMapper;
    private ClientRepository clientRepository;
    private IRoomService roomService;
    private RoomMapper roomMapper;
    @Override
    public boolean isRoomAvaible(RoomDTO roomDTO, LocalDate checkINDate, LocalDate checkOUTDate) {
        log.info("checking reservation");

        Room room = roomMapper.fromRoomDTO(roomDTO);

        if(room==null){
            return false;
        }
        List<Reservation> overlappingReservation = reservationRepository.findOverlappingReservation(room.getId(),checkINDate,checkOUTDate);
        List<ReservationDTO> overlappingReservationsDTO= overlappingReservation.stream().map(reservationMapper::fromReservation).toList();

        return overlappingReservationsDTO.isEmpty();
    }

    @Override
    public ReservationDTO makeResevation(ClientDTO clientDTO, RoomDTO roomDTO, LocalDate checkin, LocalDate checkout) throws RoomNotFoundException, RoomNotAvaibleException {
        RoomDTO foundRoom = roomService.getRoomById(roomDTO.getRoomNumber());

        if(foundRoom == null){
            throw  new RoomNotFoundException("Room with number "+foundRoom.getRoomNumber()+ " is not found" );
        }

        //check if room is available this day's
        boolean isRoomAvailable = isRoomAvaible(foundRoom,checkin,checkout);

        if(! isRoomAvailable){
            throw new RoomNotAvaibleException("room with number " + foundRoom.getRoomNumber() + "is not available for the specified dates. ");
        }

        if(checkin.isAfter(checkout)){
            throw new IllegalArgumentException("Check_in date must be before the check_out date.");
        }

        //convertr clientDTO to client

        Client clientEntity = clientMapper.fromClientDTO(clientDTO);

        //create a new reservationDTO object
        ReservationDTO reservationDTO = new ReservationDTO();
        reservationDTO.setClientDTO(clientDTO);
        reservationDTO.setRoomDTO(foundRoom);
        reservationDTO.setCheckIN(checkin);
        reservationDTO.setCheckOUT(checkout);

        //convert reservationDTO to resrvation

        Reservation reservationEntity = reservationMapper.fromReservationDTO(reservationDTO);
        //save reservation in data
        Reservation savedReservation = reservationRepository.save(reservationEntity);
            return reservationMapper.fromReservation(savedReservation);

    }

    @Override
    public void cancelReservation(Long reservationId) {

    }

    @Override
    public List<ReservationDTO> getresarvationByClient(ClientDTO clientDTO) {
        return null;
    }

    @Override
    public List<ReservationDTO> getReservationByRoom(RoomDTO roomDTO) {
        return null;
    }
}
