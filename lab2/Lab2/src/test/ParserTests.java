package test;

import static org.junit.Assert.fail;

import java.io.StringReader;

import lexer.Lexer;

import org.junit.Test;

import parser.Parser;

public class ParserTests {
	private void runtest(String src) {
		runtest(src, true);
	}

	private void runtest(String src, boolean succeed) {
		Parser parser = new Parser();
		try {
			parser.parse(new Lexer(new StringReader(src)));
			if(!succeed) {
				fail("Test was supposed to fail, but succeeded");
			}
		} catch (beaver.Parser.Exception e) {
			if(succeed) {
				e.printStackTrace();
				fail(e.getMessage());
			}
		} catch (Throwable e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	@Test
	public void testEmptyModule() {
		runtest("module Test { }");
	}
	
	@Test
	public void testNormalConstructs() {
		runtest(
			"module ImportTest {\n" +
			"	import mytest;\n" +
			"}");
		runtest(
			"module DoubleImportTest {\n" +
			"	import firstTest;\n" +
			"   import doubleTest;\n" +
			"}");
		runtest(
			"module TripleImportTest {\n" +
			"	import firstTest;\n" +
			"   import doubleTest;\n" +
			"   import tripleTest;\n" +
			"}");
		runtest(
			"module FunctionDeclaration {\n" +
			"	import importer;\n" +
			"	void hello_world() {\n" +
			"   }\n" +
			"}");
		runtest(
			"module FunctionDeclaration {\n" +
			"	import importer;\n" +
			"	void hello_world(int hello_there) {\n" +
			"   }\n" +
			"}");
		runtest(
			"module FunctionDeclaration {\n" +
			"	import importer;\n" +
			"	public void hello_world(int hello_there) {\n" +
			"   }\n" +
			"}");
		runtest(
			"module FunctionDeclaration {\n" +
			"	import importer;\n" +
			"	public void hello_world(int hello_there, double down) {\n" +
			"   }\n" +
			"}");
		runtest(
			"module FunctionDeclaration {\n" +
			"	import importer;\n" +
			"	public void hello_world(int hello_there, Double down, int[] no_way) {\n" +
			"   }\n" +
			"   Triple kiss;\n" +
			"}");
		runtest(
			"module FunctionDeclaration {\n" +
			"	import importer;\n" +
			"   import no_way_to_import;\n" +
			"	public void hello_world(int hello_there, Double down, int[] no_way) {\n" +
			"   }\n" +
			"   Triple kiss;\n" +
			"}");
		runtest(
			"module FunctionDeclaration {\n" +
			"	import importer;\n" +
			"   import no_way_to_import;\n" +
			"	public void hello_world(int hello_there, Double down, int[] no_way) {\n" +
			"   }\n" +
			"   Triple kiss;\n" +
			"   public Chinjieh cj_instance;\n" +
			"}");
		runtest(
			"module FunctionDeclaration {\n" +
			"	import importer;\n" +
			"   import no_way_to_import;\n" +
			"	public void hello_world(int hello_there, Double down, int[] no_way) {\n" +
			"   }\n" +
			"   Triple kiss;\n" +
			"   public Chinjieh cj_instance;\n" +
			"   public int Add(int a, int[] b) {\n" +
			"      return a + b[3];\n" +
			"   }\n" +
			"}");
		runtest(
			"module FunctionDeclaration {\n" +
			"	import importer;\n" +
			"   import no_way_to_import;\n" +
			"	public void hello_world(int hello_there, Double down, int[] no_way) {\n" +
			"   }\n" +
			"   Triple kiss;\n" +
			"   public Chinjieh cj_instance;\n" +
			"   public int Add(int a, int[][][] b) {\n" +
			"      return a + b[3][a[3]*a-a][1];\n" +
			"   }\n" +
			"}");
		runtest(
			"module FunctionDeclaration {\n" +
			"    Void void_function(Void of_type_void) {\n" +
			"        int a;" +
			"        a = b = c;\n" +
			"    }\n" +
			"}");
		runtest(
			"module FunctionDeclaration {\n" +
			"    Void void_function(Void of_type_void) {\n" +
			"        int a;" +
			"        a = b[3] = c;\n" +
			"    }\n" +
			"}");
		runtest(
			"module FunctionDeclaration {\n" +
			"    Void void_function(Void of_type_void) {\n" +
			"        int a;" +
			"        a = b[3][0] = c[d/3];\n" +
			"    }\n" +
			"}");
		runtest(
			"module FunctionDeclaration {\n" +
			"    Void void_function(Void of_type_void) {\n" +
			"        3 == 1;\n" +
			"    }\n" +
			"}");
		runtest(
			"module FunctionDeclaration {\n" +
			"    Void void_function(Void of_type_void) {\n" +
			"        a[3 == 1] = 3;\n" +
			"    }\n" +
			"}");
		runtest(
			"module CrazyExpr {\n" +
			"    Void void_function(Void of_type_void) {\n" +
			"        a = a[1] * 3 - [1, 3, 3, 4, 5];\n" +
			"    }\n" +
			"}");
		runtest(
			"module CrazyExpr {\n" +
			"    Void void_function(Void of_type_void) {\n" +
			"        a = a[1] * 3 - [1, 3, 3, 4, 5] - 4 * 5 + 1;\n" +
			"    }\n" +
			"}");
		runtest(
			"module CrazyExpr {\n" +
			"    Void void_function(Void of_type_void) {\n" +
			"        a = a[1] * 3 - [1, 3, 3, 4, 5] - 4 * 5 + 1 * a[a[a[a[a]]]] - [1, [1], [[2], [a[b[c]+d]]], \"hello\"];\n" +
			"    }\n" +
			"}");
		runtest(
			"module ArrayAccessWithExpr {" +
			"	void func() {" +
			"       a[i];\n" +
			"       a[i=1];\n" +
			"       a[i=1][i=1];\n" +
			"       a[i=1][i=1][2];\n" +
			"       a[i=1][i=1][2][a[i]+a[i]];\n" +
			"   }\n" +
			"}"
			);
		runtest(
			"module ArrayAccessWithExpr {" +
			"	void func() {" +
			"       b = a[i=a=a=a][23 * [i] + 3];\n" +
			"   }\n" +
			"}"
			);
		runtest(
			"module ArrayAccessWithExpr {" +
			"	void func() {" +
			"       b = \"hello\" + [\"\"] - a[i=a=a=a][23 * [i] + 3];\n" +
			"   }\n" +
			"}"
			);
	}
}
