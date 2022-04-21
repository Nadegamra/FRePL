#include <iostream>

#include "antlr4-runtime.h"
#include "ANTLR4/FRePLLexer.h"
#include "ANTLR4/FRePLParser.h"
#include "ANTLR4/FRePLBaseListener.h"

using namespace antlr4;

class LanguageListener: public FRePLBaseListener {
public:
  void enterKey(ParserRuleContext *ctx) override {
	// Do something when entering the key rule.
  }
};


int main(int argc, const char* argv[]) {
  std::ifstream stream;
  stream.open(argv[1]);
  ANTLRInputStream input(stream);
  MyGrammarLexer lexer(&input);
  CommonTokenStream tokens(&lexer);
  MyGrammarParser parser(&tokens);

  tree::ParseTree *tree = parser.key();
  TreeShapeListener listener;
  tree::ParseTreeWalker::DEFAULT.walk(&listener, tree);

  return 0;
}