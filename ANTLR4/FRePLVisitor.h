
// Generated from Grammar/FRePL.g4 by ANTLR 4.7.2

#pragma once


#include "antlr4-runtime.h"
#include "FRePLParser.h"



/**
 * This class defines an abstract visitor for a parse tree
 * produced by FRePLParser.
 */
class  FRePLVisitor : public antlr4::tree::AbstractParseTreeVisitor {
public:

  /**
   * Visit parse trees produced by FRePLParser.
   */
    virtual antlrcpp::Any visitProgram(FRePLParser::ProgramContext *context) = 0;

    virtual antlrcpp::Any visitConstant(FRePLParser::ConstantContext *context) = 0;


};

