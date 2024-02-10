package synrgy.finalproject.skyexplorer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import synrgy.finalproject.skyexplorer.model.entity.TicketCabinDetail;

import java.util.List;
import java.util.UUID;

@Repository
public interface TicketCabinDetailRepository extends JpaRepository<TicketCabinDetail, UUID> {
    @Query(value = "SELECT fd.*, sch.price AS price " +
            "FROM (SELECT sch.id AS schedule_id, sch.time_departure AS departure_time, sch.time_arrive AS arrival_time, " +
            "ap1.name AS departure_airport, ap1.city AS departure_city, na1.name AS departure_country, " +
            "ap2.name AS arrival_airport, ap2.city AS arrival_city, na2.name AS arrival_country, " +
            "ai.code AS airplane_code, ai.name AS airplane_name, cc.name AS cabin_class, tt.name AS ticket_type, " +
            "tcd.cabinbaggage, tcd.checkedbaggage, tcd.seatselection, tcd.baggagedelayprotection, tcd.mealpack, " +
            "tcd.cancellation, tcd.flightchange, tcd.noshow " +
            "FROM schedule sch " +
            "JOIN airplane ai ON sch.airplane_id = ai.id " +
            "JOIN airport ap1 ON sch.from_id = ap1.id " +
            "JOIN airport ap2 ON sch.to_id = ap2.id " +
            "JOIN national na1 ON ap1.national_id = na1.id " +
            "JOIN national na2 ON ap2.national_id = na2.id " +
            "JOIN schedule_detail sd ON sch.id = sd.schedule_id " +
            "JOIN cabin_class cc ON sd.cabin_class_id = cc.id " +
            "JOIN ticket_type tt ON sd.ticket_type_id = tt.id " +
            "JOIN ticket_cabin_detail tcd ON cc.id = tcd.cabin_class_id AND tt.id = tcd.ticket_type_id " +
            "WHERE sch.id = :scheduleId " +
            "AND sch.to_id = :toId " +
            "AND sch.from_id = :fromId " +
            "AND sd.cabin_class_id = :cabinClassId " +
            "AND sd.ticket_type_id = :ticketTypeId) AS fd " +
            "JOIN schedule sch ON fd.schedule_id = sch.id", nativeQuery = true)
    List<Object[]> findTicketCabinDetail(
            @Param("scheduleId") UUID scheduleId,
            @Param("toId") UUID toId,
            @Param("fromId") UUID fromId,
            @Param("cabinClassId") UUID cabinClassId,
            @Param("ticketTypeId") UUID ticketTypeId);
}
