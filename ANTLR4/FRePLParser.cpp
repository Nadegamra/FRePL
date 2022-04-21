
// Generated from Grammar/FRePL.g4 by ANTLR 4.7.2


#include "FRePLListener.h"
#include "FRePLVisitor.h"

#include "FRePLParser.h"


using namespace antlrcpp;
using namespace antlr4;

FRePLParser::FRePLParser(TokenStream *input) : Parser(input) {
  _interpreter = new atn::ParserATNSimulator(this, _atn, _decisionToDFA, _sharedContextCache);
}

FRePLParser::~FRePLParser() {
  delete _interpreter;
}

std::string FRePLParser::getGrammarFileName() const {
  return "FRePL.g4";
}

const std::vector<std::string>& FRePLParser::getRuleNames() const {
  return _ruleNames;
}

dfa::Vocabulary& FRePLParser::getVocabulary() const {
  return _vocabulary;
}


//----------------- ProgramContext ------------------------------------------------------------------

FRePLParser::ProgramContext::ProgramContext(ParserRuleContext *parent, size_t invokingState)
  : ParserRuleContext(parent, invokingState) {
}

tree::TerminalNode* FRePLParser::ProgramContext::EOF() {
  return getToken(FRePLParser::EOF, 0);
}

std::vector<FRePLParser::ConstantContext *> FRePLParser::ProgramContext::constant() {
  return getRuleContexts<FRePLParser::ConstantContext>();
}

FRePLParser::ConstantContext* FRePLParser::ProgramContext::constant(size_t i) {
  return getRuleContext<FRePLParser::ConstantContext>(i);
}


size_t FRePLParser::ProgramContext::getRuleIndex() const {
  return FRePLParser::RuleProgram;
}

void FRePLParser::ProgramContext::enterRule(tree::ParseTreeListener *listener) {
  auto parserListener = dynamic_cast<FRePLListener *>(listener);
  if (parserListener != nullptr)
    parserListener->enterProgram(this);
}

void FRePLParser::ProgramContext::exitRule(tree::ParseTreeListener *listener) {
  auto parserListener = dynamic_cast<FRePLListener *>(listener);
  if (parserListener != nullptr)
    parserListener->exitProgram(this);
}


antlrcpp::Any FRePLParser::ProgramContext::accept(tree::ParseTreeVisitor *visitor) {
  if (auto parserVisitor = dynamic_cast<FRePLVisitor*>(visitor))
    return parserVisitor->visitProgram(this);
  else
    return visitor->visitChildren(this);
}

FRePLParser::ProgramContext* FRePLParser::program() {
  ProgramContext *_localctx = _tracker.createInstance<ProgramContext>(_ctx, getState());
  enterRule(_localctx, 0, FRePLParser::RuleProgram);
  size_t _la = 0;

  auto onExit = finally([=] {
    exitRule();
  });
  try {
    enterOuterAlt(_localctx, 1);
    setState(5); 
    _errHandler->sync(this);
    _la = _input->LA(1);
    do {
      setState(4);
      constant();
      setState(7); 
      _errHandler->sync(this);
      _la = _input->LA(1);
    } while ((((_la & ~ 0x3fULL) == 0) &&
      ((1ULL << _la) & ((1ULL << FRePLParser::INT)
      | (1ULL << FRePLParser::FLOAT)
      | (1ULL << FRePLParser::BOOLEAN))) != 0));
    setState(9);
    match(FRePLParser::EOF);
   
  }
  catch (RecognitionException &e) {
    _errHandler->reportError(this, e);
    _localctx->exception = std::current_exception();
    _errHandler->recover(this, _localctx->exception);
  }

  return _localctx;
}

//----------------- ConstantContext ------------------------------------------------------------------

FRePLParser::ConstantContext::ConstantContext(ParserRuleContext *parent, size_t invokingState)
  : ParserRuleContext(parent, invokingState) {
}

tree::TerminalNode* FRePLParser::ConstantContext::INT() {
  return getToken(FRePLParser::INT, 0);
}

tree::TerminalNode* FRePLParser::ConstantContext::FLOAT() {
  return getToken(FRePLParser::FLOAT, 0);
}

tree::TerminalNode* FRePLParser::ConstantContext::BOOLEAN() {
  return getToken(FRePLParser::BOOLEAN, 0);
}


size_t FRePLParser::ConstantContext::getRuleIndex() const {
  return FRePLParser::RuleConstant;
}

void FRePLParser::ConstantContext::enterRule(tree::ParseTreeListener *listener) {
  auto parserListener = dynamic_cast<FRePLListener *>(listener);
  if (parserListener != nullptr)
    parserListener->enterConstant(this);
}

void FRePLParser::ConstantContext::exitRule(tree::ParseTreeListener *listener) {
  auto parserListener = dynamic_cast<FRePLListener *>(listener);
  if (parserListener != nullptr)
    parserListener->exitConstant(this);
}


