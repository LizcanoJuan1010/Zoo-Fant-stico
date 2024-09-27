package Zoo_Fantastico.app.Creature;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;


import lombok.Data;
import lombok.NoArgsConstructor;
//import javax.persistence.*; Here we had a problem with this library, however, for this reason I decided use jakarta instead of javax :)
import jakarta.persistence.*;
@Entity
@Data // Esto es opcional pueden usarlo o crear los getter y setters
@NoArgsConstructor

public class Zone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private double capacity;
    private double poblation;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getCapacity() {
        return capacity;
    }

    public void setCapacity(double capacity) {
        this.capacity = capacity;
    }

    public double getPoblation() {
        return poblation;
    }

    public void setPoblation(double poblation) {
        this.poblation = poblation;
    }
}
