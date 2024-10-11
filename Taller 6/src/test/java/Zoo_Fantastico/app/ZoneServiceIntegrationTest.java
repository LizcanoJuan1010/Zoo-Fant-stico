package Zoo_Fantastico.app;
import Zoo_Fantastico.app.Creature.Creature;
import Zoo_Fantastico.app.Creature.Zone;
import Zoo_Fantastico.app.Repository.CreatureRepository;
import Zoo_Fantastico.app.Repository.ZoneRepository;
import Zoo_Fantastico.app.Service.CreatureService;
import Zoo_Fantastico.app.Service.ZoneService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@ActiveProfiles("test")
public class ZoneServiceIntegrationTest {

    @Autowired
    private ZoneService zoneService;

    @Autowired
    private ZoneRepository zoneRepository;

    @Test
    void testCreateZone_ShouldPersistInDatabase() {
        Zone zone = new Zone();
        zone.setName("Zona Tropical");
        zone.setDescription("Zona con clima tropical y variedad de vegetación");
        zone.setCapacity(100);

        Zone savedZone = zoneService.createZone(zone);

        assertNotNull(savedZone);
        assertNotNull(savedZone.getId());
        assertEquals("Zona Tropical", savedZone.getName());

        Optional<Zone> foundZone = zoneRepository.findById(savedZone.getId());
        assertTrue(foundZone.isPresent());
        assertEquals("Zona Tropical", foundZone.get().getName());
        assertEquals("Zona con clima tropical y variedad de vegetación", foundZone.get().getDescription());
    }
    @Test
    void testUpdateZone_ShouldPersistChangesInDatabase() {
        Zone zone = new Zone();
        zone.setName("Zona Desértica");
        zone.setDescription("Zona árida con clima seco");
        zone.setCapacity(50);

        Zone savedZone = zoneService.createZone(zone);


        Zone updatedZone = new Zone();
        updatedZone.setName("Zona Desértica Modificada");
        updatedZone.setDescription("Zona árida modificada con clima seco extremo");
        updatedZone.setCapacity(60);

        Zone modifiedZone = zoneService.updateZone(savedZone.getId(), updatedZone);

        assertNotNull(modifiedZone);
        assertEquals("Zona Desértica Modificada", modifiedZone.getName());
        assertEquals("Zona árida modificada con clima seco extremo", modifiedZone.getDescription());
        assertEquals(60, modifiedZone.getCapacity());

        Optional<Zone> foundZone = zoneRepository.findById(savedZone.getId());
        assertTrue(foundZone.isPresent());
        assertEquals("Zona Desértica Modificada", foundZone.get().getName());
        assertEquals("Zona árida modificada con clima seco extremo", foundZone.get().getDescription());
    }

    @Test
    void testDeleteZone_ShouldRemoveZoneFromDatabase() {
        Zone zone = new Zone();
        zone.setName("Zona de Montaña");
        zone.setDescription("Zona con montañas y clima templado");
        zone.setCapacity(30);
        zone.setPoblation(0);

        Zone savedZone = zoneService.createZone(zone);


        zoneService.deleteZone(savedZone.getId());

        Optional<Zone> foundZone = zoneRepository.findById(savedZone.getId());
        assertFalse(foundZone.isPresent());
    }

    @Test
    void testCreateZone_ShouldNotPersistInDatabase() {
        Zone zone = new Zone();
        zone.setId(3L);
        zone.setName("Zona Fantasma");
        zone.setDescription("Esta zona no debería persistir");
        zone.setCapacity(0);

        Optional<Zone> foundZone = zoneRepository.findById(3L);
        assertFalse(foundZone.isPresent());
    }
    @Test
    void testDeleteZone_ShouldNotRemoveWhenConditionFails() {
        Zone zone = new Zone();
        zone.setName("Zona Protegida");
        zone.setDescription("No se debe eliminar debido a que tiene poblaciones");
        zone.setCapacity(50);
        zone.setPoblation(10);

        Zone savedZone = zoneService.createZone(zone);


        try {
            zoneService.deleteZone(savedZone.getId());
            fail("Debería haber lanzado una excepción al intentar eliminar una zona con población");
        } catch (IllegalStateException e) {

            Optional<Zone> foundZone = zoneRepository.findById(savedZone.getId());
            assertTrue(foundZone.isPresent());
            assertEquals("Zona Protegida", foundZone.get().getName());
        }
    }
    @Test
    void testUpdateZone_ShouldNotChangeWhenZoneNotFound() {
        Zone zone = new Zone();
        zone.setName("Zona Inexistente");
        zone.setDescription("Intento de actualización fallida");
        zone.setCapacity(100);


        Zone updatedZone = new Zone();
        updatedZone.setName("Zona Fantasma");
        updatedZone.setDescription("Esta actualización no debería persistir");
        updatedZone.setCapacity(0);


        try {
            zoneService.updateZone(999L, updatedZone); // ID ficticio que no debería existir
            fail("Debería haber lanzado una excepción al intentar actualizar una zona inexistente");
        } catch (RuntimeException e) {

            Optional<Zone> foundZone = zoneRepository.findById(999L);
            assertFalse(foundZone.isPresent());
        }
    }







}

