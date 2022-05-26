package org.frepl.visitor;

public class FRePLReturnObject {
    Object value;
    public FRePLReturnObject(Object value){
        this.value = value;
    }
    public Object getValue(){
        return value;
    }
}
