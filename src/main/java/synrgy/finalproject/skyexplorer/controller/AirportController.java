package synrgy.finalproject.skyexplorer.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import synrgy.finalproject.skyexplorer.model.dto.response.ResponseDto;
import synrgy.finalproject.skyexplorer.model.entity.Airport;
import synrgy.finalproject.skyexplorer.service.AirportService;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/airport")
@RequiredArgsConstructor
public class AirportController {

    private final AirportService airportService;

//    ENDPOINT FOR GET AIRPORTS
    @GetMapping()
    public ResponseEntity<?> getAirports(){
        List<Airport> airports = airportService.getAll();
        HashMap<String, List<Airport>> maps = new HashMap<>() ;
        maps.put("airport", airports);
        return ResponseEntity.ok(new ResponseDto("succes", maps));
    }
}
