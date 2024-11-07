package Zoo_Fantastico.app.Service;


import Zoo_Fantastico.app.Creature.Zone;
import Zoo_Fantastico.app.Repository.ZoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

//implementar lógica de negocio
@Service
public class ZoneService {
    private final ZoneRepository zoneRepository;
    @Autowired
    public ZoneService(ZoneRepository zoneRepository) {
        this.zoneRepository = zoneRepository;
    }


    public Zone createZone(Zone zone) {
        return zoneRepository.save(zone);
    }


    public List<Zone> getAllZones() {
        return zoneRepository.findAll();
    }


    public Zone getZoneById(Long id) {
        try {
            return zoneRepository.findById(id)
                    .orElseThrow(() -> new Exception("Zone not found"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Zone updateZone(Long id, Zone updatedZone) {
        Zone zone = getZoneById(id);
        zone.setName(updatedZone.getName());
        zone.setDescription(updatedZone.getDescription());
        zone.setCapacity(updatedZone.getCapacity());
        return zoneRepository.save(zone);
    }

    public void deleteZone(Long id) {
        Zone zone = getZoneById(id);
        if (zone.getPoblation() == 0) {
            zoneRepository.delete(zone);
        } else {
            throw new IllegalStateException("Cannot delete a creature in critical health");
        }
    }
}


