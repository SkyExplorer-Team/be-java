package synrgy.finalproject.skyexplorer.service;


import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import synrgy.finalproject.skyexplorer.model.dto.request.AirportDto;
import synrgy.finalproject.skyexplorer.model.entity.Airport;
import synrgy.finalproject.skyexplorer.model.entity.National;
import synrgy.finalproject.skyexplorer.model.mapper.AirportMapper;
import synrgy.finalproject.skyexplorer.repository.AirportRepository;
import synrgy.finalproject.skyexplorer.repository.NationalRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AirportService {
    private final AirportRepository airportRepository;
    private final AirportMapper airportMapper;
    private final NationalRepository nationalRepository;
    public List<Airport> getAll() {
        return airportRepository.findAll();
    }

    public Airport create(AirportDto newAirportDto) {
        Airport airport = airportMapper.airportDtoToAirportMapper(newAirportDto);
        Optional<National> nationalFound = nationalRepository.findById(newAirportDto.nationalId());
        nationalFound.ifPresent(airport::setNational);
        airport.setNational(nationalFound.get());
        return airportRepository.save(airport);
    }
}
