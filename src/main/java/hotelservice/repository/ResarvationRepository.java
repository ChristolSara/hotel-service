package hotelservice.repository;

import hotelservice.models.Resarvation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResarvationRepository extends JpaRepository<Resarvation ,Long> {
}
