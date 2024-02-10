package synrgy.finalproject.skyexplorer.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import synrgy.finalproject.skyexplorer.model.dto.request.AirportDto;
import synrgy.finalproject.skyexplorer.model.dto.response.ResponseDto;
import synrgy.finalproject.skyexplorer.model.entity.Airport;
import synrgy.finalproject.skyexplorer.service.AirportService;
import synrgy.finalproject.skyexplorer.utils.ResponseGeneral;

import java.util.HashMap;
import java.util.List;

import static synrgy.finalproject.skyexplorer.utils.ResponseGeneral.generatedResponse;

@RestController
@RequestMapping("/airport")
@RequiredArgsConstructor
@Slf4j
public class AirportController {

    private final AirportService airportService;

//    ENDPOINT FOR GET AIRPORTS
    @GetMapping
    public ResponseEntity<?> getAirports(){
        List<Airport> airports = airportService.getAll();
        return generatedResponse("airports", airports);
    }

//    ENDPOINT FOR CREATE AIRPORTS
    @PostMapping
    public ResponseEntity<?> createAirport(@RequestBody AirportDto newAirportDto){
        var airportSaved = airportService.create(newAirportDto);
        return generatedResponse("airports", airportSaved);
    }

}
