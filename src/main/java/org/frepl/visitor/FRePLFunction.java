package org.frepl.visitor;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class FRePLFunction {
    String returnType;
    FRePLParser.FunctionBodyContext functionBody;
    String functionName;
    List<FRePLParser.FunctionParameterContext> parameters;
    public String getReturnType(){
        return returnType;
    }
    public FRePLParser.FunctionBodyContext getFunctionBody(){
        return functionBody;
    }
    public String getFunctionName(){
        return functionName;
    }
    public List<FRePLParser.FunctionParameterContext> getParameters(){
        return parameters;
    }
    public FRePLFunction(FRePLParser.FunctionDeclarationContext ctx){
        returnType = ctx.TYPE().getText();
        functionName = ctx.IDENTIFIER().getText();
        functionBody = ctx.functionBody();
        if(ctx.parametersList() != null) {
            parameters = ctx.parametersList().functionParameter();
        }else{
            parameters = new ArrayList<>();
        }
    }
    public boolean doArgumentsCountMatch(List<FRePLParser.ExpressionContext> expressions){
        return expressions.size() == parameters.size();
    }
    public boolean hasArguments(){
        return parameters.size() != 0;
    }

}
