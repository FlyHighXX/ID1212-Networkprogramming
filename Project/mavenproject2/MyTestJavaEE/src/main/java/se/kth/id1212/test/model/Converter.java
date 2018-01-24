/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.kth.id1212.test.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 *
 * @author Diaco Uthman
 */
@Entity
public class Converter implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    private Currency fromCurr;
    @ManyToOne
    private Currency toCurr;
    private double amount;

    public Converter() {
    }
    
    public Converter(double amount, Currency fromCurr, Currency toCurr){
        this.fromCurr=fromCurr;
        this.toCurr=toCurr;
        this.amount=amount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Converter)) {
            return false;
        }
        Converter other = (Converter) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "se.kth.id1212.test.view.Converter[ id=" + id + " ]";
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getAmount() {
        return amount;
    }

    public double makeConversion() {
        return amount * (this.toCurr.getIndex()/this.fromCurr.getIndex());
    }
}
