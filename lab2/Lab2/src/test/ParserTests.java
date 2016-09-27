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
	
	private void runfailtest(String src) {
		runtest(src, false);
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
			"module TypeDeclaration {" +
			"	public type publicNoobs = \"immastring\";" +
			"}"
			);
		runtest(
			"module TypeDeclaration2 {" +
			"	public type publicNoobs = \"immastring\";" +
			"   type a = \"asdasd\";" +
			"}"
			);
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
		runtest(
			"module WhileStatement {" +
			"	import whilelieliewhile;" +
			"   void func() {" +
			"   	while (a+b) break;" +
			"	}" +
			"}"
			);
		runtest(
			"module IfStatementEasy {" +
			"	import iforelse;" +
			"   void func() {" +
			"   	if (a=b) return a;" +
			"	}" +
			"}"
			);
		runtest(
			"module IfStatementElse {" +
			"	import iforelse;" +
			"   void func() {" +
			"   	if (a=b) return a; else a=b+1;" +
			"	}" +
			"}"
			);
		runtest(
			"module IfStatementChainsaw {" +
			"	import iforelse;" +
			"   void func() {" +
			"   	if (a=b) return a; else if (a=b) {\n" +
			"   		if (a=b) {\n" +
			"   			if ((a=b)) if(a=b) if(a=b) break;\n" +
			"   			if (a=b) if(a=b) {}\n" +
			"   		}\n" +
			"       }\n" +
			"	}\n" +
			"}"
			);
	}
	
	@Test
	public void testWrongConstructs() {
		runfailtest(
			"module {}"
		);
		runfailtest(
			"module MissingCurly {"
		);
		runfailtest(
			"nomodule {}"
		);
		runfailtest(
			"module import asd;"
		);
		runfailtest(
			"module 2Modules1Compiler{}\n" +
			"module 2Compilers1Module{}\n"
		);
		runfailtest(
			"module SpamShit{\n" +
			"	aasdasdhasjkdhasjkfhasjkdksd\n" +
			"}"
		);
		runfailtest(
			"module OutofPlaceExp{\n" +
			"	a = a+b;\n" +
			"}"
		);
		runfailtest(
			"module FunctionFailParamList{\n" +
			"	public asd (int a,) {}\n" +
			"}"
		);
		runfailtest(
			"module FunctionFailParamList{\n" +
			"	public asd (int a int b) {}\n" +
			"}"
		);
		runfailtest(
			"module FailFunctionCallMissingSemicolon{\n" +
			"	void func() {\n" +
			"      thisisafunction(a+b)\n" +
			"   }\n" +
			"}"
		);
		runfailtest(
			"module FailFunctionCall{\n" +
			"	void func() {\n" +
			"      thisisafunction(a+b,123,4,);\n" +
			"   }\n" +
			"}"
		);
		runfailtest(
			"module FailIfStatement{\n" +
			"	void func() {\n" +
			"      if a+b return;\n" +
			"   }\n" +
			"}"
		);
		runfailtest(
			"module FailIfStatement{\n" +
			"	void func() {\n" +
			"      if (a+b) else return; else return;\n" +
			"   }\n" +
			"}"
		);
		runfailtest(
			"module FailIfStatement{\n" +
			"	void func() {\n" +
			"      if (a+b) else return else return;\n" +
			"   }\n" +
			"}"
		);
		runfailtest(
			"module FailIfStatement{\n" +
			"	void func() {\n" +
			"      if (a+b) else if return;\n" +
			"   }\n" +
			"}"
		);
		runfailtest(
			"module FailIfStatement{\n" +
			"	void func() {\n" +
			"      if (a+b)\n" +
			"   }\n" +
			"}"
		);
		runfailtest(
			"module FailIfStatement{\n" +
			"	void func() {\n" +
			"      else (a+b);\n" +
			"   }\n" +
			"}"
			);
		runfailtest(
			"module FailArrayExpEmpty{\n" +
			"	void func() {\n" +
			"      [];\n" +
			"   }\n" +
			"}"
		);
		runfailtest(
			"module FailArrayExp{\n" +
			"	void func() {\n" +
			"      [a+2,];\n" +
			"   }\n" +
			"}"
		);
		runfailtest(
			"module FailArrayExp{\n" +
			"	void func() {\n" +
			"      a+2];\n" +
			"   }\n" +
			"}"
		);
		runfailtest(
			"module FailArrayExp{\n" +
			"	void func() {\n" +
			"      [a+2;\n" +
			"   }\n" +
			"}"
		);
		runfailtest(
			"module FailAssignment{\n" +
			"	void func() {\n" +
			"      why=;\n" +
			"   }\n" +
			"}"
		);
		runfailtest(
			"module FailArrayAccess{\n" +
			"	void func() {\n" +
			"      this[];\n" +
			"   }\n" +
			"}"
		);
		runfailtest(
			"module FailMaths{\n" +
			"	void func() {\n" +
			"      mathssux=a+b/123/123/123/;" +
			"   }\n" +
			"}"
		);
		runfailtest(
			"module FailMaths{\n" +
			"	void func() {\n" +
			"      mathssux=a+b/123+123(321)/(21;" +
			"   }\n" +
			"}"
		);
		runfailtest(
			"module FailMaths{\n" +
			"	void func() {\n" +
			"      mathssux=a//2;" +
			"   }\n" +
			"}"
		);
		runfailtest(
			"module FailMaths{\n" +
			"	void func() {\n" +
			"      mathssux=array**2;" +
			"   }\n" +
			"}"
		);
		runfailtest(
			"module FailMaths{\n" +
				"	void func() {\n" +
			"      mathssux=array*2++2;" +
			"   }\n" +
			"}"
		);
	}
}
