package hotelservice.web;

import hotelservice.models.Resarvation;
import hotelservice.repository.ResarvationRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class ResarvationController {
    private ResarvationRepository resarvationRepository;




    @GetMapping("/resarvations")
    public List<Resarvation> resarvationlist(){
        return resarvationRepository.findAll();
    }
}
