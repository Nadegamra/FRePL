package org.frepl.visitor;
import org.antlr.v4.runtime.tree.RuleNode;

import java.util.ArrayList;

public class FRePLVisitorImpl extends FRePLBaseVisitor<Object> {

    public FRePLSymbolsTable SymbolTable = new FRePLSymbolsTable();

    private final FRePLConsoleIOVisitor consoleIOVisitor = new FRePLConsoleIOVisitor(this);
    private final FRePLBlockVisitor blockVisitor = new FRePLBlockVisitor(this);
    private final FRePLVariablesVisitor varVisitor = new FRePLVariablesVisitor(this);
    private final FRePLFileIOVisitor fileIOVisitor = new FRePLFileIOVisitor(this);
    private final FRePLExpressionVisitor expVisitor = new FRePLExpressionVisitor(this);
    private final FRePLTypeParsingVisitor typeParser = new FRePLTypeParsingVisitor(this);
    private final FRePLConditionalsVisitor conditionalsVisitor = new FRePLConditionalsVisitor(this);
    private final FRePLFunctionsVisitor functionsVisitor = new FRePLFunctionsVisitor(this, expVisitor);
    private final FRePLArrayOperationsVisitor arraysVisitor = new FRePLArrayOperationsVisitor(this);

    @Override
    public Object visitArrayGetElement(FRePLParser.ArrayGetElementContext ctx) {
        return arraysVisitor.visitArrayGetElement(ctx);
    }
    @Override
    public Object visitArrayGetLength(FRePLParser.ArrayGetLengthContext ctx) {
        return arraysVisitor.visitArrayGetLength(ctx);
    }
    @Override
    public Object visitArrayAddElement(FRePLParser.ArrayAddElementContext ctx) {
        return arraysVisitor.visitArrayAddElement(ctx);
    }
    @Override
    public Object visitArrayRemoveAtIndex(FRePLParser.ArrayRemoveAtIndexContext ctx) {
        return arraysVisitor.visitArrayRemoveAtIndex(ctx);
    }
    @Override
    public Object visitArraySetElement(FRePLParser.ArraySetElementContext ctx) {
        return arraysVisitor.visitArraySetElement(ctx);
    }

    @Override
    public Object visitPrintFunctionCall(FRePLParser.PrintFunctionCallContext ctx) {
        return consoleIOVisitor.visitPrintFunctionCall(ctx);
    }
    @Override
    public Object visitPrintNewLine(FRePLParser.PrintNewLineContext ctx) {
        return consoleIOVisitor.visitPrintNewLine(ctx);
    }
    @Override
    public Object visitReadFunctionCall(FRePLParser.ReadFunctionCallContext ctx) {
        return consoleIOVisitor.visitReadFunctionCall(ctx);
    }

    @Override
    public Object visitWhileLoop(FRePLParser.WhileLoopContext ctx) {
        return conditionalsVisitor.visitWhileLoop(ctx);
    }
    @Override
    public Object visitConditionalStatement(FRePLParser.ConditionalStatementContext ctx) {
        return conditionalsVisitor.visitConditionalStatement(ctx);
    }
    @Override
    public Object visitIfStatement(FRePLParser.IfStatementContext ctx) {
        return conditionalsVisitor.visitIfStatement(ctx);
    }
    @Override
    public Object visitElseIfStatement(FRePLParser.ElseIfStatementContext ctx) {
        return conditionalsVisitor.visitElseIfStatement(ctx);
    }
    @Override
    public Object visitElseStatement(FRePLParser.ElseStatementContext ctx) {
        return conditionalsVisitor.visitElseStatement(ctx);
    }

    @Override
    public Object visitBlock(FRePLParser.BlockContext ctx) {
        return blockVisitor.visitBlock(ctx);
    }

    @Override
    public Object visitSaveFunctionCall(FRePLParser.SaveFunctionCallContext ctx) {
        return fileIOVisitor.visitSaveFunctionCall(ctx);
    }
    @Override
    public Object visitLoadFunctionCall(FRePLParser.LoadFunctionCallContext ctx) {
        return fileIOVisitor.visitLoadFunctionCall(ctx);
    }
    @Override
    public Object visitFileExpression(FRePLParser.FileExpressionContext ctx) {
        return fileIOVisitor.visitFileExpression(ctx);
    }
    @Override
    public Object visitFileVar(FRePLParser.FileVarContext ctx) {
        return fileIOVisitor.visitFileVar(ctx);
    }
    @Override
    public Object visitFileLine(FRePLParser.FileLineContext ctx) {
        return fileIOVisitor.visitFileLine(ctx);
    }
    @Override
    public Object visitFileText(FRePLParser.FileTextContext ctx) {
        return fileIOVisitor.visitFileText(ctx);
    }

