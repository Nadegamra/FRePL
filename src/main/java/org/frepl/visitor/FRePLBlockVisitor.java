package org.frepl.visitor;

public class FRePLBlockVisitor extends FRePLBaseVisitor<Object> {
    private final FRePLVariablesVisitor varVisitor;
    private final FRePLVisitorImpl mainVisitor;
    public FRePLBlockVisitor(FRePLVariablesVisitor variablesVisitor,FRePLVisitorImpl visitor){
        varVisitor = variablesVisitor;
        mainVisitor = visitor;
    }
    @Override
    public Object visitBlock(FRePLParser.BlockContext ctx) {
        varVisitor.SymbolTable.EnterBlock(true);
        for (var statement: ctx.statement()) {
            mainVisitor.visit(statement);
        }
        varVisitor.SymbolTable.ExitBlock(true);
        return null;
    }
}
