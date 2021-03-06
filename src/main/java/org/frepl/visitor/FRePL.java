package org.frepl.visitor;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

public class FRePL {
    public static void main(String[] args) {
        try {
            execute(CharStreams.fromFileName(args[0]));
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
}

    public static Object execute(CharStream stream) {
        FRePLLexer lexer = new FRePLLexer(stream);
        FRePLParser parser = new FRePLParser(new CommonTokenStream(lexer));
        parser.setBuildParseTree(true);
        ParseTree tree = parser.program();

        FRePLVisitorImpl visitor = new FRePLVisitorImpl();
        return visitor.visit(tree);
    }
}
