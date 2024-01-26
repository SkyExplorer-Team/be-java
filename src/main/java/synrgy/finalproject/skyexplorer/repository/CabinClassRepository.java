package synrgy.finalproject.skyexplorer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import synrgy.finalproject.skyexplorer.model.entity.CabinClass;

import java.util.UUID;

public interface CabinClassRepository extends JpaRepository<CabinClass, UUID> {
    CabinClass findByName(String upperCase);
}
