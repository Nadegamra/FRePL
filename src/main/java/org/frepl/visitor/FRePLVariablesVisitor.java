package org.frepl.visitor;

import java.util.ArrayList;

public class FRePLVariablesVisitor extends FRePLBaseVisitor<Object> {

    private final FRePLVisitorImpl mainVisitor;
    public FRePLVariablesVisitor(FRePLVisitorImpl mainVisitor){
        this.mainVisitor = mainVisitor;
    }
    @Override
    public Object visitDeclarationWithoutValue(FRePLParser.DeclarationWithoutValueContext ctx) {
        switch(ctx.TYPE().getText()){
            case "int" -> mainVisitor.SymbolTable.currentTable.put(ctx.IDENTIFIER().getText(),0);
            case "float" -> mainVisitor.SymbolTable.currentTable.put(ctx.IDENTIFIER().getText(),0.0);
            case "boolean" -> mainVisitor.SymbolTable.currentTable.put(ctx.IDENTIFIER().getText(),false);
            case "string" -> mainVisitor.SymbolTable.currentTable.put(ctx.IDENTIFIER().getText(),"");
            case "char" -> mainVisitor.SymbolTable.currentTable.put(ctx.IDENTIFIER().getText(),' ');
            case "int[]" -> mainVisitor.SymbolTable.currentTable.put(ctx.IDENTIFIER().getText(),new ArrayList<Integer>());
            case "float[]" -> mainVisitor.SymbolTable.currentTable.put(ctx.IDENTIFIER().getText(),new ArrayList<Float>());
            case "boolean[]" -> mainVisitor.SymbolTable.currentTable.put(ctx.IDENTIFIER().getText(),new ArrayList<Boolean>());
            case "string[]" -> mainVisitor.SymbolTable.currentTable.put(ctx.IDENTIFIER().getText(),new ArrayList<String>());
            case "char[]" -> mainVisitor.SymbolTable.currentTable.put(ctx.IDENTIFIER().getText(),new ArrayList<Character>());
        }
        return null;
    }

    @Override
    public Object visitDeclarationWithValue(FRePLParser.DeclarationWithValueContext ctx) {
        if(mainVisitor.SymbolTable.currentTable.containsKey(ctx.IDENTIFIER().getText())){
            throw new IllegalArgumentException("Variable name already taken");
        }
        Object value = mainVisitor.visit(ctx.expression());
        DeclareWithValue(ctx.IDENTIFIER().getText(),value,ctx.TYPE().getText());
        return null;
    }
    private void DeclareWithValue(String key,Object value, String type){
        switch(type){
            case "string" -> {
                mainVisitor.SymbolTable.currentTable.put(key,value.toString());
            }
            case "int" -> {
                if(value instanceof Integer) mainVisitor.SymbolTable.currentTable.put(key,value);
                else throw new IllegalArgumentException("Type mismatch");
            }
            case "float" -> {
                if(value instanceof Float) mainVisitor.SymbolTable.currentTable.put(key,value);
                else throw new IllegalArgumentException("Type mismatch");
            }
            case "bool" -> {
                if(value instanceof Boolean) mainVisitor.SymbolTable.currentTable.put(key,value);
                else throw new IllegalArgumentException("Type mismatch");
            }
            case "char" -> {
                if(value instanceof String && ((String) value).length() == 1) mainVisitor.SymbolTable.currentTable.put(key,value);
                else{
                    System.out.println(value);
                    throw new IllegalArgumentException("Type mismatch");
                }
            }
            case "string[]" -> {
                if(value instanceof ArrayList<?>){
                    for(var element : (ArrayList<?>)value){
                        if(!(element instanceof String)){
                            throw new IllegalArgumentException("Type mismatch. Expected string, got:" + value.getClass().componentType()+"");
                        }
                    }
                    mainVisitor.SymbolTable.currentTable.put(key,value);
                }
                else throw new IllegalArgumentException("Type mismatch");
            }
            case "int[]" -> {
                if(value instanceof ArrayList<?>){
                    for(var element : (ArrayList<?>)value){
                        if(!(element instanceof Integer)){
                            throw new IllegalArgumentException("Type mismatch. Expected int, got:" + value.getClass().componentType()+"");
                        }
                    }
                    mainVisitor.SymbolTable.currentTable.put(key,value);
                }
                else throw new IllegalArgumentException("Type mismatch");
            }
            case "float[]" -> {
                if(value instanceof ArrayList<?>){
                    for(var element : (ArrayList<?>)value){
                        if(!(element instanceof Float)){
                            throw new IllegalArgumentException("Type mismatch. Expected float, got:" + value.getClass().componentType()+"");
                        }
                    }
                    mainVisitor.SymbolTable.currentTable.put(key,value);
                }
                else throw new IllegalArgumentException("Type mismatch. Expected float, got:" + value.getClass().componentType()+"[]");
            }
            case "bool[]" -> {
                if(value instanceof ArrayList<?>){
                    for(var element : (ArrayList<?>)value){
                        if(!(element instanceof Boolean)){
                            throw new IllegalArgumentException("Type mismatch. Expected bool, got:" + value.getClass().componentType()+"");
                        }
                    }
                    mainVisitor.SymbolTable.currentTable.put(key,value);
                }
                else throw new IllegalArgumentException("Type mismatch");
            }
            case "char[]" -> {
                if(value instanceof ArrayList<?>){
                    for(var element : (ArrayList<?>)value){
                        if(!(element instanceof Character)){
                            throw new IllegalArgumentException("Type mismatch. Expected char, got:" + value.getClass().componentType()+"");
                        }
                    }
                    mainVisitor.SymbolTable.currentTable.put(key,value);
                }
                else throw new IllegalArgumentException("Type mismatch");
            }
        }
    }

    @Override
    public Object visitDeclarationImplicitType(FRePLParser.DeclarationImplicitTypeContext ctx) {
        if(mainVisitor.SymbolTable.currentTable.containsKey(ctx.IDENTIFIER().getText())){
            throw new IllegalArgumentException("Variable name already taken");
        }
        Object data = ctx.expression().getText();
        mainVisitor.SymbolTable.currentTable.put(ctx.IDENTIFIER().getText(),mainVisitor.visit(ctx.expression()));
        return null;
    }

    @Override
    public Object visitAssignment(FRePLParser.AssignmentContext ctx) {
        Object currentValue = mainVisitor.SymbolTable.currentTable.get(ctx.IDENTIFIER().getText());
        Object nextValue = mainVisitor.visit(ctx.expression());

        if(currentValue.getClass() != nextValue.getClass()){
            throw new IllegalArgumentException("Attempted variable `" + ctx.IDENTIFIER().getText() + "` assignment to a different type");
        }
        mainVisitor.SymbolTable.currentTable.put(ctx.IDENTIFIER().getText(),nextValue);
        return null;
    }


}
