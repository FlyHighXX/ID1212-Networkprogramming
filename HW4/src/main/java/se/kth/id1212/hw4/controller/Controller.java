package se.kth.id1212.hw4.controller;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import se.kth.id1212.hw4.integration.DBAO;
import se.kth.id1212.hw4.model.Converter;
import se.kth.id1212.hw4.model.CurrencyEntity;
import se.kth.id1212.hw4.view.Conversion;
import se.kth.id1212.hw4.view.Currency;

/**
 *
 * @author Diaco Uthman
 */
@Stateless
public class Controller {
    @EJB
    private DBAO dbao;
    private final Converter converter = new Converter();
    public void registerNewCurrency(Currency currency) {
        dbao.addCurrency(new CurrencyEntity(currency.getCurrencyName(),currency.getCurrencyIndex()));
    }

    public double newConversion(Conversion conversion) {
        return converter.makeConversion(conversion);
    }
    
    public Currency findCurrency(String id){
        CurrencyEntity currencyEntity = this.dbao.findCurrency(id);
        return new Currency(currencyEntity.getName(),currencyEntity.getIndex());
    }

    public List<Currency> findAllCurrencies() {
        return dbao.findAllCurrencies();
    }
    
}
