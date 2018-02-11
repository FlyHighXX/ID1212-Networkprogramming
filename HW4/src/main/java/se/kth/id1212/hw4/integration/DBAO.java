package se.kth.id1212.hw4.integration;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import se.kth.id1212.hw4.model.CurrencyEntity;
import se.kth.id1212.hw4.view.Currency;

/**
 * This class is represents the integration between the server and the databse connection.
 * @author Diaco Uthman
 */
@Stateless
public class DBAO {
    @PersistenceContext(unitName="bankPU")
    private EntityManager em;
    
    public void addCurrency(CurrencyEntity currency){
        em.persist(currency);
    }

    public CurrencyEntity findCurrency(String id) {
        return em.find(CurrencyEntity.class, id);
    }

    public List<Currency> findAllCurrencies() {
        Query query = em.createQuery("SELECT e FROM CurrencyEntity e");
        return (List<Currency>)query.getResultList();
    }
}
