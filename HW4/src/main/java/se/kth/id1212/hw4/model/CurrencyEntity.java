package se.kth.id1212.hw4.model;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 *
 * @author Diaco Uthman
 */
@Entity
public class CurrencyEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "NAME", nullable = false)
    private String name;
    
    @Column(name = "INDEX", nullable = false)
    private double index;

    public CurrencyEntity() {
    }

    public CurrencyEntity(String currencyName, double currencyIndex) {
        this.name=currencyName;
        this.index=currencyIndex;
    }

    public String getName() {
        return name;
    }

    public double getIndex() {
        return index;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CurrencyEntity)) {
            return false;
        }
        CurrencyEntity other = (CurrencyEntity) object;
        if ((this.name == null && other.name != null) || (this.name != null && !this.name.equals(other.name))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.name);
        return hash;
    }

    @Override
    public String toString() {
        return "se.kth.id1212.hw4.model.Currency[ id=" + name + " ]";
    }
}
