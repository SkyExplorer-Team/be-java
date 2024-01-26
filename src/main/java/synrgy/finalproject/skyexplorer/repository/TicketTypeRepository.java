package synrgy.finalproject.skyexplorer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import synrgy.finalproject.skyexplorer.model.entity.TicketType;

import java.util.UUID;

public interface TicketTypeRepository extends JpaRepository<TicketType, UUID> {
    boolean existsByName(String typeName);
}
