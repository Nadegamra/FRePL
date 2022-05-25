package org.frepl.visitor;

public class FRePLConditionalsVisitor extends FRePLBaseVisitor<Object> {

    private final FRePLBlockVisitor blockVisitor;
    private final FRePLExpressionVisitor expressionVisitor;

    public FRePLConditionalsVisitor(FRePLBlockVisitor blockVisitor, FRePLExpressionVisitor expressionVisitor){
        this.blockVisitor = blockVisitor;
        this.expressionVisitor = expressionVisitor;
    }
    @Override
    public Object visitConditionalStatement(FRePLParser.ConditionalStatementContext ctx) {
        expressionVisitor.visit(ctx.ifStatement());
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
        Object value = expressionVisitor.visit(ctx.expression());
        if(value.getClass() == Boolean.class){
            if((Boolean)value){
                blockVisitor.visit(ctx.block());
            }
        }
        return null;
    }

    @Override
    public Object visitElseIfStatement(FRePLParser.ElseIfStatementContext ctx) {
        Object value = expressionVisitor.visit(ctx.expression());
        if(value.getClass() == Boolean.class){
            if((Boolean)value){
                blockVisitor.visit(ctx.block());
            }
        }
        return null;
    }

    @Override
    public Object visitElseStatement(FRePLParser.ElseStatementContext ctx) {
        return blockVisitor.visit(ctx.block());
    }

    @Override
    public Object visitWhileLoop(FRePLParser.WhileLoopContext ctx) {
        Object value = expressionVisitor.visit(ctx.expression());
        if(value.getClass() == Boolean.class){
            while((Boolean)value){
                blockVisitor.visit(ctx.block());
                value = expressionVisitor.visit(ctx.expression());
            }
        }
        return null;
    }

}
