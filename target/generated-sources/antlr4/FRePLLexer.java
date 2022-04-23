// Generated from FRePL.g4 by ANTLR 4.9.3
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class FRePLLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.9.3", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		INT=1, FLOAT=2, BOOLEAN=3, COMMENT=4, WHITESPACE=5;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"INT", "FLOAT", "BOOLEAN", "COMMENT", "WHITESPACE"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "INT", "FLOAT", "BOOLEAN", "COMMENT", "WHITESPACE"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}


	public FRePLLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "FRePL.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\7D\b\1\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\3\2\6\2\17\n\2\r\2\16\2\20\3\3\6\3\24\n"+
		"\3\r\3\16\3\25\3\3\3\3\6\3\32\n\3\r\3\16\3\33\3\4\3\4\3\4\3\4\3\4\3\4"+
		"\3\4\3\4\3\4\5\4\'\n\4\3\5\3\5\3\5\3\5\7\5-\n\5\f\5\16\5\60\13\5\3\5\3"+
		"\5\3\5\3\5\7\5\66\n\5\f\5\16\59\13\5\3\5\3\5\5\5=\n\5\3\5\3\5\3\6\3\6"+
		"\3\6\3\6\3\67\2\7\3\3\5\4\7\5\t\6\13\7\3\2\5\3\2\62;\4\2\f\f\17\17\5\2"+
		"\13\f\17\17\"\"\2J\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13"+
		"\3\2\2\2\3\16\3\2\2\2\5\23\3\2\2\2\7&\3\2\2\2\t<\3\2\2\2\13@\3\2\2\2\r"+
		"\17\t\2\2\2\16\r\3\2\2\2\17\20\3\2\2\2\20\16\3\2\2\2\20\21\3\2\2\2\21"+
		"\4\3\2\2\2\22\24\t\2\2\2\23\22\3\2\2\2\24\25\3\2\2\2\25\23\3\2\2\2\25"+
		"\26\3\2\2\2\26\27\3\2\2\2\27\31\7\60\2\2\30\32\t\2\2\2\31\30\3\2\2\2\32"+
		"\33\3\2\2\2\33\31\3\2\2\2\33\34\3\2\2\2\34\6\3\2\2\2\35\36\7h\2\2\36\37"+
		"\7c\2\2\37 \7n\2\2 !\7u\2\2!\'\7g\2\2\"#\7v\2\2#$\7t\2\2$%\7w\2\2%\'\7"+
		"g\2\2&\35\3\2\2\2&\"\3\2\2\2\'\b\3\2\2\2()\7\61\2\2)*\7\61\2\2*.\3\2\2"+
		"\2+-\n\3\2\2,+\3\2\2\2-\60\3\2\2\2.,\3\2\2\2./\3\2\2\2/=\3\2\2\2\60.\3"+
		"\2\2\2\61\62\7\61\2\2\62\63\7,\2\2\63\67\3\2\2\2\64\66\13\2\2\2\65\64"+
		"\3\2\2\2\669\3\2\2\2\678\3\2\2\2\67\65\3\2\2\28:\3\2\2\29\67\3\2\2\2:"+
		";\7,\2\2;=\7\61\2\2<(\3\2\2\2<\61\3\2\2\2=>\3\2\2\2>?\b\5\2\2?\n\3\2\2"+
		"\2@A\t\4\2\2AB\3\2\2\2BC\b\6\2\2C\f\3\2\2\2\n\2\20\25\33&.\67<\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}