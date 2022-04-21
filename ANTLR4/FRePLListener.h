
// Generated from Grammar/FRePL.g4 by ANTLR 4.7.2

#pragma once


#include "antlr4-runtime.h"
#include "FRePLParser.h"


/**
 * This interface defines an abstract listener for a parse tree produced by FRePLParser.
 */
class  FRePLListener : public antlr4::tree::ParseTreeListener {
public:

  virtual void enterProgram(FRePLParser::ProgramContext *ctx) = 0;
  virtual void exitProgram(FRePLParser::ProgramContext *ctx) = 0;

  virtual void enterConstant(FRePLParser::ConstantContext *ctx) = 0;
  virtual void exitConstant(FRePLParser::ConstantContext *ctx) = 0;


};

