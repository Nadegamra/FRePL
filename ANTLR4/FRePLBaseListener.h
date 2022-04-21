
// Generated from Grammar/FRePL.g4 by ANTLR 4.7.2

#pragma once


#include "antlr4-runtime.h"
#include "FRePLListener.h"


/**
 * This class provides an empty implementation of FRePLListener,
 * which can be extended to create a listener which only needs to handle a subset
 * of the available methods.
 */
class  FRePLBaseListener : public FRePLListener {
public:

  virtual void enterProgram(FRePLParser::ProgramContext * /*ctx*/) override { }
  virtual void exitProgram(FRePLParser::ProgramContext * /*ctx*/) override { }

  virtual void enterConstant(FRePLParser::ConstantContext * /*ctx*/) override { }
  virtual void exitConstant(FRePLParser::ConstantContext * /*ctx*/) override { }


  virtual void enterEveryRule(antlr4::ParserRuleContext * /*ctx*/) override { }
  virtual void exitEveryRule(antlr4::ParserRuleContext * /*ctx*/) override { }
  virtual void visitTerminal(antlr4::tree::TerminalNode * /*node*/) override { }
  virtual void visitErrorNode(antlr4::tree::ErrorNode * /*node*/) override { }

};

