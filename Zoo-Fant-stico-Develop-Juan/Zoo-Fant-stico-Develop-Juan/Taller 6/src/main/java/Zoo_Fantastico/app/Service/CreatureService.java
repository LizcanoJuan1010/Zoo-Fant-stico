package Zoo_Fantastico.app.Service;

import java.util.*;
import Zoo_Fantastico.app.Creature.Creature;
import Zoo_Fantastico.app.Creature.Zone;
import Zoo_Fantastico.app.Repository.CreatureRepository;
//import Zoo_Fantastico.app.ResourceNotFoundException.ResourceNotFoundException;
import Zoo_Fantastico.app.Repository.ZoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

//implementar lógica de negocio
@Service
public class CreatureService {
    private final CreatureRepository creatureRepository;
    private final ZoneRepository zoneRepository;
    @Autowired
    public CreatureService(CreatureRepository creatureRepository, ZoneRepository zoneRepository) {
        this.creatureRepository = creatureRepository;
        this.zoneRepository = zoneRepository;
    }


    public Creature createCreature(Creature creature) {
        // Validar que la criatura tenga una zona asignada
       if (creature.getZone() == null || creature.getZone().getId() == null) {
            throw new IllegalArgumentException("La criatura debe estar asignada a una zona");
        }

        // Buscar la zona a la que se asignará la criatura
        Zone zone = zoneRepository.findById(creature.getZone().getId())
                .orElseThrow(() -> new RuntimeException("Zona no encontrada"));

        // Incrementar la población de la zona en 1
        zone.setPoblation(zone.getPoblation() + 1);

        // Guardar la zona con la población actualizada
        zoneRepository.save(zone);

        // Guardar la nueva criatura asociada a la zona
        return creatureRepository.save(creature);
    }


    public List<Creature> getAllCreatures() {
        return creatureRepository.findAll();
    }


    public Creature getCreatureById(Long id) {
        try {
            return creatureRepository.findById(id)
                    .orElseThrow(() -> new Exception("Creature not found"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Creature updateCreature(Long id, Creature updatedCreature) {
        Creature creature = getCreatureById(id);
        creature.setName(updatedCreature.getName());
        creature.setSpecies(updatedCreature.getSpecies());
        creature.setSize(updatedCreature.getSize());
        creature.setDangerLevel(updatedCreature.getDangerLevel());
        creature.setHealthStatus(updatedCreature.getHealthStatus());
        return creatureRepository.save(creature);
    }

    public void deleteCreature(Long id) {
        Creature creature = getCreatureById(id);


        if (!"critical".equals(creature.getHealthStatus())) {

            Zone zone = creature.getZone();
            if (zone != null) {
                zone.setPoblation(zone.getPoblation() - 1);
                zoneRepository.save(zone);
            }
            creatureRepository.delete(creature);
        } else {
            throw new IllegalStateException("Cannot delete a creature in critical health");
        }
    }

}


