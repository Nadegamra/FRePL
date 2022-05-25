package org.frepl.visitor;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class FRePLConsoleIOVisitor extends FRePLBaseVisitor<Object> {
    private final FRePLExpressionVisitor expVisitor;

    public FRePLConsoleIOVisitor(FRePLExpressionVisitor expressionVisitor){
        expVisitor = expressionVisitor;
    }
    @Override
    public Object visitPrintFunctionCall(FRePLParser.PrintFunctionCallContext ctx) {
        String textToPrint = expVisitor.visit(ctx.expression()).toString();
        System.out.println(textToPrint);
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
}
