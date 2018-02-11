package se.kth.id1212.hw4.view;

import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import se.kth.id1212.hw4.controller.Controller;

/**
 * This class is a managed bean for the conversion of currencices used in the application.
 * @author Diaco Uthman
 */
@Named(value = "converterHandler")
@RequestScoped
public class ConverterHandler {
    @EJB
    Controller controller;
    
    private String fromCurrency;
    private String toCurrency;
    private double amountToConvert;
    private double resultOfConversion;

    public double getResultOfConversion() {
        return resultOfConversion;
    }

    public double getAmountToConvert() {
        return amountToConvert;
    }

    public void setAmountToConvert(double amountToConvert) {
        this.amountToConvert = amountToConvert;
    }

    public String convert(){
        this.resultOfConversion=this.controller.newConversion(new Conversion(controller.findCurrency(this.fromCurrency),
                                                            controller.findCurrency(this.toCurrency),
                                                            this.amountToConvert));
        return "theend";
    }
    
    public String getFromCurrency() {
        return fromCurrency;
    }

    public void setFromCurrency(String fromCurrency) {
        this.fromCurrency = fromCurrency;
    }

    public String getToCurrency() {
        return toCurrency;
    }

    public void setToCurrency(String toCurrency) {
        this.toCurrency = toCurrency;
    }
}
