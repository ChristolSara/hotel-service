package hotelservice.repository;

import hotelservice.models.Resarvation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResarvationRepository extends JpaRepository<Resarvation ,Long> {
}
