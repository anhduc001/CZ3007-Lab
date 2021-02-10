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
 * This class contains unit tests for your lexer. Currently, there is only one test, but you
 * are strongly encouraged to write your own tests.
 */
public class LexerTests {
	// helper method to run tests; no need to change this
	private final void runtest(String input, Token... output) {
		Lexer lexer = new Lexer(new StringReader(input));
		int i=0;
		Token actual=new Token(MODULE, 0, 0, ""), expected;
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
					/* return; */
				}
			} while(!actual.isEOF());
		} catch (IOException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	/** Example unit test. */
	@Test
	public void testKWs() {
		// first argument to runtest is the string to lex; the remaining arguments
		// are the expected tokens
		runtest("module false return while",
				new Token(MODULE, 0, 0, "module"),
				new Token(FALSE, 0, 7, "false"),
				new Token(RETURN, 0, 13, "return"),
				new Token(WHILE, 0, 20, "while"),
				new Token(EOF, 0, 25, ""));
	}

	@Test
	public void testStringLiteralWithDoubleQuote() {
		runtest("\"\"\"",
				new Token(STRING_LITERAL, 0, 0, ""),
				(Token)null,
				new Token(EOF, 0, 3, ""));
	}

	@Test
	public void testStringLiteral() {
		runtest("\"\\n\"", 
				new Token(STRING_LITERAL, 0, 0, "\\n"),
				new Token(EOF, 0, 4, ""));
	}
	
	@Test
	public void testKeywordAndIdentifier() {
		runtest("boolean Boolean wHiLe while true True falSe false breaker integer",
				new Token(BOOLEAN, 0, 0, "boolean"),
				new Token(ID, 0, 8, "Boolean"),
				new Token(ID, 0, 16, "wHiLe"),
				new Token(WHILE, 0, 22, "while"),
				new Token(TRUE, 0, 28, "true"),
				new Token(ID, 0, 33, "True"),
				new Token(ID, 0, 38, "falSe"),
				new Token(FALSE, 0, 44, "false"),
				new Token(ID, 0, 50, "breaker"),
				new Token(ID, 0, 58, "integer"),
				new Token(EOF, 0, 65, ""));
	}
	
	@Test
	public void testStringLiteral2() {
		runtest("\"the\" \"b1g\" brown f0x",
				new Token(STRING_LITERAL, 0, 0, "the"),
				new Token(STRING_LITERAL, 0, 6, "b1g"),
				new Token(ID, 0, 12, "brown"),
				new Token(ID, 0, 18, "f0x"),
				new Token(EOF, 0, 21, ""));
	}
	
	@Test
	public void testIntegerLiteral() {
		runtest("0203 405 boolean123 -158 7+7=14",
				new Token(INT_LITERAL, 0, 0, "0203"),
				new Token(INT_LITERAL, 0, 5, "405"),
				new Token(ID, 0, 9, "boolean123"),
				new Token(MINUS, 0, 20, "-"),
				new Token(INT_LITERAL, 0, 21, "158"),
				new Token(INT_LITERAL, 0, 25, "7"),
				new Token(PLUS, 0, 26, "+"),
				new Token(INT_LITERAL, 0, 27, "7"),
				new Token(EQL, 0, 28, "="),
				new Token(INT_LITERAL, 0, 29, "14"),
				new Token(EOF, 0, 31, ""));
	}

}
