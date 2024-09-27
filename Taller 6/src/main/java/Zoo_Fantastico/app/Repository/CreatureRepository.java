package Zoo_Fantastico.app.Repository;

import Zoo_Fantastico.app.Creature.*;
import org.springframework.data.jpa.repository.JpaRepository;

//definir repositorio
public interface CreatureRepository extends JpaRepository<Creature, Long> {
}

