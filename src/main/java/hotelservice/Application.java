package hotelservice;

import hotelservice.models.Reservation;
import hotelservice.repository.ReservationRepository;
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

//    @Bean
//    CommandLineRunner commandLineRunner(ReservationRepository reservationRepository) {
//        return
//                args -> {
//
////                    reservationRepository.save(new Reservation(null, "res 1", 1200, CONFIRMED));
////                    reservationRepository.save(new Reservation(null, "res 2", 1600, CREATED));
////					reservationRepository.save(new Reservation(null,"res 3",4000,CANCELED));
////                    reservationRepository.save(new Reservation(null, "res 4", 3500, CONFIRMED));
//
//
//                };
//    }

}
