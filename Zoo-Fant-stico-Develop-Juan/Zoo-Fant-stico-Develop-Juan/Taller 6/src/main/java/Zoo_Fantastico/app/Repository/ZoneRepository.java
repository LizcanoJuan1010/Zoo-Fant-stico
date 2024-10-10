package Zoo_Fantastico.app.Repository;

import Zoo_Fantastico.app.Creature.Zone;
import org.springframework.data.jpa.repository.JpaRepository;

//definir repositorio
public interface ZoneRepository extends JpaRepository<Zone, Long> {
}

