package se.kth.id1212.hw4.view;

import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import se.kth.id1212.hw4.controller.Controller;

/**
 * This managed bean is responsible for handling the different currencies.
 * 
 * @author Diaco Uthman
 */
@Named(value = "currencyHandler")
@RequestScoped
public class CurrencyHandler {
    @EJB
    Controller controller;
    
    private String currencyName;
    private double indexComparedToSEK;
    
    public void registerNewCurrency(){
        this.controller.registerNewCurrency(new Currency(this.currencyName,this.indexComparedToSEK));
    }
    
    public List<Currency> getAllCurrencies(){
        return this.controller.findAllCurrencies(); 
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    public double getIndexComparedToSEK() {
        return indexComparedToSEK;
    }

    public void setIndexComparedToSEK(double indexComparedToSEK) {
        this.indexComparedToSEK = indexComparedToSEK;
    }
}
