package org.frepl.visitor;

import org.antlr.v4.runtime.tree.RuleNode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FRePLFunctionsVisitor extends FRePLBaseVisitor<Object> {
    private final FRePLExpressionVisitor expressionVisitor;
    private final FRePLVisitorImpl mainVisitor;
    private final Map<String,FRePLFunction> functions = new HashMap<String,FRePLFunction>();

    public FRePLFunctionsVisitor(FRePLVisitorImpl mainVisitor, FRePLExpressionVisitor expressionVisitor){
        this.mainVisitor = mainVisitor;
        this.expressionVisitor = expressionVisitor;
    }
    @Override
    public Object visitReturnStatement(FRePLParser.ReturnStatementContext ctx) {
        Object value = mainVisitor.visit(ctx.expression());
        return new FRePLReturnObject(value);
    }

    @Override
    protected boolean shouldVisitNextChild(RuleNode node, Object currentResult) {
        if(currentResult instanceof FRePLReturnObject){
            return false;
        }
        return super.shouldVisitNextChild(node, currentResult);
    }

    @Override
    public Object visitFunctionDeclaration(FRePLParser.FunctionDeclarationContext ctx) {
        if(functions.containsKey(ctx.IDENTIFIER().getText())){
            throw new IllegalStateException("The function is already declared");
            //TODO: function overloading
        }
        functions.put(ctx.IDENTIFIER().getText(), new FRePLFunction(ctx));
        return null;
    }

    @Override
    public Object visitFunctionCall(FRePLParser.FunctionCallContext ctx) {
        if(!functions.containsKey(ctx.IDENTIFIER().getText())){
            throw new IllegalCallerException("Function not found");
        }
        FRePLFunction function = functions.get(ctx.IDENTIFIER().getText());
        if(!function.hasArguments()){
            mainVisitor.SymbolTable.EnterBlock(false);
        }
        else{
            List<FRePLParser.FunctionParameterContext> parameters = function.getParameters();
            List<FRePLParser.ExpressionContext> expressions = ctx.functionArgumentList().expression();
            if(!function.doArgumentsCountMatch(expressions)){
                throw new IllegalCallerException("Function argument count does not match");
            }
            Map<String,Object> newTable = new HashMap<>();
            for (int i=0;i<expressions.size();i++) {
                Object result = mainVisitor.visit(expressions.get(i));
                FRePLParser.FunctionParameterContext parameter = parameters.get(i);
                String expressionType = expressionVisitor.getExpressionType(result);
                if(!expressionType.equals(parameter.TYPE().getText())){
                    throw new IllegalCallerException("Argument type is invalid. Expected: " + parameter.TYPE().getText() +", got: " + expressionType);
                }
                newTable.put(parameter.IDENTIFIER().getText(),result);
            }
            mainVisitor.SymbolTable.EnterBlock(false);
            mainVisitor.SymbolTable.currentTable = newTable;
        }
        Object result = visitFunctionBody(function.getFunctionBody());
        if(function.getReturnType().equals("void")){
            return null;
        }
        String functionReturnType = expressionVisitor.getExpressionType(result);
        if(!function.getReturnType().equals(functionReturnType)){
            throw new IllegalCallerException("Function returned value type is incorrect. Expected: " + function.getReturnType() +", got: " + functionReturnType);
        }
        mainVisitor.SymbolTable.ExitBlock(false);

        return result;
    }
    @Override
    public Object visitFunctionBody(FRePLParser.FunctionBodyContext ctx) {
        for (var statement: ctx.statement()) {
            Object result = mainVisitor.visit(statement);
            if(result instanceof FRePLReturnObject){
                return ((FRePLReturnObject) result).getValue();
            }
        }
        return null;
    }


}
