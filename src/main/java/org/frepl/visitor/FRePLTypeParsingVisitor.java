package org.frepl.visitor;

public class FRePLTypeParsingVisitor extends FRePLBaseVisitor<Object> {
    private final FRePLVisitorImpl mainVisitor;
    public FRePLTypeParsingVisitor(FRePLVisitorImpl mainVisitor){
        this.mainVisitor = mainVisitor;
    }
    @Override
    public Object visitParseInt(FRePLParser.ParseIntContext ctx) {
        Object value = mainVisitor.visit(ctx.expression());
        if(value instanceof Integer){
            return ((Number)value).intValue();
        }
        if(value instanceof Float){
            return ((Number)value).intValue();
        }
        if(value instanceof Boolean){
            return ((Boolean)value) ? 1 : 0;
        }
        if(value instanceof String && ((String) value).length() == 1){
            return (int)((String) value).charAt(0);
        }
        if(value instanceof String) {
            return Integer.parseInt((String)value);
        }
        return null;
    }

    @Override
    public Object visitParseFloat(FRePLParser.ParseFloatContext ctx) {
        Object value = mainVisitor.visit(ctx.expression());
        if(value instanceof Integer){
            return ((Number)value).floatValue();
        }
        if(value instanceof Float){
            return ((Number)value).floatValue();
        }
        if(value instanceof Boolean){
            return ((Boolean)value) ? 1.0 : 0.0;
        }
        if(value instanceof String && ((String) value).length() == 1){
            return (float)((String) value).charAt(0);
        }
        if(value instanceof String) {
            return Float.parseFloat((String)value);
        }
        return null;
    }

    @Override
    public Object visitParseBool(FRePLParser.ParseBoolContext ctx) {
        Object value = mainVisitor.visit(ctx.expression());
        if(value instanceof Integer){
            return (Integer)value != 0;
        }
        if(value instanceof Float){
            return (Float)value != 0;
        }
        if(value instanceof Boolean){
            return (Boolean)value;
        }
        if(value instanceof String){
            return !((String) value).equals("");
        }
        return null;
    }

    @Override
    public Object visitParseChar(FRePLParser.ParseCharContext ctx) {
        Object value = mainVisitor.visit(ctx.expression());
        if(value instanceof Integer){
            return (char)value;
        }
        if(value instanceof Float){
            return (char)((int)value);
        }
        if(value instanceof Boolean){
            return (char)(((Boolean)value ? 1 : 0) + '0');
        }
        if(value instanceof String){
            return ((String) value).charAt(0);
        }
        return null;
    }

    @Override
    public Object visitParseString(FRePLParser.ParseStringContext ctx) {
        Object value = mainVisitor.visit(ctx.expression());
        return value.toString();
    }
}
