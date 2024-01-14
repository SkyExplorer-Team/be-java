package synrgy.finalproject.skyexplorer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import synrgy.finalproject.skyexplorer.model.entity.National;

import java.util.UUID;

@Repository
public interface NationalRepository extends JpaRepository<National, UUID> {
}
