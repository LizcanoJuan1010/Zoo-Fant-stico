package Zoo_Fantastico.app.Service;

import java.util.*;
import Zoo_Fantastico.app.Creature.Creature;
import Zoo_Fantastico.app.Repository.CreatureRepository;
//import Zoo_Fantastico.app.ResourceNotFoundException.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

//implementar lógica de negocio
@Service
public class CreatureService {
    private final CreatureRepository creatureRepository;
    @Autowired
    public CreatureService(CreatureRepository creatureRepository) {
        this.creatureRepository = creatureRepository;
    }
    public Creature createCreature(Creature creature) {
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
            creatureRepository.delete(creature);
        } else {
            throw new IllegalStateException("Cannot delete a creature in critical health");
        }
    }
}


