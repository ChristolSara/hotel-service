package hotelservice.web;

import hotelservice.models.Reservation;
import hotelservice.repository.ReservationRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class ReservationController {
    private ReservationRepository resarvationRepository;




    @GetMapping("/resarvations")
    public List<Reservation> resarvationlist(){
        return resarvationRepository.findAll();
    }
}
