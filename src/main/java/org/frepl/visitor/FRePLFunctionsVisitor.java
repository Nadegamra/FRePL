package org.frepl.visitor;

import org.antlr.v4.runtime.tree.RuleNode;

public class FRePLFunctionsVisitor extends FRePLBaseVisitor<Object> {
    private final FRePLExpressionVisitor expressionVisitor;
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
}
