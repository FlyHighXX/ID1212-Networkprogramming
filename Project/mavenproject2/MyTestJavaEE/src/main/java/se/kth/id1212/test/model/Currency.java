package se.kth.id1212.test.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 *
 * @author Diaco Uthman
 */
@Entity
public class Currency implements Serializable {
    @Id
    private String name;
    private double index;

    public void setName(String name) {
        this.name = name;
    }

    public void setIndex(double index) {
        this.index = index;
    }

    public Currency() {
    }

    public String getName() {
        return name;
    }

    public double getIndex() {
        return index;
    }
    
    public Currency(String name, double index) {
        this.name=name;
        this.index=index;
    }
    
}
