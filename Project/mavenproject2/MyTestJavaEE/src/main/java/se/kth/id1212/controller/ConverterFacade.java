package se.kth.id1212.controller;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import se.kth.id1212.test.model.Converter;
import se.kth.id1212.test.model.Currency;

/**
 *
 * @author Diaco Uthman
 */
@Stateless
public class ConverterFacade extends AbstractFacade<Converter>{
    @PersistenceContext(unitName = "se.kth.id1212_Controller_war_1.0-SNAPSHOTPU")
    private EntityManager em;
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ConverterFacade() {
        super(Converter.class);
    }

    public double makeConversion(Converter converter) {
        return converter.makeConversion();
    }    

    public Converter createConversion(double amount, Currency fromCurrC, Currency toCurrC) {
        Converter thisConversion = new Converter(
                amount,
                fromCurrC,
                toCurrC
        );
        return thisConversion;
    }
}
