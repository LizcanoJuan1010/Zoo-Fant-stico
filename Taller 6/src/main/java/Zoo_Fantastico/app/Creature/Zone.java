package Zoo_Fantastico.app.Creature;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
        import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Zone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    private int capacity;

    @OneToMany(mappedBy = "zone", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Creature> creatures;

    public boolean hasCapacity() {
        return creatures.size() < capacity;
    }

    public boolean isEmpty() {
        return creatures.isEmpty();
    }
}
