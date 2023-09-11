package hotelservice.service;

import hotelservice.dto.RoomDTO;
import hotelservice.exceptions.DuplicateRoomException;
import hotelservice.exceptions.RoomNotFoundException;
import hotelservice.mappers.RoomMapper;
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
    public RoomDTO getRoomByNumber(String roomNumber) throws RoomNotFoundException {
        // Retrieve the Room entity from the database by room number
        Room room = roomRepository.findByRoomNumber(roomNumber);
        if (room == null) {
            throw new RoomNotFoundException("Room with number " + roomNumber + " not found.");
        }

        // Now, we need to check if the retrieved room actually has the same room number as requested
        if (!room.getRoomNumber().equals(roomNumber)) {
            throw new RoomNotFoundException("Room with number " + roomNumber + " not found.");
        }

        // Convert Room entity to RoomDTO using the RoomMapper
        return roomMapper.fromRoom(room);
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



    @Override
    public RoomDTO findById(Long id) throws RoomNotFoundException {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new RoomNotFoundException("Room with ID: "+id+" Not Found!"));
        return roomMapper.fromRoom(room);
    }

}
