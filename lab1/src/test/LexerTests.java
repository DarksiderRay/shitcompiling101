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
	public void testEmptyString()
	{
		runtest("",
				new Token(EOF, 0, 0, ""));
	}

	@Test
	public void testStringLiteralEmpty()
	{
		runtest("\"\"",
				new Token(STRING_LITERAL, 0, 0, ""),
				new Token(EOF, 0, 2, ""));
	}

	@Test
	public void testStringLiteralWithDoubleQuote() {
		runtest("\"\"\"",
				new Token(STRING_LITERAL, 0, 0, ""),
				(Token)null);
	}

	@Test
	public void testStringLiteral(){
		runtest("\"\\n\"", 
				new Token(STRING_LITERAL, 0, 0, "\\n"),
				new Token(EOF, 0, 4, ""));

	}

}
