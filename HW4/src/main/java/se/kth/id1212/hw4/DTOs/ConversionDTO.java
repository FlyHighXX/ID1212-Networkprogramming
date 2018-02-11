package se.kth.id1212.hw4.DTOs;

import se.kth.id1212.hw4.view.Currency;

/**
 *
 * @author Diaco Uthman
 */
public interface ConversionDTO {
    public Currency getFromCurrency();
    public Currency getToCurrency();
    public double getAmount();
}
