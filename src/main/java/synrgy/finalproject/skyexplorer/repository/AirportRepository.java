package synrgy.finalproject.skyexplorer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import synrgy.finalproject.skyexplorer.model.entity.Airport;

import java.util.UUID;

@Repository
public interface AirportRepository extends JpaRepository<Airport, UUID> {
}
