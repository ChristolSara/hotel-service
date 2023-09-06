package hotelservice.service;

import hotelservice.dto.RoomDTO;
import hotelservice.exceptions.DuplicateRoomException;
import hotelservice.exceptions.RoomNotFoundException;

import java.util.List;

public interface IRoomService {
    RoomDTO addRoom(RoomDTO room) throws DuplicateRoomException;
    RoomDTO updateRoom(RoomDTO room) throws RoomNotFoundException;
    List<RoomDTO> getAllRooms();
    void deleteRoomById(Long id) throws RoomNotFoundException;

    RoomDTO getRoomById(String roomNumber);
}
