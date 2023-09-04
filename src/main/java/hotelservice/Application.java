package hotelservice;

import hotelservice.models.Resarvation;
import hotelservice.repository.ResarvationRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import static hotelservice.models.ReservationStatus.*;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(ResarvationRepository resarvationRepository) {
        return
                args -> {

                    resarvationRepository.save(new Resarvation(null, "res 1", 1200, CONFIRMED));
                    resarvationRepository.save(new Resarvation(null, "res 2", 1600, CREATED));
					resarvationRepository.save(new Resarvation(null,"res 3",4000,CANCELED));
                    resarvationRepository.save(new Resarvation(null, "res 4", 3500, CONFIRMED));


                };
    }

}
