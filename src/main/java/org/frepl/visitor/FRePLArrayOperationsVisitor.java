package org.frepl.visitor;

import java.util.ArrayList;

public class FRePLArrayOperationsVisitor extends FRePLBaseVisitor<Object> {
    private final FRePLVisitorImpl mainVisitor;
    public FRePLArrayOperationsVisitor(FRePLVisitorImpl mainVisitor){
        this.mainVisitor = mainVisitor;
    }

    @Override
    public Object visitArrayGetElement(FRePLParser.ArrayGetElementContext ctx) {
        String identifier = ctx.IDENTIFIER().getText();
        var list = mainVisitor.SymbolTable.currentTable.get(identifier);
        if(list.getClass() == ArrayList.class) {
            int size = ((ArrayList)list).size();
            int index = (Integer)(mainVisitor.visit(ctx.expression()));
            if(size <= index){
                throw new IllegalArgumentException("Index " + index + " out of bounds for length " + size);
            }
            return ((ArrayList) list).get(index);
        }
        return null;
    }
    @Override
    public Object visitArrayGetLength(FRePLParser.ArrayGetLengthContext ctx) {
        String identifier = ctx.IDENTIFIER().getText();
        var list = mainVisitor.SymbolTable.currentTable.get(identifier);
        if(list.getClass() == ArrayList.class) {
            return ((ArrayList)list).size();
        }
        return null;
    }
    @Override
    public Object visitArrayAddElement(FRePLParser.ArrayAddElementContext ctx) {
        Object array = mainVisitor.SymbolTable.currentTable.get(ctx.IDENTIFIER().getText());
        Object value = mainVisitor.visit(ctx.expression());
        Class<?> type = ((ArrayList<?>)array).get(0).getClass();
        if(type != value.getClass()){
            throw new IllegalArgumentException("Invalid assigned value type. Expected:" + type + ", got:" + value.getClass());
        }
        if(value instanceof Integer){
            ((ArrayList<Integer>) array).add((Integer)value);
        } else if(value instanceof Float){
            ((ArrayList<Float>) array).add((Float)value);
        } else if(value instanceof Boolean){
            ((ArrayList<Boolean>) array).add((Boolean) value);
        } else if(value instanceof Character){
            ((ArrayList<Character>) array).add((Character) value);
        } else if(value instanceof String){
            ((ArrayList<String>) array).add((String) value);
        } else {
            throw new IllegalArgumentException("Unknown assigned value type");
        }
        mainVisitor.SymbolTable.currentTable.put(ctx.IDENTIFIER().getText(),array);
        return null;
    }
    @Override
    public Object visitArrayRemoveAtIndex(FRePLParser.ArrayRemoveAtIndexContext ctx) {
        Object array = mainVisitor.SymbolTable.currentTable.get(ctx.IDENTIFIER().getText());
        Integer index = (Integer)mainVisitor.visit(ctx.expression());
        Integer arraySize = ((ArrayList<?>)array).size();
        if(index >= arraySize){
            throw new IllegalArgumentException("Attemping to remove element out of bounds");
        }
        ((ArrayList<?>)array).remove((int)index);
        mainVisitor.SymbolTable.currentTable.put(ctx.IDENTIFIER().getText(),array);
        return null;
    }
    @Override
    public Object visitArraySetElement(FRePLParser.ArraySetElementContext ctx) {
        Object array = mainVisitor.SymbolTable.currentTable.get(ctx.IDENTIFIER().getText());
        Integer index = (Integer)mainVisitor.visit(ctx.expression(0));
        Object value = mainVisitor.visit(ctx.expression(1));
        Class<?> type = ((ArrayList<?>)array).get(0).getClass();
        if(type != value.getClass()){
            throw new IllegalArgumentException("Invalid assigned value type. Expected:" + type + ", got:" + value.getClass());
        }
        if(value instanceof Integer){
            ((ArrayList<Integer>) array).set(index, (Integer)value);
        } else if(value instanceof Float){
            ((ArrayList<Float>) array).set(index, (Float)value);
        } else if(value instanceof Boolean){
            ((ArrayList<Boolean>) array).set(index, (Boolean) value);
        } else if(value instanceof Character){
            ((ArrayList<Character>) array).set(index, (Character) value);
        } else if(value instanceof String){
            ((ArrayList<String>) array).set(index, (String) value);
        } else {
            throw new IllegalArgumentException("Unknown assigned value type");
        }
        mainVisitor.SymbolTable.currentTable.put(ctx.IDENTIFIER().getText(),array);
        return null;
    }
}
