package hotelservice.service;

import hotelservice.dto.ClientDTO;
import hotelservice.dto.ReservationDTO;
import hotelservice.dto.RoomDTO;
import hotelservice.exceptions.*;
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
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public ReservationDTO makeResevation(ClientDTO clientDTO, RoomDTO room, LocalDate checkin, LocalDate checkout) throws RoomNotFoundException, RoomNotAvaibleException {
        RoomDTO foundRoom = roomService.getRoomByNumber(room.getRoomNumber());

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
    public void cancelReservation(Long reservationId) throws ResrvationNotFoundexception {
        log.info("canceling reservation");

        //check if the reservation exict in database
        if(! reservationRepository.existsById(reservationId)){
            throw  new ResrvationNotFoundexception("reservation with "+ reservationId +" not exist");
        }
        //found the reservation
        Optional<Reservation> optionalReservation=reservationRepository.findById(reservationId);
        if(optionalReservation.isEmpty()){
            throw  new ResrvationNotFoundexception("reservation with "+reservationId+"  not found");
        }
        Reservation reservation= optionalReservation.get();
        ReservationDTO reservationDTO=reservationMapper.fromReservation(reservation);
        reservationRepository.delete(reservation);


    }

    @Override
    public List<ReservationDTO> getresarvationByClient(ClientDTO clientDTO) throws ClientNotFoundException, ClientSaveException {

        Client client = clientMapper.fromClientDTO(clientDTO);
         Client existingClient = clientRepository.findByEmail(client.getEmail());

         if(existingClient != null){
             client = existingClient;
         }else{
             try {
                 client = clientRepository.save(client);
             }catch (DataAccessException ex){
                 throw new ClientSaveException("failed to save the client in the database .", ex);
             }
         }


         if(client == null){
             throw  new ClientNotFoundException("Client not found");
         }

         List<Reservation> reservations = reservationRepository.findByClient(client);
        return reservations.stream()
                .map(reservationMapper::fromReservation)
                .collect(Collectors.toList());
    }

    @Override
    public List<ReservationDTO> getReservationByRoom(RoomDTO roomDTO) {

        Room room= roomMapper.fromRoomDTO(roomDTO);
        log.info("room entity :" + room);

        List<Reservation> reservations = reservationRepository.findByRoom(room);

        return reservations.stream()
                .map(reservationMapper::fromReservation)
                .collect(Collectors.toList());
    }
}
