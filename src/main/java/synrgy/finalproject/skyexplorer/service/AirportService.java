package synrgy.finalproject.skyexplorer.service;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import synrgy.finalproject.skyexplorer.model.entity.Airport;
import synrgy.finalproject.skyexplorer.repository.AirportRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AirportService {
    private final AirportRepository airportRepository;
    public List<Airport> getAll() {
        return airportRepository.findAll();
    }
}
