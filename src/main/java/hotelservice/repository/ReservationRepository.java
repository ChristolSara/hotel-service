package hotelservice.repository;

import hotelservice.models.Client;
import hotelservice.models.Reservation;
import hotelservice.models.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation,Long> {
    @Query("SELECT r FROM Reservation r " +
            "WHERE r.room.id = :roomId " +
            "AND ((r.checkIN >= :checkInDate AND r.checkIN < :checkOutDate) " +
            "     OR (r.checkOUT > :checkInDate AND r.checkOUT <= :checkOutDate) " +
            "     OR (r.checkIN <= :checkInDate AND r.checkOUT >= :checkOutDate))")
    List<Reservation> findOverlappingReservations(@Param("roomId") Long roomId, @Param("checkInDate") LocalDate checkIN, @Param("checkOutDate") LocalDate checkOUT);

    List<Reservation> findByClient(Client client);

    List<Reservation> findByRoom(Room room);
}
