package org.frepl.visitor;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class FRePLExpressionVisitor extends FRePLBaseVisitor<Object> {
    private final FRePLVisitorImpl mainVisitor;
    public FRePLExpressionVisitor(FRePLVisitorImpl mainVisitor){
        this.mainVisitor = mainVisitor;
    }

    @Override
    public Object visitFunctionCallExpression(FRePLParser.FunctionCallExpressionContext ctx) {
        return mainVisitor.visitFunctionCallExpression(ctx);
    }

    @Override
    public Object visitParseInt(FRePLParser.ParseIntContext ctx) {
        return mainVisitor.visitParseInt(ctx);
    }

    @Override
    public Object visitParseFloat(FRePLParser.ParseFloatContext ctx) {
        return mainVisitor.visitParseFloat(ctx);
    }

    @Override
    public Object visitParseBool(FRePLParser.ParseBoolContext ctx) {
        return mainVisitor.visitParseBool(ctx);
    }

    @Override
    public Object visitParseChar(FRePLParser.ParseCharContext ctx) {
        return mainVisitor.visitParseChar(ctx);
    }

    @Override
    public Object visitParseString(FRePLParser.ParseStringContext ctx) {
        return mainVisitor.visitParseString(ctx);
    }

    @Override
    public Object visitReadFunctionCall(FRePLParser.ReadFunctionCallContext ctx) {
        return mainVisitor.visitReadFunctionCall(ctx);
    }

    @Override
    public Object visitIdentifierExpression(FRePLParser.IdentifierExpressionContext ctx) {
        return mainVisitor.SymbolTable.currentTable.get(ctx.IDENTIFIER().getText());
    }

    @Override
    public Object visitBinaryConcatExpression(FRePLParser.BinaryConcatExpressionContext ctx) {
        Object val1 = mainVisitor.visit(ctx.expression(0));
        Object val2 = mainVisitor.visit(ctx.expression(1));

        return val1.toString()+val2.toString();
    }

    @Override
    public Object visitBinaryBooleanExpression(FRePLParser.BinaryBooleanExpressionContext ctx) {
        Object val1 = mainVisitor.visit(ctx.expression(0));
        Object val2 = mainVisitor.visit(ctx.expression(1));
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
    public Object visitFunctionCall(FRePLParser.FunctionCallContext ctx) {
        return super.visitFunctionCall(ctx);
    }

    @Override
    public Object visitParenthesisExpression(FRePLParser.ParenthesisExpressionContext ctx) {
        return mainVisitor.visit(ctx.expression());
    }

    @Override
    public Object visitUnaryNegationExpression(FRePLParser.UnaryNegationExpressionContext ctx) {
        Object val1 = mainVisitor.visit(ctx.expression());
        Boolean value = (Boolean)val1;
        if(value == null){
            throw new IllegalArgumentException("! operator can only be used with `bool` data type");
        }
        return !value;
    }

    @Override
    public Object visitBinaryPowerExpression(FRePLParser.BinaryPowerExpressionContext ctx) {
        Object val1 = mainVisitor.visit(ctx.expression(0));
        Object val2 = mainVisitor.visit(ctx.expression(1));

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
        Object val1 = mainVisitor.visit(ctx.expression(0));
        Object val2 = mainVisitor.visit(ctx.expression(1));

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
        Object val1 = mainVisitor.visit(ctx.expression(0));
        Object val2 = mainVisitor.visit(ctx.expression(1));

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
        Object val1 = mainVisitor.visit(ctx.expression(0));
        Object val2 = mainVisitor.visit(ctx.expression(1));

        if(val1.getClass() == val2.getClass()){
            switch (ctx.binCompOp().getText()){
                case "!=" : return !(val1).equals(val2);
                case "==" : return (val1).equals(val2);
                default: break;
            }
            if(val1 instanceof Integer){
                switch (ctx.binCompOp().getText()){
                    case ">" : return (Integer) val1 > (Integer) val2;
                    case ">=" : return (Integer) val1 >= (Integer) val2;
                    case "<" : return (Integer) val1 < (Integer) val2;
                    case "<=" : return (Integer) val1 <= (Integer) val2;
                    case "<=>" : return ((Integer)val1).compareTo((Integer)val2);
                    default: break;
                }
            }
            else if(val1 instanceof Float){
                switch (ctx.binCompOp().getText()){
                    case ">" : return (Float)val1 > (Float)val2;
                    case ">=" : return (Float)val1 >= (Float)val2;
                    case "<" : return (Float)val1 < (Float)val2;
                    case "<=" : return (Float)val1 <= (Float)val2;
                    case "<=>" : return ((Float)val1).compareTo((Float)val2);
                    default: break;
                }
            }
            else if(val1 instanceof String) {
                switch (ctx.binCompOp().getText()){
                    case ">" : return ((String)val1).compareTo((String)val2) > 0;
                    case ">=" : return ((String)val1).compareTo((String)val2) >= 0;
                    case "<" : return ((String)val1).compareTo((String)val2) < 0;
                    case "<=" : return ((String)val1).compareTo((String)val2) <= 0;
                    case "<=>" : return ((String)val1).compareTo((String)val2);
                    default : break;
                }

            }
            else if(val1 instanceof Character) {
                switch (ctx.binCompOp().getText()){
                    case ">" : return ((Character)val1).compareTo((Character)val2) > 0;
                    case ">=" : return ((Character)val1).compareTo((Character)val2) >= 0;
                    case "<" : return ((Character)val1).compareTo((Character)val2) < 0;
                    case "<=" : return ((Character)val1).compareTo((Character)val2) <= 0;
                    case "<=>" : return ((Character)val1).compareTo((Character)val2);
                    default : break;
                }
            }
            throw new IllegalArgumentException("Type not found");
        } else {
            throw new IllegalArgumentException("Compared types do not match");
        }
    }

    @Override
    public Object visitConstantArrayExpression(FRePLParser.ConstantArrayExpressionContext ctx) {
        return visitConstantArr(ctx.constantArr());
    }

    @Override
    public Object visitConstantArr(FRePLParser.ConstantArrContext ctx) {
        if(ctx.INT().size() != 0){
            List<Integer> list = new ArrayList<>();
            for (var val : ctx.INT()) {
                list.add(Integer.parseInt(val.getText()));
            }
            return list;
        }
        if(ctx.FLOAT().size() != 0){
            List<Float> list = new ArrayList<>();
            for (var val : ctx.FLOAT()) {
                list.add(Float.parseFloat(val.getText()));
            }
            return list;
        }
        if(ctx.BOOLEAN().size() != 0){
            List<Boolean> list = new ArrayList<>();
            for (var val : ctx.INT()) {
                list.add(Boolean.parseBoolean(val.getText()));
            }
            return list;
        }
        if(ctx.CHAR().size() != 0){
            List<String> list = new ArrayList<>();
            for (var val : ctx.CHAR()) {
                String text = val.getText();
                //TODO: Fix behaviour with special symbols for example \n
                if(text.charAt(0) != '\'' || text.charAt(text.length()-1) != '\''){
                    throw new IllegalArgumentException("Invalid character");
                }
                list.add(text.substring(1,text.length()-1));
            }
            return list;
        }
        if(ctx.STRING().size() != 0){
            List<String> list = new ArrayList<>();
            for (var val : ctx.STRING()) {
                String text = val.getText();
                list.add(text.substring(1,text.length()-1));
            }
            return list;
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
    @Override
    public Object visitFileExpression(FRePLParser.FileExpressionContext ctx) {
        return mainVisitor.visitFileExpression(ctx);
    }

    @Override
    public Object visitFileVar(FRePLParser.FileVarContext ctx) {
        return mainVisitor.visitFileVar(ctx);
    }

    @Override
    public Object visitFileLine(FRePLParser.FileLineContext ctx) {
        return mainVisitor.visitFileLine(ctx);
    }

    @Override
    public Object visitFileText(FRePLParser.FileTextContext ctx) {
        return mainVisitor.visitFileText(ctx);
    }

    public String getExpressionType(Object value){
        if(value instanceof Integer){
            return "int";
        }
        if(value instanceof Float){
            return "float";
        }
        if(value instanceof Boolean){
            return "bool";
        }
        if(value instanceof Character){
            return "char";
        }
        if(value instanceof String){
            return "string";
        }
        return null;
    }

}
