package synrgy.finalproject.skyexplorer.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import synrgy.finalproject.skyexplorer.model.entity.Schedule;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, UUID> {
    @Query("SELECT s FROM Schedule s " +
            "WHERE s.from.id = :fromId " +
            "AND s.to.id = :toId " +
            "AND CAST(s.timeDeparture AS date) >= :startDate " +
            "ORDER BY s.timeDeparture ASC")
    List<Schedule> findSchedules(
            @Param("fromId") UUID fromId,
            @Param("toId") UUID toId,
            @Param("startDate") LocalDateTime startDate,
            Pageable pageable);//    List<Schedule> findAllByFromAndToAndTimeDeparture(UUID from, UUID to, LocalDateTime timeDeparture);

}
