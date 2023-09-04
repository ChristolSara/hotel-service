package hotelservice.dto;

import lombok.Data;

@Data
public class ClientDTO {
    private Long id;
    private String firtName;
    private String lastName;
    private String email;
    private String password;
}
