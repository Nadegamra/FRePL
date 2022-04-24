package org.frepl.visitor;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class FRePLVisitorImpl extends FRePLBaseVisitor<Object> {

    private final StringBuilder OUTPUT = new StringBuilder();
    private final Map<String,Object> SymbolTable = new HashMap<>();

    @Override
    public Object visitProgram(FRePLParser.ProgramContext ctx) {
        super.visitProgram(ctx);
        return OUTPUT.toString();
    }

    @Override
    public Object visitPrintFunctionCall(FRePLParser.PrintFunctionCallContext ctx) {
        String textToPrint = visit(ctx.expression()).toString();
        System.out.println(textToPrint);
        OUTPUT.append(textToPrint).append("\n");
        return null;
    }

    @Override
    public Object visitPrintNewLine(FRePLParser.PrintNewLineContext ctx) {
        System.out.println();
        return null;
    }

    @Override
    public Object visitReadFunctionCall(FRePLParser.ReadFunctionCallContext ctx) {
        BufferedReader reader  = new BufferedReader(
                new InputStreamReader(System.in));
        try{
            String line = reader.readLine();
            return line;
        }catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public Object visitFunctionReturnExpression(FRePLParser.FunctionReturnExpressionContext ctx) {
        if(Objects.equals(ctx.systemFunction().getText(), "READ()")){
            return visit(ctx.systemFunction());
        }
        return null;
    }

    @Override
    public Object visitDeclarationWithoutValue(FRePLParser.DeclarationWithoutValueContext ctx) {
        switch(ctx.TYPE().getText()){
            case "int" -> SymbolTable.put(ctx.IDENTIFIER().getText(),0);
            case "float" -> SymbolTable.put(ctx.IDENTIFIER().getText(),0.0);
            case "boolean" -> SymbolTable.put(ctx.IDENTIFIER().getText(),false);
            case "string" -> SymbolTable.put(ctx.IDENTIFIER().getText(),"");
            case "char" -> SymbolTable.put(ctx.IDENTIFIER().getText(),' ');
        }
        return null;
    }

    @Override
    public Object visitDeclarationWithValue(FRePLParser.DeclarationWithValueContext ctx) {
        if(SymbolTable.containsKey(ctx.IDENTIFIER().getText())){
            throw new IllegalArgumentException("Variable name already taken");
        }
        Object value = visit(ctx.expression());
        switch(ctx.TYPE().getText()){
            case "int" -> {
                if(value.getClass() == Integer.class) SymbolTable.put(ctx.IDENTIFIER().getText(),value);
                else throw new IllegalArgumentException("Type mismatch");
            }
            case "float" -> {
                if(value.getClass() == Float.class) SymbolTable.put(ctx.IDENTIFIER().getText(),value);
                else throw new IllegalArgumentException("Type mismatch");
            }
            case "boolean" -> {
                if(value.getClass() == Boolean.class) SymbolTable.put(ctx.IDENTIFIER().getText(),value);
                else throw new IllegalArgumentException("Type mismatch");
            }
            case "string" -> {
                if(value.getClass() == String.class) SymbolTable.put(ctx.IDENTIFIER().getText(),value);
                else throw new IllegalArgumentException("Type mismatch");
            }
            case "char" -> {
                if(value.getClass() == String.class && ((String) value).length() == 1) SymbolTable.put(ctx.IDENTIFIER().getText(),value);
                else{
                    System.out.println(value);
                    throw new IllegalArgumentException("Type mismatch");
                }
            }
        }
        return null;
    }

    @Override
    public Object visitDeclarationImplicitType(FRePLParser.DeclarationImplicitTypeContext ctx) {
        if(SymbolTable.containsKey(ctx.IDENTIFIER().getText())){
            throw new IllegalArgumentException("Variable name already taken");
        }
        Object data = ctx.expression().getText();
        SymbolTable.put(ctx.IDENTIFIER().getText(),visit(ctx.expression()));
        return null;
    }

    @Override
    public Object visitAssignment(FRePLParser.AssignmentContext ctx) {
        Object currentValue = SymbolTable.get(ctx.IDENTIFIER().getText());
        Object nextValue = visit(ctx.expression());

        if(currentValue.getClass() != nextValue.getClass()){
            throw new IllegalArgumentException("Attempted variable `" + ctx.IDENTIFIER().getText() + "` assignment to a different type");
        }
        SymbolTable.put(ctx.IDENTIFIER().getText(),nextValue);
        return null;
    }

    @Override
    public Object visitIdentifierExpression(FRePLParser.IdentifierExpressionContext ctx) {
        return SymbolTable.get(ctx.IDENTIFIER().getText());
    }

    @Override
    public Object visitBinaryConcatExpression(FRePLParser.BinaryConcatExpressionContext ctx) {
        Object val1 = visit(ctx.expression(0));
        Object val2 = visit(ctx.expression(1));

        return (String)val1+val2;
    }

    @Override
    public Object visitBinaryBooleanExpression(FRePLParser.BinaryBooleanExpressionContext ctx) {
        Object val1 = visit(ctx.expression(0));
        Object val2 = visit(ctx.expression(1));
        if(val1.getClass() == Boolean.class && val2.getClass() == Boolean.class) {
            return switch (ctx.binBoolOp().getText()) {
                case "||" -> (boolean) val1 || (boolean) val2;
                case "&&" -> (boolean) val1 && (boolean) val2;
                default -> null;
            };
        }
        return null;
    }

    @Override
    public Object visitParenthesisExpression(FRePLParser.ParenthesisExpressionContext ctx) {
        return visit(ctx.expression());
    }

    @Override
    public Object visitUnaryNegationExpression(FRePLParser.UnaryNegationExpressionContext ctx) {
        Object val1 = visit(ctx.expression());
        Boolean value = (Boolean)val1;
        if(value == null){
            throw new IllegalArgumentException("! operator can only be used with `bool` data type");
        }
        return !value;
    }

    @Override
    public Object visitBinaryMultExpression(FRePLParser.BinaryMultExpressionContext ctx) {
        Object val1 = visit(ctx.expression(0));
        Object val2 = visit(ctx.expression(1));

        if(val1.getClass() == Integer.class && val2.getClass() == Integer.class){
            return switch (ctx.binMultOp().getText()){
                case "*" -> (Integer)val1 * (Integer)val2;
                case "/" -> (Integer)val1 / (Integer)val2;
                case "%" -> (Integer)val1 % (Integer)val2;
                default -> null;
            };
        }else if (val1.getClass() == Float.class && val2.getClass() == Float.class){
            return switch (ctx.binMultOp().getText()){
                case "*" -> (Float)val1 * (Float)val2;
                case "/" -> (Float)val1 / (Float)val2;
                case "%" -> (Float)val1 % (Float)val2;
                default -> null;
            };
        }else if (val1.getClass() == Float.class && val2.getClass() == Integer.class){
            return switch (ctx.binMultOp().getText()){
                case "*" -> (Float)val1 * (Integer)val2;
                case "/" -> (Float)val1 / (Integer)val2;
                case "%" -> (Float)val1 % (Integer)val2;
                default -> null;
            };
        }
        else if (val2.getClass() == Float.class && val1.getClass() == Integer.class){
            return switch (ctx.binMultOp().getText()){
                case "*" -> (Integer)val1 * (Float)val2;
                case "/" -> (Integer)val1 / (Float)val2;
                case "%" -> (Integer)val1 % (Float)val2;
                default -> null;
            };
        }
        return null;
    }

    @Override
    public Object visitBinaryAddExpression(FRePLParser.BinaryAddExpressionContext ctx) {
        Object val1 = visit(ctx.expression(0));
        Object val2 = visit(ctx.expression(1));

        if(val1.getClass() == Integer.class && val2.getClass() == Integer.class){
            return switch (ctx.binAddOp().getText()){
                case "+" -> (Integer)val1 + (Integer)val2;
                case "-" -> (Integer)val1 - (Integer)val2;
                default -> null;
            };
        }else if (val1.getClass() == Float.class || val2.getClass() == Float.class){
            return switch (ctx.binAddOp().getText()){
                case "+" -> (Float)val1 + (Float)val2;
                case "-" -> (Float)val1 - (Float)val2;
                default -> null;
            };
        }
        return null;
    }

    @Override
    public Object visitConstant(FRePLParser.ConstantContext ctx) {
        if (ctx.INT() != null) {
            return Integer.parseInt(ctx.INT().getText());
        }
        if (ctx.FLOAT() != null) {
            return Float.parseFloat(ctx.FLOAT().getText());
        }
        if (ctx.BOOLEAN() != null) {
            return Boolean.parseBoolean(ctx.BOOLEAN().getText());
        }
        if (ctx.CHAR() != null) {
            String text = ctx.CHAR().getText();
            //TODO: Fix behaviour with special symbols for example \n
            if(text.charAt(0) != '\'' || text.charAt(text.length()-1) != '\''){
                throw new IllegalArgumentException("Invalid character");
            }
            return text.substring(1,text.length()-1);
        }
        if(ctx.STRING() != null) {
            String text = ctx.STRING().getText();
            return text.substring(1,text.length()-1);
        }
        return null;
    }






}
