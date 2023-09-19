package hotelservice;

import hotelservice.dto.ClientDTO;
import hotelservice.exceptions.EmailAlreadyExistsException;
import hotelservice.models.Reservation;
import hotelservice.repository.ReservationRepository;
import hotelservice.service.IClientService;
import hotelservice.service.IReservationService;
import hotelservice.service.IRoomService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import static hotelservice.enums.ReservationStatus.*;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(IClientService clientService, IRoomService roomService, IReservationService reservationService) {
        return
                args -> {

            ClientDTO client1 = new ClientDTO();
            client1.setId(1L);
            client1.setFirtName("sara");
            client1.setLastName("christol");
            client1.setEmail("sara.christol@gmail.com");
            client1.setPassword("password1");
                 try {
                     clientService.registerClient(client1);
                 }catch(EmailAlreadyExistsException ex){
                     System.out.println("Error while registering clien 1: "+ex.getMessage());
                    }



                };
    }

}
