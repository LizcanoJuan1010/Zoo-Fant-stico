package Zoo_Fantastico.app.Controller;

import Zoo_Fantastico.app.Creature.Zone;
import Zoo_Fantastico.app.Service.ZoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//creación de controladores
//definir losendpoint del crud
@RestController
@RequestMapping("/api/zones")
public class ZoneController {
    private final ZoneService zoneService;
    @Autowired
    public ZoneController(ZoneService zoneService) {
        this.zoneService = zoneService;
    }

    @PostMapping
    public ResponseEntity<Zone> createCreature(@RequestBody Zone zone) {
        Zone newZone = zoneService.createZone(zone);
        return ResponseEntity.status(HttpStatus.CREATED).body(newZone);
    }

    @GetMapping
    public List<Zone> getAllZones() {
        return zoneService.getAllZones();
    }

    @GetMapping("/{id}")
    public Zone getZoneById(@PathVariable Long id) {
        return zoneService.getZoneById(id);
    }

    @PutMapping("/{id}")
    public Zone updateCreature(@PathVariable Long id, @RequestBody Zone updatedCreature) {
        return zoneService.updateZone(id, updatedCreature);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCreature(@PathVariable Long id) {
        zoneService.deleteZone(id);
        return ResponseEntity.noContent().build();

    }
}
