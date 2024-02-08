package synrgy.finalproject.skyexplorer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import synrgy.finalproject.skyexplorer.model.entity.Schedule;
import synrgy.finalproject.skyexplorer.model.entity.ScheduleDetail;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface ScheduleDetailRepository extends JpaRepository<ScheduleDetail, UUID> {
    @Query("SELECT s FROM Schedule s " +
            "JOIN ScheduleDetail sd ON s.id = sd.schedule.id " +
            "WHERE sd.cabinClass.id = :cabinClassId " +
            "AND sd.ticketType.id = :ticketTypeId " +
            "AND CAST(s.timeDeparture AS date) = :date " +
            "AND s.from.id = :fromAirportId " +
            "AND s.to.id = :toAirportId " +
            "ORDER BY s.timeDeparture ASC")
    List<Schedule> findSchedulesByCabinClassAndTicketTypeAndDate(
            @Param("cabinClassId") UUID cabinClassId,
            @Param("ticketTypeId") UUID ticketTypeId,
            @Param("date") LocalDate date,
            @Param("fromAirportId") UUID fromAirportId,
            @Param("toAirportId") UUID toAirportId);

    @Query("SELECT s FROM Schedule s " +
            "JOIN ScheduleDetail sd ON s.id = sd.schedule.id " +
            "WHERE sd.cabinClass.id = :cabinClassId " +
            "AND sd.ticketType.id = :ticketTypeId " +
            "AND CAST(s.timeDeparture AS date) = :date " +
            "AND s.from.id = :fromAirportId " +
            "AND s.to.id = :toAirportId " +
            "ORDER BY s.timeDeparture DESC")
    List<Schedule> findSchedulesByCabinClassAndTicketTypeAndDateLast(
            @Param("cabinClassId") UUID cabinClassId,
            @Param("ticketTypeId") UUID ticketTypeId,
            @Param("date") LocalDate date,
            @Param("fromAirportId") UUID fromAirportId,
            @Param("toAirportId") UUID toAirportId);

    @Query("SELECT s FROM Schedule s " +
            "JOIN ScheduleDetail sd ON s.id = sd.schedule.id " +
            "WHERE sd.cabinClass.id = :cabinClassId " +
            "AND sd.ticketType.id = :ticketTypeId " +
            "AND CAST(s.timeDeparture AS date) = :date " +
            "AND s.from.id = :fromAirportId " +
            "AND s.to.id = :toAirportId " +
            "ORDER BY s.timeArrive ASC")
    List<Schedule> findSchedulesByCabinClassAndTicketTypeAndDateEarliestArrival(
            @Param("cabinClassId") UUID cabinClassId,
            @Param("ticketTypeId") UUID ticketTypeId,
            @Param("date") LocalDate date,
            @Param("fromAirportId") UUID fromAirportId,
            @Param("toAirportId") UUID toAirportId);

    @Query("SELECT s FROM Schedule s " +
            "JOIN ScheduleDetail sd ON s.id = sd.schedule.id " +
            "WHERE sd.cabinClass.id = :cabinClassId " +
            "AND sd.ticketType.id = :ticketTypeId " +
            "AND CAST(s.timeDeparture AS date) = :date " +
            "AND s.from.id = :fromAirportId " +
            "AND s.to.id = :toAirportId " +
            "ORDER BY s.timeArrive DESC")
    List<Schedule> findSchedulesByCabinClassAndTicketTypeAndDateLastArrival(
            @Param("cabinClassId") UUID cabinClassId,
            @Param("ticketTypeId") UUID ticketTypeId,
            @Param("date") LocalDate date,
            @Param("fromAirportId") UUID fromAirportId,
            @Param("toAirportId") UUID toAirportId);
}
