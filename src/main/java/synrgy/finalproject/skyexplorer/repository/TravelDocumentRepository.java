package synrgy.finalproject.skyexplorer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import synrgy.finalproject.skyexplorer.model.entity.TravelDocument;

import java.util.UUID;

public interface TravelDocumentRepository extends JpaRepository<TravelDocument, UUID> {
}