antlrcpp::Any FRePLParser::ConstantContext::accept(tree::ParseTreeVisitor *visitor) {
  if (auto parserVisitor = dynamic_cast<FRePLVisitor*>(visitor))
    return parserVisitor->visitConstant(this);
  else
    return visitor->visitChildren(this);
}

FRePLParser::ConstantContext* FRePLParser::constant() {
  ConstantContext *_localctx = _tracker.createInstance<ConstantContext>(_ctx, getState());
  enterRule(_localctx, 2, FRePLParser::RuleConstant);
  size_t _la = 0;

  auto onExit = finally([=] {
    exitRule();
  });
  try {
    enterOuterAlt(_localctx, 1);
    setState(11);
    _la = _input->LA(1);
    if (!((((_la & ~ 0x3fULL) == 0) &&
      ((1ULL << _la) & ((1ULL << FRePLParser::INT)
      | (1ULL << FRePLParser::FLOAT)
      | (1ULL << FRePLParser::BOOLEAN))) != 0))) {
    _errHandler->recoverInline(this);
    }
    else {
      _errHandler->reportMatch(this);
      consume();
    }
   
  }
  catch (RecognitionException &e) {
    _errHandler->reportError(this, e);
    _localctx->exception = std::current_exception();
    _errHandler->recover(this, _localctx->exception);
  }

  return _localctx;
}

// Static vars and initialization.
std::vector<dfa::DFA> FRePLParser::_decisionToDFA;
atn::PredictionContextCache FRePLParser::_sharedContextCache;

// We own the ATN which in turn owns the ATN states.
atn::ATN FRePLParser::_atn;
std::vector<uint16_t> FRePLParser::_serializedATN;

std::vector<std::string> FRePLParser::_ruleNames = {
  "program", "constant"
};

std::vector<std::string> FRePLParser::_literalNames = {
};

std::vector<std::string> FRePLParser::_symbolicNames = {
  "", "INT", "FLOAT", "BOOLEAN", "COMMENT", "WHITESPACE"
};

dfa::Vocabulary FRePLParser::_vocabulary(_literalNames, _symbolicNames);

std::vector<std::string> FRePLParser::_tokenNames;

FRePLParser::Initializer::Initializer() {
	for (size_t i = 0; i < _symbolicNames.size(); ++i) {
		std::string name = _vocabulary.getLiteralName(i);
		if (name.empty()) {
			name = _vocabulary.getSymbolicName(i);
		}

		if (name.empty()) {
			_tokenNames.push_back("<INVALID>");
		} else {
      _tokenNames.push_back(name);
    }
	}

  _serializedATN = {
    0x3, 0x608b, 0xa72a, 0x8133, 0xb9ed, 0x417c, 0x3be7, 0x7786, 0x5964, 
    0x3, 0x7, 0x10, 0x4, 0x2, 0x9, 0x2, 0x4, 0x3, 0x9, 0x3, 0x3, 0x2, 0x6, 
    0x2, 0x8, 0xa, 0x2, 0xd, 0x2, 0xe, 0x2, 0x9, 0x3, 0x2, 0x3, 0x2, 0x3, 
    0x3, 0x3, 0x3, 0x3, 0x3, 0x2, 0x2, 0x4, 0x2, 0x4, 0x2, 0x3, 0x3, 0x2, 
    0x3, 0x5, 0x2, 0xe, 0x2, 0x7, 0x3, 0x2, 0x2, 0x2, 0x4, 0xd, 0x3, 0x2, 
    0x2, 0x2, 0x6, 0x8, 0x5, 0x4, 0x3, 0x2, 0x7, 0x6, 0x3, 0x2, 0x2, 0x2, 
    0x8, 0x9, 0x3, 0x2, 0x2, 0x2, 0x9, 0x7, 0x3, 0x2, 0x2, 0x2, 0x9, 0xa, 
    0x3, 0x2, 0x2, 0x2, 0xa, 0xb, 0x3, 0x2, 0x2, 0x2, 0xb, 0xc, 0x7, 0x2, 
    0x2, 0x3, 0xc, 0x3, 0x3, 0x2, 0x2, 0x2, 0xd, 0xe, 0x9, 0x2, 0x2, 0x2, 
    0xe, 0x5, 0x3, 0x2, 0x2, 0x2, 0x3, 0x9, 
  };

  atn::ATNDeserializer deserializer;
  _atn = deserializer.deserialize(_serializedATN);

  size_t count = _atn.getNumberOfDecisions();
  _decisionToDFA.reserve(count);
  for (size_t i = 0; i < count; i++) { 
    _decisionToDFA.emplace_back(_atn.getDecisionState(i), i);
  }
}

FRePLParser::Initializer FRePLParser::_init;
