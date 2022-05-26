package org.frepl.visitor;

public class FRePLBlockVisitor extends FRePLBaseVisitor<Object> {
    private final FRePLVisitorImpl mainVisitor;
    public FRePLBlockVisitor(FRePLVisitorImpl visitor){
        mainVisitor = visitor;
    }


    @Override
    public Object visitBlock(FRePLParser.BlockContext ctx) {
        mainVisitor.SymbolTable.EnterBlock(true);
        for (var statement: ctx.statement()) {
            Object result = mainVisitor.visit(statement);
            if(result instanceof FRePLReturnObject){
                return result;
            }
        }
        mainVisitor.SymbolTable.ExitBlock(true);
        return null;
    }
}
