package org.frepl.visitor;

import java.util.ArrayList;

public class FRePLVariablesVisitor extends FRePLBaseVisitor<Object> {

    public final FRePLSymbolsTable SymbolTable = new FRePLSymbolsTable();
    public final FRePLExpressionVisitor expVisitor = new FRePLExpressionVisitor(this);
    @Override
    public Object visitDeclarationWithoutValue(FRePLParser.DeclarationWithoutValueContext ctx) {
        switch(ctx.TYPE().getText()){
            case "int" -> SymbolTable.currentTable.put(ctx.IDENTIFIER().getText(),0);
            case "float" -> SymbolTable.currentTable.put(ctx.IDENTIFIER().getText(),0.0);
            case "boolean" -> SymbolTable.currentTable.put(ctx.IDENTIFIER().getText(),false);
            case "string" -> SymbolTable.currentTable.put(ctx.IDENTIFIER().getText(),"");
            case "char" -> SymbolTable.currentTable.put(ctx.IDENTIFIER().getText(),' ');
            case "int[]" -> SymbolTable.currentTable.put(ctx.IDENTIFIER().getText(),new ArrayList<Integer>());
            case "float[]" -> SymbolTable.currentTable.put(ctx.IDENTIFIER().getText(),new ArrayList<Float>());
            case "boolean[]" -> SymbolTable.currentTable.put(ctx.IDENTIFIER().getText(),new ArrayList<Boolean>());
            case "string[]" -> SymbolTable.currentTable.put(ctx.IDENTIFIER().getText(),new ArrayList<String>());
            case "char[]" -> SymbolTable.currentTable.put(ctx.IDENTIFIER().getText(),new ArrayList<Character>());
        }
        return null;
    }

    @Override
    public Object visitDeclarationWithValue(FRePLParser.DeclarationWithValueContext ctx) {
        if(SymbolTable.currentTable.containsKey(ctx.IDENTIFIER().getText())){
            throw new IllegalArgumentException("Variable name already taken");
        }
        Object value = expVisitor.visit(ctx.expression());
        DeclareWithValue(ctx.IDENTIFIER().getText(),value,ctx.TYPE().getText());
        return null;
    }
    private void DeclareWithValue(String key,Object value, String type){
        switch(type){
            case "string" -> {
                SymbolTable.currentTable.put(key,value.toString());
            }
            case "int" -> {
                if(value instanceof Integer) SymbolTable.currentTable.put(key,value);
                else throw new IllegalArgumentException("Type mismatch");
            }
            case "float" -> {
                if(value instanceof Float) SymbolTable.currentTable.put(key,value);
                else throw new IllegalArgumentException("Type mismatch");
            }
            case "bool" -> {
                if(value instanceof Boolean) SymbolTable.currentTable.put(key,value);
                else throw new IllegalArgumentException("Type mismatch");
            }
            case "char" -> {
                if(value instanceof String && ((String) value).length() == 1) SymbolTable.currentTable.put(key,value);
                else{
                    System.out.println(value);
                    throw new IllegalArgumentException("Type mismatch");
                }
            }
            case "string[]" -> {
                if(value instanceof ArrayList<?>){
                    SymbolTable.currentTable.put(key,value);
                }
                else throw new IllegalArgumentException("Type mismatch");
            }
            case "int[]" -> {
                if(value instanceof ArrayList<?>){
                    SymbolTable.currentTable.put(key,value);
                }
                else throw new IllegalArgumentException("Type mismatch");
            }
            case "float[]" -> {
                if(value instanceof ArrayList<?>){
                    SymbolTable.currentTable.put(key,value);
                }
                else throw new IllegalArgumentException("Type mismatch");
            }
            case "bool[]" -> {
                if(value instanceof ArrayList<?>){
                    SymbolTable.currentTable.put(key,value);
                }
                else throw new IllegalArgumentException("Type mismatch");
            }
            case "char[]" -> {
                if(value instanceof ArrayList<?>){
                    SymbolTable.currentTable.put(key,value);
                }
                else throw new IllegalArgumentException("Type mismatch");
            }
        }
    }

    @Override
    public Object visitDeclarationImplicitType(FRePLParser.DeclarationImplicitTypeContext ctx) {
        if(SymbolTable.currentTable.containsKey(ctx.IDENTIFIER().getText())){
            throw new IllegalArgumentException("Variable name already taken");
        }
        Object data = ctx.expression().getText();
        SymbolTable.currentTable.put(ctx.IDENTIFIER().getText(),expVisitor.visit(ctx.expression()));
        return null;
    }

    @Override
    public Object visitAssignment(FRePLParser.AssignmentContext ctx) {
        Object currentValue = SymbolTable.currentTable.get(ctx.IDENTIFIER().getText());
        Object nextValue = expVisitor.visit(ctx.expression());

        if(currentValue.getClass() != nextValue.getClass()){
            throw new IllegalArgumentException("Attempted variable `" + ctx.IDENTIFIER().getText() + "` assignment to a different type");
        }
        SymbolTable.currentTable.put(ctx.IDENTIFIER().getText(),nextValue);
        return null;
    }


}
