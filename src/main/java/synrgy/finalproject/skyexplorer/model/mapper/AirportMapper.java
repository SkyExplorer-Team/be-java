package synrgy.finalproject.skyexplorer.model.mapper;

import org.mapstruct.Mapper;
import synrgy.finalproject.skyexplorer.model.dto.request.AirportDto;
import synrgy.finalproject.skyexplorer.model.entity.Airport;

@Mapper
public interface AirportMapper  {

    Airport airportDtoToAirportMapper(AirportDto airportDto);

    AirportDto airportToAirportDtoMapper(Airport airport);
}
