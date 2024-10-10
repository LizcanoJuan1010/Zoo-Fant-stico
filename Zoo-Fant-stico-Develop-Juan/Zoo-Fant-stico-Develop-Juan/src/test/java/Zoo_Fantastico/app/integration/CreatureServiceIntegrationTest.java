package Zoo_Fantastico.app.integration;



import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import Zoo_Fantastico.app.model.Creature; // Importa el modelo de Creature
import Zoo_Fantastico.app.repository.CreatureRepository; // Importa el repositorio
import Zoo_Fantastico.app.service.CreatureService; // Importa el servicio
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest // Indica que es una prueba de integración
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // Para que no reemplace la configuración de H2
public class CreatureServiceIntegrationTest {

    @Autowired
    private CreatureService creatureService;

    @Autowired
    private CreatureRepository creatureRepository;

    @Test
    void testCreateCreature_ShouldPersistInDatabase() {
        // Crea una nueva criatura
        Creature creature = new Creature();
        creature.setName("Unicornio");
        creature.setDangerLevel(5);

        // Guarda la criatura en la base de datos a través del servicio
        creatureService.createCreature(creature);

        // Busca la criatura por ID y verifica que esté presente
        Optional<Creature> foundCreature = creatureRepository.findById(creature.getId());
        assertTrue(foundCreature.isPresent());
        assertEquals("Unicornio", foundCreature.get().getName());
    }

    @Test
    void testUpdateCreature_ShouldUpdateInDatabase() {
        // Crea y guarda una criatura
        Creature creature = new Creature();
        creature.setName("Dragón");
        creature.setDangerLevel(3);
        creatureService.createCreature(creature);

        // Actualiza el nombre de la criatura
        creature.setName("Dragón Actualizado");
        creatureService.updateCreature(creature);

        // Verifica que el cambio se haya reflejado en la base de datos
        Optional<Creature> foundCreature = creatureRepository.findById(creature.getId());
        assertTrue(foundCreature.isPresent());
        assertEquals("Dragón Actualizado", foundCreature.get().getName());
    }

    @Test
    void testDeleteCreature_ShouldRemoveFromDatabase() {
        // Crea y guarda una criatura
        Creature creature = new Creature();
        creature.setName("Serpiente");
        creature.setDangerLevel(2);
        creatureService.createCreature(creature);

        // Elimina la criatura de la base de datos
        Long creatureId = creature.getId();
        creatureService.deleteCreature(creatureId);

        // Verifica que la criatura ya no exista en la base de datos
        Optional<Creature> foundCreature = creatureRepository.findById(creatureId);
        assertFalse(foundCreature.isPresent());
    }
}
