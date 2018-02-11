package se.kth.id1212.hw4.view;

import se.kth.id1212.hw4.DTOs.ConversionDTO;

/**
 *
 * @author Diaco Uthman
 */
public class Conversion implements ConversionDTO{
    private Currency fromCurrency;
    private Currency toCurrency;
    private double amount;

    Conversion(Currency fromCurrency, Currency toCurrency, double amountToConvert) {
        this.fromCurrency=fromCurrency;
        this.toCurrency=toCurrency;
        this.amount=amountToConvert;
    }

    public Currency getFromCurrency() {
        return fromCurrency;
    }

    public Currency getToCurrency() {
        return toCurrency;
    }

    public double getAmount() {
        return amount;
    }
}
