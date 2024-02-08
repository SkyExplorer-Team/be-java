package synrgy.finalproject.skyexplorer.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import synrgy.finalproject.skyexplorer.model.dto.response.SuccessResponse;
import synrgy.finalproject.skyexplorer.model.entity.Schedule;
import synrgy.finalproject.skyexplorer.service.ScheduleDetailService;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/schedule-detail")
public class ScheduleDetailController {

    private final ScheduleDetailService scheduleDetailService;

    @GetMapping("/getSchedules")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<Object> getSchedulesByFilters(
            @RequestParam UUID cabinClassId,
            @RequestParam UUID ticketTypeId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam UUID fromAirportId,
            @RequestParam UUID toAirportId) {
        try {
            List<Schedule> schedules = scheduleDetailService.getSchedulesByCabinClassAndTicketTypeAndDate(
                    cabinClassId, ticketTypeId, date, fromAirportId, toAirportId);
            return SuccessResponse.generateResponse("success", "Schedules retrieved successfully", schedules, HttpStatus.OK);
        } catch (Exception e) {
            return SuccessResponse.generateResponse("error", e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getSchedulesLast")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<Object> getSchedulesByFiltersLast(
            @RequestParam UUID cabinClassId,
            @RequestParam UUID ticketTypeId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam UUID fromAirportId,
            @RequestParam UUID toAirportId) {
        try {
            List<Schedule> schedules = scheduleDetailService.getSchedulesByCabinClassAndTicketTypeAndDateLast(
                    cabinClassId, ticketTypeId, date, fromAirportId, toAirportId);
            return SuccessResponse.generateResponse("success", "Schedules retrieved successfully", schedules, HttpStatus.OK);
        } catch (Exception e) {
            return SuccessResponse.generateResponse("error", e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getSchedulesEarliestArrivel")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<Object> getSchedulesByFiltersEarliestArrivel(
            @RequestParam UUID cabinClassId,
            @RequestParam UUID ticketTypeId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam UUID fromAirportId,
            @RequestParam UUID toAirportId) {
        try {
            List<Schedule> schedules = scheduleDetailService.getSchedulesByCabinClassAndTicketTypeAndDateEarliestArrival(
                    cabinClassId, ticketTypeId, date, fromAirportId, toAirportId);
            return SuccessResponse.generateResponse("success", "Schedules retrieved successfully", schedules, HttpStatus.OK);
        } catch (Exception e) {
            return SuccessResponse.generateResponse("error", e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getSchedulesLastArrivel")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<Object> getSchedulesByFiltersLastArrivel(
            @RequestParam UUID cabinClassId,
            @RequestParam UUID ticketTypeId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam UUID fromAirportId,
            @RequestParam UUID toAirportId) {
        try {
            List<Schedule> schedules = scheduleDetailService.getSchedulesByCabinClassAndTicketTypeAndDateLastArrival(
                    cabinClassId, ticketTypeId, date, fromAirportId, toAirportId);
            return SuccessResponse.generateResponse("success", "Schedules retrieved successfully", schedules, HttpStatus.OK);
        } catch (Exception e) {
            return SuccessResponse.generateResponse("error", e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}