package synrgy.finalproject.skyexplorer.seeder;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import synrgy.finalproject.skyexplorer.model.entity.TicketType;
import synrgy.finalproject.skyexplorer.repository.TicketTypeRepository;

import java.util.Arrays;
import java.util.UUID;

@Component
public class TicketTypeDataSeeder implements CommandLineRunner {
    private final TicketTypeRepository ticketTypeRepository;

    public TicketTypeDataSeeder(TicketTypeRepository ticketTypeRepository) {
        this.ticketTypeRepository = ticketTypeRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Arrays.asList("SAVER", "FLEXI", "PLUS")
                .forEach(typeName -> {
                    if (!ticketTypeRepository.existsByName(typeName)) {
                        TicketType ticketType = new TicketType(UUID.randomUUID(), typeName);
                        ticketTypeRepository.save(ticketType);
                    }
                });
    }
}
