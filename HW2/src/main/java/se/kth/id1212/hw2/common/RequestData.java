package se.kth.id1212.hw2.common;

/**
 *
 * @author Diaco Uthman
 */
public class RequestData {
    private int intValue;
    private String stringValue;
    
    public void setIntValue(int val){
        this.intValue=val;
    }
    
    public void setStringValue(String val){
        this.stringValue=val;
    }
    
    public int getIntValue(){
        return this.intValue;
    }
    
    public String getStringValue(){
        return this.stringValue;
    }
}
