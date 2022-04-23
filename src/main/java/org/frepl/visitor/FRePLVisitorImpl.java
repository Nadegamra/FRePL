package org.frepl.visitor;

public class FRePLVisitorImpl extends FRePLBaseVisitor<Object> {

    private final StringBuilder OUTPUT = new StringBuilder();

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
            return text.substring(1,text.length()-1);
        }
        if(ctx.STRING() != null) {
            String text = ctx.STRING().getText();
            return text.substring(1,text.length()-1);
        }
        return null;
    }

    @Override
    public Object visitExpression(FRePLParser.ExpressionContext ctx) {
        if(ctx.constant() != null) {
            return visitConstant(ctx.constant());
        }
        return null;
    }

    @Override
    public Object visitPrintFunctionCall(FRePLParser.PrintFunctionCallContext ctx) {
        String textToPrint = visitExpression(ctx.expression()).toString();
        System.out.println(textToPrint);
        OUTPUT.append(textToPrint).append("\n");
        return null;
    }

    @Override
    public Object visitProgram(FRePLParser.ProgramContext ctx) {
        super.visitProgram(ctx);
        return OUTPUT.toString();
    }
}
