package se.kth.id1212.controller;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import se.kth.id1212.test.model.Currency;

/**
 *
 * @author Diaco Uthman
 */
@Stateless
public class CurrencyFacade extends AbstractFacade<Currency> {
    @PersistenceContext(unitName = "se.kth.id1212_Controller_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    public Currency createCurrency(String name, double index){
        return new Currency(name,index);  
    }
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CurrencyFacade() {
        super(Currency.class);
    }
    
}
