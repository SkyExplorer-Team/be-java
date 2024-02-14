package synrgy.finalproject.skyexplorer.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import synrgy.finalproject.skyexplorer.model.dto.TravelDocumentDTO;
import synrgy.finalproject.skyexplorer.model.entity.TravelDocument;
import synrgy.finalproject.skyexplorer.model.entity.Users;
import synrgy.finalproject.skyexplorer.repository.TravelDocumentRepository;
import synrgy.finalproject.skyexplorer.repository.UsersRepository;

import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class TravelDocumentService {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private TravelDocumentRepository travelDocumentRepository;

    public TravelDocument updateDocument(UUID id, TravelDocumentDTO travelDocumentDTO) {
        Optional<TravelDocument> optionalTravelDocument = travelDocumentRepository.findById(id);
        if (optionalTravelDocument.isPresent()) {
            TravelDocument travelDocument = optionalTravelDocument.get();
            travelDocument.setPassportNumber(travelDocumentDTO.getPassportNumber());
            travelDocument.setIssueDate(travelDocumentDTO.getIssueDate());
            travelDocument.setExpiryDate(travelDocumentDTO.getExpiryDate());
            travelDocument.setIssuingCountryPassport(travelDocumentDTO.getIssuingCountryPassport());
            travelDocument.setNationalIDNumber(travelDocumentDTO.getNationalIDNumber());
            travelDocument.setIssuingCountryNational(travelDocumentDTO.getIssuingCountryNational());
            travelDocument.setFirstName(travelDocumentDTO.getFirstName());
            travelDocument.setLastName(travelDocumentDTO.getLastName());
            travelDocument.setPhone(travelDocumentDTO.getPhone());

            return travelDocumentRepository.save(travelDocument);
        } else {
            return null;
        }

    }

    public Optional<TravelDocument> getTravelDocumentByUserId(UUID userId) {
        Optional<Users> optionalUser = usersRepository.findById(userId);
        if (optionalUser.isPresent()) {
            Users user = optionalUser.get();
            return Optional.ofNullable(user.getTravelDocument());
        } else {
            return Optional.empty();
        }
    }
}
