package org.frepl.visitor;

public class FRePLFunction {
    String returnType;
    FRePLParser.FunctionBodyContext functionBody;
    String functionName;
    public FRePLFunction(FRePLParser.FunctionDeclarationContext ctx){
        returnType = ctx.TYPE().getText();
        functionName = ctx.IDENTIFIER().getText();
        functionBody = ctx.functionBody();
    }

}
