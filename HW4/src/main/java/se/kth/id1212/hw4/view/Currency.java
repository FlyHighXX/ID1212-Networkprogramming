package se.kth.id1212.hw4.view;

import se.kth.id1212.hw4.DTOs.CurrencyDTO;

/**
 *
 * @author Diaco Uthman
 */
public class Currency implements CurrencyDTO{
    private final String currencyName;
    private final double currencyIndex;

    public String getCurrencyName() {
        return currencyName;
    }

    public double getCurrencyIndex() {
        return currencyIndex;
    }
    
    public Currency(String currencyName, double indexComparedToSEK) {
        this.currencyName=currencyName;
        this.currencyIndex=indexComparedToSEK;
    }
    
}