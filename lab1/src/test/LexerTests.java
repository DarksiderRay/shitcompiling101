package test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.StringReader;

import lexer.Lexer;

import org.junit.Test;

import frontend.Token;
import frontend.Token.Type;
import static frontend.Token.Type.*;

/**
 * This class contains unit tests for your lexer. Currently, there are only 3 tests, but you
 * are strongly encouraged to write your own tests.
 */
public class LexerTests {
	// helper method to run tests; no need to change this
	private final void runtest(String input, Token... output) {
		Lexer lexer = new Lexer(new StringReader(input));
		int i=0;
		Token actual, expected;
		try {
			do {
				assertTrue(i < output.length);
				expected = output[i++];
				try {
					actual = lexer.nextToken();
					assertEquals(expected, actual);
				} catch(Error e) {
					if(expected != null)
						fail(e.getMessage());
					return;
				}
			} while(!actual.isEOF());
		} catch (IOException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	/** Example unit test. */
	@Test
	public void testTest()
	{
		runtest("==",
				new Token(EQEQ, 0, 0, "=="),
				new Token(EOF, 0, 2, ""));
	}

	@Test
	public void testWhitespaceChars()
	{
		runtest("a\nbc	def\nghi   jk",
				new Token(ID, 0, 0, "a"),
				new Token(ID, 1, 0, "bc"),
				new Token(ID, 1, 3, "def"),
				new Token(ID, 2, 0, "ghi"),
				new Token(ID, 2, 6, "jk"),
				new Token(EOF, 2, 8, ""));
	}
	
	@Test
	public void testKWs() {
		// first argument to runtest is the string to lex; the remaining arguments
		// are the expected tokens
		runtest("true false if else while",
				new Token(TRUE, 0, 0, "true"),
				new Token(FALSE, 0, 5, "false"),
				new Token(IF, 0, 11, "if"),
				new Token(ELSE, 0, 14, "else"),
				new Token(WHILE, 0, 19, "while"),
				new Token(EOF, 0, 24, ""));

		runtest("type boolean int void",
				new Token(TYPE, 0, 0, "type"),
				new Token(BOOLEAN, 0, 5, "boolean"),
				new Token(INT, 0, 13, "int"),
				new Token(VOID, 0, 17, "void"),
				new Token(EOF, 0, 21, ""));

		runtest("break import module public return",
				new Token(BREAK, 0, 0, "break"),
				new Token(IMPORT, 0, 6, "import"),
				new Token(MODULE, 0, 13, "module"),
				new Token(PUBLIC, 0, 20, "public"),
				new Token(RETURN, 0, 27, "return"),
				new Token(EOF, 0, 33, ""));

		// Case sensitive?
		runtest("Break Import module public return",
				new Token(ID, 0, 0, "Break"),
				new Token(ID, 0, 6, "Import"),
				new Token(MODULE, 0, 13, "module"),
				new Token(PUBLIC, 0, 20, "public"),
				new Token(RETURN, 0, 27, "return"),
				new Token(EOF, 0, 33, ""));
	}

	@Test
	public void testPunctuation() {
		runtest("[1] (a) {bcd}",
				new Token(LBRACKET, 0, 0, "["),
				new Token(INT_LITERAL, 0, 1, "1"),
				new Token(RBRACKET, 0, 2, "]"),
				new Token(LPAREN, 0, 4, "("),
				new Token(ID, 0, 5, "a"),
				new Token(RPAREN, 0, 6, ")"),
				new Token(LCURLY, 0, 8, "{"),
				new Token(ID, 0, 9, "bcd"),
				new Token(RCURLY, 0, 12, "}"),
				new Token(EOF, 0, 13, ""));

		runtest("a,b,c;a;",
				new Token(ID, 0, 0, "a"),
				new Token(COMMA, 0, 1, ","),
				new Token(ID, 0, 2, "b"),
				new Token(COMMA, 0, 3, ","),
				new Token(ID, 0, 4, "c"),
				new Token(SEMICOLON, 0, 5, ";"),
				new Token(ID, 0, 6, "a"),
				new Token(SEMICOLON, 0, 7, ";"),
				new Token(EOF, 0, 8, ""));
	}

	@Test
	public void testEmptySource()
	{
		runtest("",
				new Token(EOF, 0, 0, ""));
	}

	@Test
	public void testIntLiteral()
	{
		runtest("1 + 23",
				new Token(INT_LITERAL, 0, 0, "1"),
				new Token(PLUS, 0, 2, "+"),
				new Token(INT_LITERAL, 0, 4, "23"),
				new Token(EOF, 0, 6, ""));
	}

	@Test
	public void testIntLiteralSigned()
	{
		runtest("1 + -2 - +3",
				new Token(INT_LITERAL, 0, 0, "1"),
				new Token(PLUS, 0, 2, "+"),
				new Token(MINUS, 0, 4, "-"),
				new Token(INT_LITERAL, 0, 5, "2"),
				new Token(MINUS, 0, 7, "-"),
				new Token(PLUS, 0, 9, "+"),
				new Token(INT_LITERAL, 0, 10, "3"),
				new Token(EOF, 0, 11, ""));
	}

	@Test
	public void testIntLiteralZeros()
	{
		runtest("0001 + 0002",
				new Token(INT_LITERAL, 0, 0, "0001"),
				new Token(PLUS, 0, 5, "+"),
				new Token(INT_LITERAL, 0, 7, "0002"),
				new Token(EOF, 0, 11, ""));
	}

	@Test
	public void testStringLiteral(){
		runtest("\"\\n\"",
				new Token(STRING_LITERAL, 0, 0, "\\n"),
				new Token(EOF, 0, 4, ""));
	}

	@Test
	public void testStringLiteralEmpty()
	{
		runtest("\"\"",
				new Token(STRING_LITERAL, 0, 0, ""),
				new Token(EOF, 0, 2, ""));
	}
	
	@Test
	public void testStringWithWhitespaces() {
		runtest("\"hello\tworld\"",
				new Token(STRING_LITERAL, 0, 0, "hello\tworld"),
				new Token(EOF, 0, 13, ""));
		runtest("\"\twhat is 42_-_-?+!!\t#@ baby\"",
				new Token(STRING_LITERAL, 0, 0, "\twhat is 42_-_-?+!!\t#@ baby"),
				new Token(EOF, 0, 29, ""));
	}
	
	@Test
	public void testNewline() {
		runtest("\n",
				new Token(EOF, 1, 0, ""));
		
		// \r \f are also newline characters for JFlex!
		runtest("\r",
				new Token(EOF, 1, 0, ""));
		runtest("\f",
				new Token(EOF, 1, 0, ""));
	}
	
	@Test
	public void testInvalidString() {
		runtest("\"\n\"",
				(Token) null);
		runtest("\"hello\" \n\"\"\"th\nere\"",
				new Token(STRING_LITERAL, 0, 0, "hello"),
				new Token(STRING_LITERAL, 1, 0, ""),
				(Token) null);
	}
	
	@Test
	public void testUnicode() {
		runtest("\"\u00ea\"",
				new Token(STRING_LITERAL, 0, 0, "\u00ea"),
				new Token(EOF, 0, 3, ""));
		runtest("e \u003D \u0031",
				new Token(ID, 0, 0, "e"),
				new Token(EQL, 0, 2, "="),
				new Token(INT_LITERAL, 0, 4, "1"),
				new Token(EOF, 0, 5, ""));
		
		// We assume that identifier can only be ASCII letters.
		runtest("\u00ea \u003D \u0031",
				(Token) null);
	}
	

	@Test
	public void testStringLiteralWithDoubleQuote() {
		runtest("\"\"\"",
				new Token(STRING_LITERAL, 0, 0, ""),
				(Token)null);
	}

	@Test
	public void testStringAndIdentifierMixture() {
		runtest("\"hello\" hello",
				new Token(STRING_LITERAL, 0, 0, "hello"),
				new Token(ID, 0, 8, "hello"),
				new Token(EOF, 0, 13, ""));
	}
	
	@Test
	public void testComplexIdentifiers() {
		runtest("_am3 3m_3",
				new Token(ID, 0, 0, "_am3"),
				new Token(INT_LITERAL, 0, 5, "3"),
				new Token(ID, 0, 6, "m_3"),
				new Token(EOF, 0, 9, ""));
		runtest("_aAaB8*8",
				new Token(ID, 0, 0, "_aAaB8"),
				new Token(TIMES, 0, 6, "*"),
				new Token(INT_LITERAL, 0, 7, "8"),
				new Token(EOF, 0, 8, ""));
	}
	
	@Test
	public void testAmbiguousTokens() {
		runtest("!==",
				new Token(NEQ, 0, 0, "!="),
				new Token(EQL, 0, 2, "="),
				new Token(EOF, 0, 3, ""));
		
		// Invalid last trailing '!'
		runtest("==!",
				new Token(EQEQ, 0, 0, "=="),
				(Token) null);
		
		runtest("// comment",
				new Token(DIV, 0, 0, "/"),
				new Token(DIV, 0, 1, "/"),
				new Token(ID, 0, 3, "comment"),
				new Token(EOF, 0, 10, ""));
		
		runtest("_/=*!====",
				new Token(ID, 0, 0, "_"),
				new Token(DIV, 0, 1, "/"),
				new Token(EQL, 0, 2, "="),
				new Token(TIMES, 0, 3, "*"),
				new Token(NEQ, 0, 4, "!="),
				new Token(EQEQ, 0, 6, "=="),
				new Token(EQL, 0, 8, "="),
				new Token(EOF, 0, 9, ""));
		runtest("<=>",
				new Token(LEQ, 0, 0, "<="),
				new Token(GT, 0, 2, ">"),
				new Token(EOF, 0, 3, ""));
		
		runtest("_if if if_",
				new Token(ID, 0, 0, "_if"),
				new Token(IF, 0, 4, "if"),
				new Token(ID, 0, 7, "if_"),
				new Token(EOF, 0, 10, ""));
		runtest("if IF If iF",
				new Token(IF, 0, 0, "if"),
				new Token(ID, 0, 3, "IF"),
				new Token(ID, 0, 6, "If"),
				new Token(ID, 0, 9, "iF"),
				new Token(EOF, 0, 11, ""));
		
		runtest("boolean truetrue = true lah",
				new Token(BOOLEAN, 0, 0, "boolean"),
				new Token(ID, 0, 8, "truetrue"),
				new Token(EQL, 0, 17, "="),
				new Token(TRUE, 0, 19, "true"),
				new Token(ID, 0, 24, "lah"),
				new Token(EOF, 0, 27, ""));
	}
	
	@Test
	public void testOrdinaryStatement() {
		runtest("int _id != -43;",
				new Token(INT, 0, 0, "int"),
				new Token(ID, 0, 4, "_id"),
				new Token(NEQ, 0, 8, "!="),
				new Token(MINUS, 0, 11, "-"),
				new Token(INT_LITERAL, 0, 12, "43"),
				new Token(SEMICOLON, 0, 14, ";"),
				new Token(EOF, 0, 15, ""));
		
		runtest("if( hello!=\"12 hello\"+12 \n) return returns;\n\t\t\t\t;",
				new Token(IF, 0, 0, "if"),
				new Token(LPAREN, 0, 2, "("),
				new Token(ID, 0, 4, "hello"),
				new Token(NEQ, 0, 9, "!="),
				new Token(STRING_LITERAL, 0, 11, "12 hello"),
				new Token(PLUS, 0, 21, "+"),
				new Token(INT_LITERAL, 0, 22, "12"),
				new Token(RPAREN, 1, 0, ")"),
				new Token(RETURN, 1, 2, "return"),
				new Token(ID, 1, 9, "returns"),
				new Token(SEMICOLON, 1, 16, ";"),
				new Token(SEMICOLON, 2, 4, ";"),
				new Token(EOF, 2, 5, ""));
	}

}
