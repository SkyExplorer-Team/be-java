package synrgy.finalproject.skyexplorer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import synrgy.finalproject.skyexplorer.model.dto.FlightDetailDto;
import synrgy.finalproject.skyexplorer.repository.TicketCabinDetailRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class FlightDetailService {

    @Autowired
    private TicketCabinDetailRepository ticketCabinDetailRepository;

    public List<FlightDetailDto> getFlightDetails(UUID scheduleId, UUID toId,
                                                  UUID fromId, UUID cabinClassId, UUID ticketTypeId) {
        List<Object[]> resultList = ticketCabinDetailRepository.findTicketCabinDetail(scheduleId, toId, fromId, cabinClassId, ticketTypeId);
        List<FlightDetailDto> flightDetails = new ArrayList<>();

        for (Object[] result : resultList) {
            FlightDetailDto flightDetail = new FlightDetailDto();
            flightDetail.setScheduleId((UUID) result[0]);
            flightDetail.setDepartureTime((LocalDateTime) result[1]);
            flightDetail.setArrivalTime((LocalDateTime) result[2]);
            flightDetail.setDepartureAirport((String) result[3]);
            flightDetail.setDepartureCity((String) result[4]);
            flightDetail.setDepartureCountry((String) result[5]);
            flightDetail.setArrivalAirport((String) result[6]);
            flightDetail.setArrivalCity((String) result[7]);
            flightDetail.setArrivalCountry((String) result[8]);
            flightDetail.setAirplaneCode((String) result[9]);
            flightDetail.setAirplaneName((String) result[10]);
            flightDetail.setCabinClass((String) result[11]);
            flightDetail.setTicketType((String) result[12]);
            flightDetail.setCabinBaggage((String) result[13]);
            flightDetail.setCheckedBaggage((String) result[14]);
            flightDetail.setSeatSelection((String) result[15]);
            flightDetail.setBaggageDelayProtection((Boolean) result[16]);
            flightDetail.setMealPack((Boolean) result[17]);
            flightDetail.setCancellation((String) result[18]);
            flightDetail.setFlightChange((String) result[19]);
            flightDetail.setNoShow((String) result[20]);
            flightDetail.setPrice((BigDecimal) result[21]);
            flightDetails.add(flightDetail);
        }

        return flightDetails;
    }
}
