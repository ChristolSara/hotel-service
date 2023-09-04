package hotelservice.models;

import hotelservice.enums.RoomType;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class Room {


    private Long id;
    private String roomNumer;
    private RoomType roomType;
    private List<Reservation> resarvations;
}
