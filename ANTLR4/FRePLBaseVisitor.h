
// Generated from Grammar/FRePL.g4 by ANTLR 4.7.2

#pragma once


#include "antlr4-runtime.h"
#include "FRePLVisitor.h"


/**
 * This class provides an empty implementation of FRePLVisitor, which can be
 * extended to create a visitor which only needs to handle a subset of the available methods.
 */
class  FRePLBaseVisitor : public FRePLVisitor {
public:

  virtual antlrcpp::Any visitProgram(FRePLParser::ProgramContext *ctx) override {
    return visitChildren(ctx);
  }

  virtual antlrcpp::Any visitConstant(FRePLParser::ConstantContext *ctx) override {
    return visitChildren(ctx);
  }


};