    @Override
    public Object visitParseInt(FRePLParser.ParseIntContext ctx) {
        return typeParser.visitParseInt(ctx);
    }
    @Override
    public Object visitParseFloat(FRePLParser.ParseFloatContext ctx) {
        return typeParser.visitParseFloat(ctx);
    }
    @Override
    public Object visitParseBool(FRePLParser.ParseBoolContext ctx) {
        return typeParser.visitParseBool(ctx);
    }
    @Override
    public Object visitParseChar(FRePLParser.ParseCharContext ctx) {
        return typeParser.visitParseChar(ctx);
    }
    @Override
    public Object visitParseString(FRePLParser.ParseStringContext ctx) {
        return typeParser.visitParseString(ctx);
    }

    @Override
    public Object visitDeclarationWithoutValue(FRePLParser.DeclarationWithoutValueContext ctx) {
        return varVisitor.visitDeclarationWithoutValue(ctx);
    }
    @Override
    public Object visitDeclarationWithValue(FRePLParser.DeclarationWithValueContext ctx) {
        return varVisitor.visitDeclarationWithValue(ctx);
    }
    @Override
    public Object visitDeclarationImplicitType(FRePLParser.DeclarationImplicitTypeContext ctx) {
        return varVisitor.visitDeclarationImplicitType(ctx);
    }
    @Override
    public Object visitAssignment(FRePLParser.AssignmentContext ctx) {
        return varVisitor.visitAssignment(ctx);
    }

    @Override
    public Object visitBinaryConcatExpression(FRePLParser.BinaryConcatExpressionContext ctx) {
        return expVisitor.visitBinaryConcatExpression(ctx);
    }
    @Override
    public Object visitBinaryComparisonExpression(FRePLParser.BinaryComparisonExpressionContext ctx) {
        return expVisitor.visitBinaryComparisonExpression(ctx);
    }
    @Override
    public Object visitConstantExpression(FRePLParser.ConstantExpressionContext ctx) {
        return expVisitor.visitConstantExpression(ctx);
    }
    @Override
    public Object visitIdentifierExpression(FRePLParser.IdentifierExpressionContext ctx) {
        return expVisitor.visitIdentifierExpression(ctx);
    }
    @Override
    public Object visitParenthesisExpression(FRePLParser.ParenthesisExpressionContext ctx) {
        return expVisitor.visitParenthesisExpression(ctx);
    }
    @Override
    public Object visitBinaryMultExpression(FRePLParser.BinaryMultExpressionContext ctx) {
        return expVisitor.visitBinaryMultExpression(ctx);
    }
    @Override
    public Object visitFunctionReturnExpression(FRePLParser.FunctionReturnExpressionContext ctx) {
        return expVisitor.visitFunctionReturnExpression(ctx);
    }
    @Override
    public Object visitBinaryBooleanExpression(FRePLParser.BinaryBooleanExpressionContext ctx) {
        return expVisitor.visitBinaryBooleanExpression(ctx);
    }
    @Override
    public Object visitUnaryNegationExpression(FRePLParser.UnaryNegationExpressionContext ctx) {
        return expVisitor.visitUnaryNegationExpression(ctx);
    }
    @Override
    public Object visitConstantArrayExpression(FRePLParser.ConstantArrayExpressionContext ctx) {
        return expVisitor.visitConstantArrayExpression(ctx);
    }
    @Override
    public Object visitBinaryPowerExpression(FRePLParser.BinaryPowerExpressionContext ctx) {
        return expVisitor.visitBinaryPowerExpression(ctx);
    }
    @Override
    public Object visitBinaryAddExpression(FRePLParser.BinaryAddExpressionContext ctx) {
        return expVisitor.visitBinaryAddExpression(ctx);
    }
    @Override
    public Object visitConstantArr(FRePLParser.ConstantArrContext ctx) {
        return expVisitor.visitConstantArr(ctx);
    }
    @Override
    public Object visitConstant(FRePLParser.ConstantContext ctx) {
        return expVisitor.visitConstant(ctx);
    }

    @Override
    public Object visitReturnStatement(FRePLParser.ReturnStatementContext ctx) {
        return functionsVisitor.visitReturnStatement(ctx);
    }
    @Override
    protected boolean shouldVisitNextChild(RuleNode node, Object currentResult) {
        return functionsVisitor.shouldVisitNextChild(node, currentResult);
    }
    @Override
    public Object visitFunctionDeclaration(FRePLParser.FunctionDeclarationContext ctx) {
        return functionsVisitor.visitFunctionDeclaration(ctx);
    }
    @Override
    public Object visitFunctionCallExpression(FRePLParser.FunctionCallExpressionContext ctx) {
        return functionsVisitor.visitFunctionCallExpression(ctx);
    }
    @Override
    public Object visitFunctionCall(FRePLParser.FunctionCallContext ctx) {
        return functionsVisitor.visitFunctionCall(ctx);
    }
    @Override
    public Object visitFunctionBody(FRePLParser.FunctionBodyContext ctx) {
        return functionsVisitor.visitFunctionBody(ctx);
    }

    @Override
    public Object visitStatement(FRePLParser.StatementContext ctx) {
        Object result = super.visitStatement(ctx);
        return result;
    }
}
