package synrgy.finalproject.skyexplorer.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import synrgy.finalproject.skyexplorer.model.dto.FlightDetailDto;
import synrgy.finalproject.skyexplorer.model.dto.response.SuccessResponse;
import synrgy.finalproject.skyexplorer.service.FlightDetailService;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/flight_detail")
public class FlightDetailController {

    @Autowired
    private final FlightDetailService flightDetailService;

    @GetMapping
    public ResponseEntity<Object> getFlightDetails(@RequestParam UUID scheduleId,
                                                   @RequestParam UUID toId,
                                                   @RequestParam UUID fromId,
                                                   @RequestParam UUID cabinClassId,
                                                   @RequestParam UUID ticketTypeId) {
        try {
            List<FlightDetailDto> flightDetails = flightDetailService.getFlightDetails(scheduleId, toId, fromId, cabinClassId, ticketTypeId);
            return SuccessResponse.generateResponse("success", "Flight Detail retrieved successfully", flightDetails, HttpStatus.OK);
        } catch (Exception e) {
            return SuccessResponse.generateResponse("error", e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
