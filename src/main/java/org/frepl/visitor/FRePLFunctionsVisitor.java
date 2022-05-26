package org.frepl.visitor;

import org.antlr.v4.runtime.tree.RuleNode;

import java.util.HashMap;
import java.util.Map;

public class FRePLFunctionsVisitor extends FRePLBaseVisitor<Object> {
    private final FRePLExpressionVisitor expressionVisitor;
    private final Map<String,FRePLFunction> functions = new HashMap<String,FRePLFunction>();
    public FRePLFunctionsVisitor(FRePLExpressionVisitor expressionVisitor){
        this.expressionVisitor = expressionVisitor;
    }
    @Override
    public Object visitReturnStatement(FRePLParser.ReturnStatementContext ctx) {
        Object value = expressionVisitor.visit(ctx.expression());
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
        return super.visitFunctionDeclaration(ctx);
    }

    @Override
    public Object visitFunctionBody(FRePLParser.FunctionBodyContext ctx) {
        return super.visitFunctionBody(ctx);
    }

    @Override
    public Object visitFunctionCall(FRePLParser.FunctionCallContext ctx) {
        return super.visitFunctionCall(ctx);
    }
}
