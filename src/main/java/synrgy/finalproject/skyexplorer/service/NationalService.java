package synrgy.finalproject.skyexplorer.service;


import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import synrgy.finalproject.skyexplorer.model.dto.request.NationalDto;
import synrgy.finalproject.skyexplorer.model.entity.National;
import synrgy.finalproject.skyexplorer.repository.NationalRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NationalService {
    private final NationalRepository nationalRepository;
    
    public List<National> getAll() {
        return nationalRepository.findAll();
    }

    public National create(NationalDto nationalDto) {
        return nationalRepository.save(National.
                builder().
                name(nationalDto.name()).
                build());
    }
}
