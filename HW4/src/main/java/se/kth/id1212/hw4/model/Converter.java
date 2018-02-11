package se.kth.id1212.hw4.model;

import se.kth.id1212.hw4.view.Conversion;

/**
 *
 * @author Diaco Uthman
 */
public class Converter {

    public double makeConversion(Conversion conversion) {
        return conversion.getAmount() * (conversion.getFromCurrency().getCurrencyIndex()/conversion.getToCurrency().getCurrencyIndex());
    }
    
}
