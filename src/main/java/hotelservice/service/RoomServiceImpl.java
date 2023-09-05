package hotelservice.service;

import hotelservice.dto.RoomDTO;
import hotelservice.exceptions.DuplicateRoomException;
import hotelservice.exceptions.RoomNotFoundException;
import hotelservice.mappers.RoomMapper;
import hotelservice.models.Client;
import hotelservice.models.Room;
import hotelservice.repository.RoomRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class RoomServiceImpl implements IRoomService{
    private RoomMapper roomMapper;
    private RoomRepository roomRepository;
    @Override
    public RoomDTO addRoom(RoomDTO roomDTO) throws DuplicateRoomException {
        log.info("save new room");

        if(roomRepository.existsByRoomNumber(roomDTO.getRoomNumber())){
            throw new DuplicateRoomException("Room with number "+ roomDTO.getRoomNumber() +" already exists. ");
        }
        Room room = roomMapper.fromRoomDTO(roomDTO);
        Room savedRoom = roomRepository.save(room);
        return roomMapper.fromRoom(savedRoom);
    }

    @Override
    public RoomDTO updateRoom(RoomDTO roomDTO) throws RoomNotFoundException {
        log.info("update room");
        if(!roomRepository.existsById(roomDTO.getId())){
            throw new RoomNotFoundException("Room with id : "+ roomDTO.getId()+" not found ");
        }
        Room room = roomMapper.fromRoomDTO(roomDTO);
        Room savedRoom = roomRepository.save(room);
        return roomMapper.fromRoom(savedRoom);
    }

    @Override
    public List<RoomDTO> getAllRooms() {
       List<Room> allRooms= roomRepository.findAll();
       List<RoomDTO> roomDTOS = allRooms.stream()
               .map(roomMapper::fromRoom)
               .collect(Collectors.toList());

       return  roomDTOS;
    }

    @Override
    public void deleteRoomById(Long id)  throws RoomNotFoundException{
        if(!roomRepository.existsById(id)){
            throw new RoomNotFoundException("Room with id : "+ id + " not found");
        }
        Optional<Room> OptionalRoom = roomRepository.findById(id);
         if(OptionalRoom.isEmpty()){
             throw  new RoomNotFoundException("room with id "+ id+" not found");
         }

         Room room = OptionalRoom.get();
        RoomDTO roomDTO = roomMapper.fromRoom(room);
        roomRepository.delete(room);
    }
}
