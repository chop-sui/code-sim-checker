import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream
import org.antlr.v4.runtime.tree.ParseTree
import org.antlr.v4.runtime.tree.ParseTreeWalker
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class JavaMethodListenerTest {
    
    @Test
    fun `should collect method data`() {
        val javaClassContent = "public class SampleClass { public boolean foo(int x, int y, int z){ return 1; } }"
        val javaLexer = JavaLexer(CharStreams.fromString(javaClassContent))
        val tokens = CommonTokenStream(javaLexer)
        val javaParser = JavaParser(tokens)
        val tree: ParseTree = javaParser.compilationUnit()
        val walker = ParseTreeWalker()
        val methodListener = JavaMethodListener()
    
        walker.walk(methodListener, tree)
        
//        assertEquals(1, methodListener.collectedMethods.size)
//        assertEquals("foo", methodListener.collectedMethods[0].name)
//        assertEquals("int", methodListener.collectedMethods[0].returnType.name)
//        assertEquals(3, methodListener.collectedMethods[0].parameters.size)
//        assertEquals("int", methodListener.collectedMethods[0].parameters[0].type)
//        assertEquals("x", methodListener.collectedMethods[0].parameters[0].name)
//        assertEquals("int", methodListener.collectedMethods[0].parameters[1].type)
//        assertEquals("y", methodListener.collectedMethods[0].parameters[1].name)
//        assertEquals(1, methodListener.collectedMethods[0].modifiers.size)
//        assertEquals("public", methodListener.collectedMethods[0].modifiers[0].name)
    }
    
}