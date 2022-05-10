import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream
import org.antlr.v4.runtime.tree.ParseTree
import org.antlr.v4.runtime.tree.ParseTreeWalker
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class JavaUppercaseMethodListenerTest {
    
    @Test
    fun `when one method starts with uppercase then one error should be returned`() {
        val javaClassContent: String = "public class SampleClass { void DoSomething(){} }"
        val java8Lexer: Java8Lexer = Java8Lexer(CharStreams.fromString(javaClassContent))
        val tokens: CommonTokenStream = CommonTokenStream(java8Lexer)
        val java8Parser: Java8Parser = Java8Parser(tokens)
        val tree: ParseTree = java8Parser.compilationUnit()
        val walker: ParseTreeWalker = ParseTreeWalker()
        val uppercaseMethodListener: JavaUppercaseMethodListener = JavaUppercaseMethodListener()
        
        walker.walk(uppercaseMethodListener, tree)
        
        assertEquals(1, uppercaseMethodListener.errors.size)
        assertEquals("Method DoSomething is uppercased!", uppercaseMethodListener.errors[0])
    }
}