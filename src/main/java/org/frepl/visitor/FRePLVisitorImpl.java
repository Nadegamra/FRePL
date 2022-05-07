package org.frepl.visitor;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FRePLVisitorImpl extends FRePLBaseVisitor<Object> {

    private final StringBuilder OUTPUT = new StringBuilder();
    private final FRePLSymbolsTable SymbolTable = new FRePLSymbolsTable();

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
            return reader.readLine();
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
    public Object visitConditionalStatement(FRePLParser.ConditionalStatementContext ctx) {
        visit(ctx.ifStatement());
        for (var elseIf : ctx.elseIfStatement()) {
            visit(elseIf);
        }
        if(ctx.elseStatement() != null){
            visit(ctx.elseStatement());
        }
        return null;
    }

    @Override
    public Object visitIfStatement(FRePLParser.IfStatementContext ctx) {
        Object value = visit(ctx.expression());
        if(value.getClass() == Boolean.class){
            if((Boolean)value){
                visit(ctx.block());
            }
        }
        return null;
    }

    @Override
    public Object visitElseIfStatement(FRePLParser.ElseIfStatementContext ctx) {
        Object value = visit(ctx.expression());
        if(value.getClass() == Boolean.class){
            if((Boolean)value){
                visit(ctx.block());
            }
        }
        return null;
    }

    @Override
    public Object visitElseStatement(FRePLParser.ElseStatementContext ctx) {
        return visit(ctx.block());
    }

    @Override
    public Object visitBlock(FRePLParser.BlockContext ctx) {
        SymbolTable.EnterBlock(true);
        for (var statement: ctx.statement()) {
            visit(statement);
        }
        SymbolTable.ExitBlock(true);
        return null;
    }

    @Override
    public Object visitDeclarationWithoutValue(FRePLParser.DeclarationWithoutValueContext ctx) {
        switch(ctx.TYPE().getText()){
            case "int" -> SymbolTable.currentTable.put(ctx.IDENTIFIER().getText(),0);
            case "float" -> SymbolTable.currentTable.put(ctx.IDENTIFIER().getText(),0.0);
            case "boolean" -> SymbolTable.currentTable.put(ctx.IDENTIFIER().getText(),false);
            case "string" -> SymbolTable.currentTable.put(ctx.IDENTIFIER().getText(),"");
            case "char" -> SymbolTable.currentTable.put(ctx.IDENTIFIER().getText(),' ');
        }
        return null;
    }

    @Override
    public Object visitDeclarationWithValue(FRePLParser.DeclarationWithValueContext ctx) {
        if(SymbolTable.currentTable.containsKey(ctx.IDENTIFIER().getText())){
            throw new IllegalArgumentException("Variable name already taken");
        }
        Object value = visit(ctx.expression());
        DeclareWithValue(ctx.IDENTIFIER().getText(),value,ctx.TYPE().getText());
        return null;
    }
    private void DeclareWithValue(String key,Object value, String type){
        switch(type){
            case "string" -> {
                SymbolTable.currentTable.put(key,value.toString());
            }
            case "int" -> {
                if(value.getClass() == Integer.class) SymbolTable.currentTable.put(key,value);
                else throw new IllegalArgumentException("Type mismatch");
            }
            case "float" -> {
                if(value.getClass() == Float.class) SymbolTable.currentTable.put(key,value);
                else throw new IllegalArgumentException("Type mismatch");
            }
            case "boolean" -> {
                if(value.getClass() == Boolean.class) SymbolTable.currentTable.put(key,value);
                else throw new IllegalArgumentException("Type mismatch");
            }
            case "char" -> {
                if(value.getClass() == String.class && ((String) value).length() == 1) SymbolTable.currentTable.put(key,value);
                else{
                    System.out.println(value);
                    throw new IllegalArgumentException("Type mismatch");
                }
            }
        }
    }

    @Override
    public Object visitDeclarationImplicitType(FRePLParser.DeclarationImplicitTypeContext ctx) {
        if(SymbolTable.currentTable.containsKey(ctx.IDENTIFIER().getText())){
            throw new IllegalArgumentException("Variable name already taken");
        }
        Object data = ctx.expression().getText();
        SymbolTable.currentTable.put(ctx.IDENTIFIER().getText(),visit(ctx.expression()));
        return null;
    }

    @Override
    public Object visitAssignment(FRePLParser.AssignmentContext ctx) {
        Object currentValue = SymbolTable.currentTable.get(ctx.IDENTIFIER().getText());
        Object nextValue = visit(ctx.expression());

        if(currentValue.getClass() != nextValue.getClass()){
            throw new IllegalArgumentException("Attempted variable `" + ctx.IDENTIFIER().getText() + "` assignment to a different type");
        }
        SymbolTable.currentTable.put(ctx.IDENTIFIER().getText(),nextValue);
        return null;
    }

    @Override
    public Object visitIdentifierExpression(FRePLParser.IdentifierExpressionContext ctx) {
        return SymbolTable.currentTable.get(ctx.IDENTIFIER().getText());
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
    public Object visitBinaryPowerExpression(FRePLParser.BinaryPowerExpressionContext ctx) {
        Object val1 = visit(ctx.expression(0));
        Object val2 = visit(ctx.expression(1));

        if(val1.getClass() == Integer.class && val2.getClass() == Integer.class){
            float temp1 = (int)val1;
            float temp2 = (int)val2;
            return Math.pow(temp1,temp2);
        }else if (val1.getClass() == Float.class && val2.getClass() == Float.class){
            return Math.pow((Float)val1,(Float)val2);

        }else if (val1.getClass() == Float.class && val2.getClass() == Integer.class){
            float temp2 = (int)val2;
            return Math.pow((Float)val1,temp2);

        }
        else if (val2.getClass() == Float.class && val1.getClass() == Integer.class){
            float temp1 = (int)val1;
            return Math.pow(temp1,(Float)val2);
        }
        return null;
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
    public Object visitBinaryComparisonExpression(FRePLParser.BinaryComparisonExpressionContext ctx) {//TODO: Make comparisons more loose
        Object val1 = visit(ctx.expression(0));
        Object val2 = visit(ctx.expression(1));

        if(val1.getClass() == val2.getClass()){
            return switch (ctx.binCompOp().getText()){
                case "!=" -> !(val1).equals(val2);
                case "==" -> (val1).equals(val2);
                default -> val1.getClass() == Integer.class ? switch (ctx.binCompOp().getText()) {
                    case ">" -> (Integer) val1 > (Integer) val2;
                    case ">=" -> (Integer) val1 >= (Integer) val2;
                    case "<" -> (Integer) val1 < (Integer) val2;
                    case "<=" -> (Integer) val1 <= (Integer) val2;
                    case "<=>" -> ((Integer)val1).compareTo((Integer)val2);
                    default -> null;
                } : val1.getClass() == Float.class ? switch (ctx.binCompOp().getText()){
                        case ">" -> (Float)val1 > (Float)val2;
                        case ">=" -> (Float)val1 >= (Float)val2;
                        case "<" -> (Float)val1 < (Float)val2;
                        case "<=" -> (Float)val1 <= (Float)val2;
                        case "<=>" -> ((Float)val1).compareTo((Float)val2);
                        default -> null;
                } : val1.getClass() == String.class ? switch (ctx.binCompOp().getText()){
                    case ">" -> ((String)val1).compareTo((String)val2) > 0;
                    case ">=" -> ((String)val1).compareTo((String)val2) >= 0;
                    case "<" -> ((String)val1).compareTo((String)val2) < 0;
                    case "<=" -> ((String)val1).compareTo((String)val2) <= 0;
                    case "<=>" -> ((String)val1).compareTo((String)val2);
                    default -> null;
                } : val1.getClass() == Character.class ? switch (ctx.binCompOp().getText()){
                    case ">" -> ((Character)val1).compareTo((Character)val2) > 0;
                    case ">=" -> ((Character)val1).compareTo((Character)val2) >= 0;
                    case "<" -> ((Character)val1).compareTo((Character)val2) < 0;
                    case "<=" -> ((Character)val1).compareTo((Character)val2) <= 0;
                    case "<=>" -> ((Character)val1).compareTo((Character)val2);
                    default -> null;
                } : null;
            };
        } else {
            return null;
        }
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

    @Override
    public Object visitFileVar(FRePLParser.FileVarContext ctx) {
        String filePath = ctx.STRING(0).getText();
        filePath = filePath.substring(1,filePath.length()-1);
        int lineNum = Integer.parseInt(ctx.INT(0).getText());
        int fieldNum = Integer.parseInt(ctx.INT(1).getText());
        String delimiter = ctx.STRING(1).getText();
        delimiter = delimiter.substring(1,delimiter.length()-1);

        String fullPath = System.getProperty("user.dir") + "\\" + filePath;
        try(BufferedReader reader = new BufferedReader(new FileReader(filePath))){
            while(lineNum-- > 1){
                reader.readLine();
            }
            String line = reader.readLine();
            String data = line.split(delimiter,-1)[fieldNum-1];
            try {
                return Integer.parseInt(data);
            }catch (NumberFormatException ex1){
                try{
                    return Float.parseFloat(data);
                }catch (NumberFormatException ex2){
                    if(data.toLowerCase(Locale.ROOT).equals("true") || data.toLowerCase(Locale.ROOT).equals("false")){
                        return Boolean.parseBoolean(data);
                    }else{
                        if(data.length() == 1){
                            return data.charAt(0);
                        }else{
                            return data;
                        }
                    }
                }
            }
        }catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public Object visitFileLine(FRePLParser.FileLineContext ctx) {
        String filePath = ctx.STRING().getText();
        filePath = filePath.substring(1,filePath.length()-1);
        int lineNum = Integer.parseInt(ctx.INT().getText());

        String fullPath = System.getProperty("user.dir") + "\\" + filePath;
        try(BufferedReader reader = new BufferedReader(new FileReader(filePath))){
            while(lineNum-- > 1){
                reader.readLine();
            }
            return reader.readLine();
        }catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public Object visitFileText(FRePLParser.FileTextContext ctx) {
        String filePath = ctx.STRING().getText();
        filePath = filePath.substring(1,filePath.length()-1);
        String fullPath = System.getProperty("user.dir") + "\\" + filePath;
        try {
            byte[] bytes = Files.readAllBytes(Paths.get(fullPath));
            return new String(bytes, StandardCharsets.UTF_8);
        }catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public Object visitSaveFunctionCall(FRePLParser.SaveFunctionCallContext ctx) {
        String filePath = ctx.STRING().getText();
        filePath = filePath.substring(1,filePath.length()-1);
        try(PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(filePath)))) {
            for (var key:SymbolTable.currentTable.keySet()) {
                Object value = SymbolTable.currentTable.get(key);
                if(value.getClass() == String.class){
                    String val = value.toString();
                    val = val.replaceAll(System.lineSeparator(),"\\\\r\\\\n");
                    writer.println("\"" + key + "\": \"" + val + "\";");
                }else{
                    writer.println("\"" + key + "\": \"" + value + "\";");
                }

            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public Object visitLoadFunctionCall(FRePLParser.LoadFunctionCallContext ctx) {//Currently overrides all current variables with same name. Might change this later
        String filePath = ctx.STRING().getText();
        filePath = filePath.substring(1,filePath.length()-1);
        try{
            String line;
            byte[] bytes = Files.readAllBytes(Paths.get(filePath));
            String text = new String(bytes, StandardCharsets.UTF_8);
            List<String> matches = new ArrayList<>();
            Matcher m = Pattern.compile("\".+?\": \".+?\";")
                    .matcher(text);
            while (m.find()){
                String match = m.group();
                String[] data = match.split("\": \"");
                String varName = data[0].substring(1);
                String varData = data[1].substring(0,data[1].length()-2);
                try {
                    SymbolTable.currentTable.put(varName, Integer.parseInt(varData));
                }catch (NumberFormatException ex1){
                    try{
                        SymbolTable.currentTable.put(varName, Float.parseFloat(varData));
                    }catch (NumberFormatException ex2){
                        if(varData.toLowerCase(Locale.ROOT).equals("true") || varData.toLowerCase(Locale.ROOT).equals("false")){
                            SymbolTable.currentTable.put(varName, Boolean.parseBoolean(varData));
                        }else{
                            if(varData.length() == 1){
                                SymbolTable.currentTable.put(varName, varData.charAt(0));
                            }else{
                                SymbolTable.currentTable.put(varName, varData.replaceAll("\\\\r\\\\n",System.lineSeparator()));
                            }
                        }
                    }
                }
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return null;
    }
}
