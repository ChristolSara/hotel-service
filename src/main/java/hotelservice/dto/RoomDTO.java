package hotelservice.dto;

import hotelservice.enums.RoomType;
import lombok.Data;

@Data
public class RoomDTO {

    private Long id;
    private String roomNumber;
    private RoomType roomType;
}
