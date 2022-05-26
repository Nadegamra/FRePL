package org.frepl.visitor;

public class FRePLConditionalsVisitor extends FRePLBaseVisitor<Object> {

    private final FRePLVisitorImpl mainVisitor;

    public FRePLConditionalsVisitor(FRePLVisitorImpl mainVisitor){
        this.mainVisitor = mainVisitor;
    }
    @Override
    public Object visitConditionalStatement(FRePLParser.ConditionalStatementContext ctx) {
        Object result = visit(ctx.ifStatement());
        if(result instanceof FRePLReturnObject){
            return result;
        }
        for (var elseIf : ctx.elseIfStatement()) {
            result = visit(elseIf);
            if(result instanceof FRePLReturnObject){
                return result;
            }
        }
        if(ctx.elseStatement() != null){
           result = visit(ctx.elseStatement());
           if(result instanceof FRePLReturnObject){
               return result;
           }
        }
        return null;
    }


    @Override
    public Object visitIfStatement(FRePLParser.IfStatementContext ctx) {
        Object value = mainVisitor.visit(ctx.expression());
        if(value.getClass() == Boolean.class){
            if((Boolean)value){
                Object result = mainVisitor.visit(ctx.block());
                if(result instanceof FRePLReturnObject){
                    mainVisitor.SymbolTable.ExitBlock(false);
                    return result;
                }
            }
        }
        return null;
    }

    @Override
    public Object visitElseIfStatement(FRePLParser.ElseIfStatementContext ctx) {
        Object value = mainVisitor.visit(ctx.expression());
        if(value.getClass() == Boolean.class){
            if((Boolean)value){
                Object result = mainVisitor.visit(ctx.block());
                if(result instanceof FRePLReturnObject){
                    mainVisitor.SymbolTable.ExitBlock(false);
                    return result;
                }
            }
        }
        return null;
    }

    @Override
    public Object visitElseStatement(FRePLParser.ElseStatementContext ctx) {
        return mainVisitor.visit(ctx.block());
    }

    @Override
    public Object visitWhileLoop(FRePLParser.WhileLoopContext ctx) {
        Object value = mainVisitor.visit(ctx.expression());
        if(value.getClass() == Boolean.class){
            while((Boolean)value){
                Object result = mainVisitor.visit(ctx.block());
                if(result instanceof FRePLReturnObject){
                    return result;
                }
                value = mainVisitor.visit(ctx.expression());
            }
        }
        return null;
    }

}
