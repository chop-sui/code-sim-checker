import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream
import org.antlr.v4.runtime.tree.ParseTree
import org.antlr.v4.runtime.tree.ParseTreeWalker
import org.snt.inmemantlr.utils.FileUtils


fun main(args: Array<String>) {
    // #1. Testing inmemantlr API
    var start = System.nanoTime()
    val ptg = ParseTreeGenerator()
    val pt = ptg.parse(
        "D:\\Dev\\code-sim-checker\\src\\main\\antlr4\\Java8.g4",
        "D:\\Dev\\code-sim-checker\\src\\main\\resources\\SimpleBefore.java"
    )

    // Pretty print json of ParseTree
    val mapper = ObjectMapper()
    mapper.enable(SerializationFeature.INDENT_OUTPUT)
    val jsonObject = mapper.readValue(pt.toJson(), Object::class.java)
    var prettyJsonString = mapper.writeValueAsString(jsonObject)
    //println(prettyJsonString)

    // Print parse tree nodes
    val nodes = pt.nodes
    nodes.forEach { it ->
        if (it.rule.equals("methodDeclaration")) {
            println(it.children)
            it.children.forEach { child ->
                if (child.rule.equals("methodHeader")) {
                    println(child.children)
                }
            }
        }
    }
    var finish = System.nanoTime()
    var timeElapsed = finish - start
    println(timeElapsed)
    
    start = System.nanoTime()

    var javaClassContent = FileUtils.loadFileContent("D:\\Dev\\code-sim-checker\\src\\main\\resources\\SimpleBefore.java")
    var java8Lexer = Java8Lexer(CharStreams.fromString(javaClassContent))
    var tokens = CommonTokenStream(java8Lexer)
    var java8Parser = Java8Parser(tokens)
    val treeBefore: ParseTree = java8Parser.compilationUnit()
    val walker = ParseTreeWalker()
    var methodListener = JavaMethodListener()
    
    walker.walk(methodListener, treeBefore)
    val methodsBefore = methodListener.methods
    
    javaClassContent = FileUtils.loadFileContent("D:\\Dev\\code-sim-checker\\src\\main\\resources\\SimpleAfter.java")
    java8Lexer = Java8Lexer((CharStreams.fromString(javaClassContent)))
    tokens = CommonTokenStream(java8Lexer)
    java8Parser = Java8Parser(tokens)
    val treeAfter: ParseTree = java8Parser.compilationUnit()
    methodListener = JavaMethodListener()
    
    walker.walk(methodListener, treeAfter)
    val methodsAfter = methodListener.methods
    
    methodsBefore.keys.forEach {
        if (methodsAfter.keys.contains(it)) {
            if (methodsBefore[it] != methodsAfter[it]) {
                println(it)
            }
        }
    }
    
    finish = System.nanoTime()
    timeElapsed = finish - start
    print(timeElapsed)
    
}