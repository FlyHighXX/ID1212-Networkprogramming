package se.kth.id1212.hw2.common;
/**
 *
 * @author Diaco Uthman
 */
public class ResponseData {
    private int intValue;
    private String stringValue;
    
    public void setStringValue(String val){
        this.stringValue=val;
    }
    
    public String getStringValue(){
        return this.stringValue;
    }

    public void setIntValue(int val) {
        this.intValue = val;
    }

    public int getIntValue() {
        return this.intValue;
    }

    @Override
    public String toString() {
        return this.stringValue;
    }
}