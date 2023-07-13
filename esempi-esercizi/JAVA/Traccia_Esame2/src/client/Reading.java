package client;

import interfaces.IReading;

public class Reading implements IReading{

    private String type;
    private int value;

    protected static final long serialVersionUID=10;

    public Reading(String t, int v){
        type = t;
        value = v;
    }

    @Override
    public int getValue() {
        // TODO Auto-generated method stub
        return value;
    }

    @Override
    public String getType() {
        // TODO Auto-generated method stub
        return type;
    }
    
}
