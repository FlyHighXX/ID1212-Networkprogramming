package se.kth.id1212.test.view;

import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Named;
import javax.inject.Inject;
import se.kth.id1212.controller.ConverterFacade;
import se.kth.id1212.controller.CurrencyFacade;
import se.kth.id1212.test.model.Converter;
import se.kth.id1212.test.model.Currency;

/**
 * 
 * @author Diaco Uthman
 */
@Named(value = "converterView")
@ConversationScoped
public class ConverterView implements Serializable {
    @EJB
    private ConverterFacade converterFacade;
    @EJB
    private CurrencyFacade currencyFacade;
    private String fromCurr;
    private String toCurr;
    private double amount;
    
    private String currencyName;
    private double index;
    
    private Currency fromCurrC;
    private Currency toCurrC;
    private double result;

    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    public double getIndex() {
        return index;
    }

    public void setIndex(double index) {
        this.index = index;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getToCurr() {
        return toCurr;
    }

    public void setToCurr(String toCurr) {
        this.toCurr = toCurr;
    }

    public String getFromCurr() {
        return fromCurr;
    }

    public void setFromCurr(String fromCurr) {
        this.fromCurr = fromCurr;
    }
    @Inject
    Conversation conversation;
    
    private void startConversation() {
        if (conversation.isTransient()) {
            conversation.begin();
        }
    }

    private void stopConversation() {
        if (!conversation.isTransient()) {
            conversation.end();
        }
    }
    
    public String makeConvert(){
        startConversation();
        // Creates a log of every conversion
        this.fromCurrC = this.currencyFacade.find(this.fromCurr);
        this.toCurrC = this.currencyFacade.find(this.toCurr);
        Converter conv = this.converterFacade.createConversion(this.amount,this.fromCurrC,this.toCurrC);
        this.converterFacade.create(conv);
        this.result = conv.makeConversion();
        return "theend";
    }
    
    public String returnHome(){
        return "index";
    }
    
    public double getResult(){
        return this.result;
    }
    
    public String addCurrency(){
        startConversation();
        this.currencyFacade.create(
                this.currencyFacade.createCurrency(
                        this.currencyName,
                        this.index)
        );
        return "index";
    }
    
    public List<Currency> getCurrencies(){
        return this.currencyFacade.findAll();
    }
    
    public int getNumberOfMessages(){
       return this.converterFacade.findAll().size();
    }
}
