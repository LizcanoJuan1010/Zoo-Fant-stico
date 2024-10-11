package Zoo_Fantastico.app;
import Zoo_Fantastico.app.Creature.Creature;
import Zoo_Fantastico.app.Creature.Zone;
import Zoo_Fantastico.app.Repository.CreatureRepository;
import Zoo_Fantastico.app.Repository.ZoneRepository;
import Zoo_Fantastico.app.Service.CreatureService;
import Zoo_Fantastico.app.Service.ZoneService;
import org.antlr.v4.runtime.misc.LogManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
public class CreatureServiceIntegrationTest {

    @Autowired
    private CreatureService creatureService;

    @Autowired
    private CreatureRepository creatureRepository;
    @Autowired
    private ZoneService zoneService;
    @Autowired
    private ZoneRepository zoneRepository;

    @Test
    void testCreateCreature_ShouldPersistInDatabase() {
        Zone zona = new Zone();
        zona.setId(1L);
        zona.setName("Zona Fantástica");


        Creature creature = new Creature();
        creature.setName("Unicornio");
        creature.setDangerLevel(5);
        creature.setZone(zona);

        zoneService.createZone(zona);
        creatureService.createCreature(creature);

        Optional<Creature> foundCreature = creatureRepository.findById(creature.getId());

        assertTrue(foundCreature.isPresent());
        assertEquals("Unicornio", foundCreature.get().getName());
    }
    @Test
    void testUpdateCreature_ShouldPersistChangesInDatabase() {
        Zone zona = new Zone();
        zona.setName("Zona Fantástica");

        zoneService.createZone(zona);

        Creature creature = new Creature();
        creature.setName("Unicornio");
        creature.setDangerLevel(5);
        creature.setZone(zona);

        creatureService.createCreature(creature);

        // Actualizar la criatura
        creature.setName("Pegaso");
        creature.setDangerLevel(7);
        creatureService.updateCreature(creature.getId(),creature);
        Optional<Creature> foundCreature = creatureRepository.findById(creature.getId());

        assertTrue(foundCreature.isPresent());
        assertEquals("Pegaso", foundCreature.get().getName());
        assertEquals(7, foundCreature.get().getDangerLevel());
    }


    @Test
    void testDeleteCreature_ShouldRemoveCreatureFromDatabase() {
        Zone zona = new Zone();
        zona.setName("Zona Fantástica");

        zoneService.createZone(zona);

        Creature creature = new Creature();
        creature.setName("Unicornio");
        creature.setDangerLevel(5);
        creature.setZone(zona);

        creatureService.createCreature(creature);
        creatureService.deleteCreature(creature.getId());

        Optional<Creature> foundCreature = creatureRepository.findById(creature.getId());

        assertFalse(foundCreature.isPresent());
    }

    @Test
    void testCreateCreature_ShouldNotPersistInDatabase() {
        Creature creature = new Creature();
        creature.setId(1L);
        creature.setName("Criatura Fantasma");
        creature.setDangerLevel(10);



        Optional<Creature> foundCreature = creatureRepository.findById(creature.getId());
        assertFalse(foundCreature.isPresent());
    }
    @Test
    void testUpdateCreature_ShouldNotChangeWhenCreatureNotFound() {
        Creature creature = new Creature();
        creature.setName("Criatura Inexistente");
        creature.setDangerLevel(5);


        Creature updatedCreature = new Creature();
        updatedCreature.setName("Criatura Fantasma");
        updatedCreature.setDangerLevel(8);

        try {
            creatureService.updateCreature(999L, updatedCreature);
            fail("Debería haber lanzado una excepción al intentar actualizar una criatura inexistente");
        } catch (RuntimeException e) {

            Optional<Creature> foundCreature = creatureRepository.findById(999L);
            assertFalse(foundCreature.isPresent());
        }
    }

}
