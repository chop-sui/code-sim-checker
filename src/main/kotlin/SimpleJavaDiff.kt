import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream
import org.antlr.v4.runtime.tree.ParseTree
import org.antlr.v4.runtime.tree.ParseTreeWalker


class SimpleJavaDiff(srcPath: String, dstPath: String) {
    
    private val srcClassContent = loadFileContent(srcPath)
    private val dstClassContent = loadFileContent(dstPath)
    private lateinit var java8Lexer: Java8Lexer
    private lateinit var tokens: CommonTokenStream
    private lateinit var java8Parser: Java8Parser
    private val walker = ParseTreeWalker()
    private lateinit var methodListener: JavaMethodListener
    
    // FIXME: Currently, only finds the method names
    fun generateDiff(): List<String?> {
        
        val diffMethods: MutableList<String?> = mutableListOf()
        
        if (srcClassContent == null || dstClassContent == null) {
            println("Error loading source code content")
        } else {
            val srcParseTree = generateParseTree(srcClassContent)
            val dstParseTree = generateParseTree(dstClassContent)
            val srcMethods = walkParseTree(srcParseTree)
            val dstMethods = walkParseTree(dstParseTree)
            
            srcMethods.keys.forEach {
                if (dstMethods.keys.contains(it)) {
                    if (srcMethods[it] != dstMethods[it]) {
                        println(it)
                        diffMethods.add(it)
                    }
                } else {
                    println(it)
                    diffMethods.add(it)
                }
            }
        }
        return diffMethods
    }
    
    private fun generateParseTree(classContent: String): ParseTree {
        java8Lexer = Java8Lexer(CharStreams.fromString(classContent))
        tokens = CommonTokenStream(java8Lexer)
        java8Parser = Java8Parser(tokens)
        return java8Parser.compilationUnit()
    }
    
    private fun walkParseTree(tree: ParseTree): MutableMap<String?, String> {
        methodListener = JavaMethodListener()
        walker.walk(methodListener, tree)
        return methodListener.methods
    }
    
}