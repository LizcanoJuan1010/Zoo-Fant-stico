package Zoo_Fantastico.app;

//implementación de pruebas unitarias

import Zoo_Fantastico.app.Creature.Zone;
import Zoo_Fantastico.app.Repository.CreatureRepository;
import Zoo_Fantastico.app.Repository.ZoneRepository;
import Zoo_Fantastico.app.Service.CreatureService;
import Zoo_Fantastico.app.Service.ZoneService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ZoneServiceTest {
    @Mock
    private CreatureRepository creatureRepository;
    @InjectMocks
    private CreatureService creatureService;
    @Mock
    private ZoneRepository zoneRepository;
    @InjectMocks
    private ZoneService zoneService;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void testCreateZones_ShouldReturnSavedZones(){
      //Arrege
      Zone zone = new Zone();
      zone.setId(1L);
      zone.setName("CieloNordico");
      zone.setDescription("A peleal en el Balhala para revivir y comer un bufete!!!");
      zone.setCapacity(1000);
      zone.setPoblation(452);

      when(zoneRepository.save(any(Zone.class))).thenReturn(zone);
      //Act
      Zone savedZone = zoneService.createZone(zone);
      //Assert
       assertNotNull(savedZone);
       assertEquals(1L,zone.getId());
    }
    @Test
    void  testGetZoneById_ShouldReturnZoneWhenExists(){
        //Arrege
        Long id= 1L;
        Zone zone = new Zone();
        zone.setId(1L);
        zone.setName("CieloNordico");
        zone.setDescription("A peleal en el Balhala para revivir y comer un bufete!!!");
        zone.setCapacity(1000);
        zone.setPoblation(452);

        when(zoneRepository.findById(id)).thenReturn(Optional.of(zone));

        //Act

        Zone savedZone= zoneService.getZoneById(id);
        //Assert
        assertNotNull(savedZone);
        assertEquals(id,savedZone.getId());
    }

    @Test
    void  testGetZoneById_ShouldReturnZoneWhenDoesntExists(){
        //Arrege
        Long id= 1L;


        when(zoneRepository.findById(id)).thenReturn(Optional.empty());

        //Act
        //Assert
        assertThrows(RuntimeException.class,()->
        {zoneService.getZoneById(id);});
        verify((zoneRepository),times(1)).findById(id);
    }



    @Test
    void testUpdateZone_ShouldUpdateZone_WhenZoneExists(){
        //Arrange
        Long id=1L;

        Zone zone = new Zone();
        zone.setId(1L);
        zone.setName("Paraiso");
        Zone updatedZone = new Zone();
        updatedZone.setName("Paraiso Pastafarista");
        when(zoneRepository.findById(id)).thenReturn(Optional.of(zone));
        when(zoneRepository.save(any(Zone.class))).thenReturn(updatedZone);
        //Act
        Zone zonePepe = zoneService.updateZone(id,updatedZone);
        //Assert
        assertNotNull(zonePepe);
        assertEquals("Paraiso Pastafarista",zonePepe.getName());

        verify(zoneRepository,times(1)).findById(id);
        verify(zoneRepository,times(1)).save(any(Zone.class));
    }

    @Test
    void testUpdateZoneCreature_ShouldThrowException_WhenCreatureDoesntExists(){

        //Arrage
        Long id=1L;
        Zone updatedZone = new Zone();
        updatedZone.setId(id);
        when(zoneRepository.findById(id)).thenReturn(Optional.empty());

        //Act//Assert
        assertThrows(RuntimeException.class,()->{
            zoneService.updateZone(id,updatedZone);
        });

        verify(zoneRepository,times(1)).findById(id);
        verify(zoneRepository,never()).save(any(Zone.class));

    }

    @Test
    void  testDeleteZone_ShouldDelete(){
        //Arrage
        Long id= 1L;

        Zone zone= new Zone();
        zone.setId(id);
        zone.setCapacity(6);
        zone.setPoblation(0);//Solo elimina si es 0
        zone.setName("Laberinto Infinito");

        when(zoneRepository.findById(id)).thenReturn(Optional.of(zone));
        //Act
        zoneService.deleteZone(id);
        //Assert
        verify(zoneRepository,times(1)).findById(id);
        verify(zoneRepository,times(1)).delete(zone);


    }

    @Test
    void  testDeleteZoneWhenItsPoblationIsMoreThan0_ShouldThrowException(){ //Arrage
        Long id= 1L;

        Zone zone= new Zone();
        zone.setId(id);
        zone.setCapacity(6);
        zone.setPoblation(3);
        zone.setName("Laberinto Infinito");
        when(zoneRepository.findById(id)).thenReturn(Optional.of(zone));
        assertThrows(RuntimeException.class,()->{
            zoneService.deleteZone(id);
        });
        verify(zoneRepository,times(1)).findById(id);
        verify(zoneRepository,never()).save(any(Zone.class));

    }

}
