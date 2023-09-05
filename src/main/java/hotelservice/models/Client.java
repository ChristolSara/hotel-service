package hotelservice.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Entity @AllArgsConstructor @NoArgsConstructor @Data
public class Client {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;

    private String email;
    private String password;

    @OneToMany(mappedBy = "client")
    private List<Reservation> resarvations;
}
