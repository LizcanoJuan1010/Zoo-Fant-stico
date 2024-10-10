package Zoo_Fantastico.app.Unit;

//implementación de pruebas unitarias
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.util.Optional;

import Zoo_Fantastico.app.Creature.Creature;
import Zoo_Fantastico.app.Creature.Zone;
import Zoo_Fantastico.app.Repository.CreatureRepository;
import Zoo_Fantastico.app.Repository.ZoneRepository;
import Zoo_Fantastico.app.Service.ZoneService;
import Zoo_Fantastico.app.Service.CreatureService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class CreatureServiceTest {
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
	void testGetCreatureById_ShouldReturnCreature_WhenCreatureExists() {
// Arrange
		Long creatureId = 1L;
		Creature expectedCreature = new Creature();
		expectedCreature.setId(creatureId);
		when(creatureRepository.findById(creatureId)).thenReturn(Optional.of(expectedCreature));
// Act
		Creature actualCreature = creatureService.getCreatureById(creatureId);
// Assert
		assertNotNull(actualCreature);
		assertEquals(expectedCreature, actualCreature);
		verify(creatureRepository, times(1)).findById(creatureId);
	}
	@Test
	void testGetCreatureById_ShouldThrowException_WhenCreatureDoesNotExist() {
// Arrange
		Long creatureId = 2L;
		when(creatureRepository.findById(creatureId)).thenReturn(Optional.empty());
// Act & Assert
		assertThrows(Exception.class, () -> {
			creatureService.getCreatureById(creatureId);
		});
		verify(creatureRepository, times(1)).findById(creatureId);
	}

@Test
void testCreateCreature_ShouldReturnSavedCreature() {
	Creature creature = new Creature();
	Zone zona = new Zone();
	zona.setId(1L);
	zona.setName("a");

	creature.setName("Fénix");
	creature.setZone(zona);


	when(zoneRepository.findById(eq(1L))).thenReturn(Optional.of(zona));
	when(creatureRepository.save(any(Creature.class))).thenReturn(creature);

	Creature savedCreature = creatureService.createCreature(creature);

	assertNotNull(savedCreature);
	assertEquals("Fénix", savedCreature.getName());
}
@Test
void testGetCreatureById_ShouldReturneCreture_WhenCreatureExists(){
	//Arrange
	Long id= 1L;
	Creature expectedCreature= new Creature();
	expectedCreature.setId(id);
	when(creatureRepository.findById(id)).thenReturn(Optional.of(expectedCreature));
	//Act
	Creature actualCreature =creatureService.getCreatureById(id);

	//Assert
	assertNotNull(actualCreature);
	assertEquals(expectedCreature,actualCreature);
	verify(creatureRepository,times(1)).findById(id);
}

@Test
void testGetCreatureById_ShouldThrowException_WhenCreatureDoenstExists(){
	//Arrenge
	Long id=1L;
	when(creatureRepository.findById(id)).thenReturn(Optional.empty());
	//Assert
	assertThrows(RuntimeException.class,()->{
		creatureService.getCreatureById(id);
	});
	verify((creatureRepository),times(1)).findById(id);
}
@Test
void testUpdateCreatureCreature_ShouldUpdateACreature_WhenCreatureExists(){
	//Arrange
	Long id=1L;
	Creature creature = new Creature();
	Zone zone = new Zone();
	zone.setId(1L);
	zone.setName("Paraiso");

	creature.setName("Espiritu Santo");
	creature.setZone(zone);
	Creature updatedCreature = new Creature();
	updatedCreature.setName("EL HIJO");
	updatedCreature.setZone(zone);
	when(creatureRepository.findById(id)).thenReturn(Optional.of(creature));
	when(creatureRepository.save(any(Creature.class))).thenReturn(updatedCreature);
	//Act
	Creature creturePepe = creatureService.updateCreature(id,updatedCreature);
	//Assert
	assertNotNull(creturePepe);
	assertEquals("EL HIJO",creturePepe.getName());
	assertEquals(zone,creturePepe.getZone());

	verify(creatureRepository,times(1)).findById(id);
	verify(creatureRepository,times(1)).save(any(Creature.class));
}
	@Test
	void testUpdateCreatureCreature_ShouldThrowException_WhenCreatureDoesntExists(){

	//Arrage
	Long id=1L;
	Creature updatedCreature = new Creature();
	updatedCreature.setId(id);
	when(creatureRepository.findById(id)).thenReturn(Optional.empty());

	//Act//Assert
assertThrows(RuntimeException.class,()->{
	creatureService.updateCreature(id,updatedCreature);
});

 verify(creatureRepository,times(1)).findById(id);
 verify(creatureRepository,never()).save(any(Creature.class));

	}

@Test
void  testDeleteCreature_ShouldDelete(){
	//Arrage
	Long id= 1L;
	Creature creature= new Creature();
	creature.setName("Ojo que todo lo ve");
	creature.setHealthStatus("Excelent");
	creature.setId(id);
	Zone zone= new Zone();
	zone.setId(id);
	zone.setCapacity(3);
	zone.setPoblation(3);
	zone.setName("Cueva con ojos que todo lo ven");
	creature.setZone(zone);
	 when(creatureRepository.findById(id)).thenReturn(Optional.of(creature));
	 //Act
	creatureService.deleteCreature(id);
	//Assert
	verify(creatureRepository,times(1)).findById(id);
	verify(creatureRepository,times(1)).delete(creature);
	verify(zoneRepository,times(1)).save(any(Zone.class));//Por lo qeu si se borra es -1
	assertEquals(2,zone.getPoblation()); //Si son 3-1=2

	}

	@Test
	void  testDeleteCreatureWhenItsHealthStatusIsCritical_ShoulntdDelete(){
		//Arrage
		Long id= 1L;
		Creature creature= new Creature();
		creature.setName("Ojo que todo lo ve, Pero con covid");
		creature.setHealthStatus("critical");
		creature.setId(id);
		Zone zone= new Zone();
		zone.setId(id);
		zone.setCapacity(3);
		zone.setPoblation(3);
		zone.setName("Cueva con ojos que todo lo ven");
		creature.setZone(zone);
		when(creatureRepository.findById(id)).thenReturn(Optional.of(creature));
		assertThrows(RuntimeException.class,()->{
			creatureService.deleteCreature(id);
		});
		verify(creatureRepository,times(1)).findById(id);
		verify(creatureRepository,never()).save(any(Creature.class));
		verify(zoneRepository,never()).save(any(Zone.class));
	}
}



