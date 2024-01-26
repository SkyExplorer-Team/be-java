package synrgy.finalproject.skyexplorer.seeder;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import synrgy.finalproject.skyexplorer.model.entity.CabinClass;
import synrgy.finalproject.skyexplorer.repository.CabinClassRepository;

import java.util.Arrays;
import java.util.UUID;

@Component
public class CabinClassDataSeeder implements CommandLineRunner {

    private final CabinClassRepository cabinClassRepository;

    public CabinClassDataSeeder(CabinClassRepository cabinClassRepository) {
        this.cabinClassRepository = cabinClassRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Arrays.asList("ECONOMY", "BUSINESS", "FIRST")
                .forEach(classType -> {
                    CabinClass existingCabinClass = cabinClassRepository.findByName(classType.toUpperCase());
                    if (existingCabinClass == null) {
                        cabinClassRepository.save(new CabinClass(UUID.randomUUID(), classType.toUpperCase()));
                    }
                });
    }
}
