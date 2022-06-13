import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream
import org.antlr.v4.runtime.tree.ParseTree
import org.antlr.v4.runtime.tree.ParseTreeWalker
import org.snt.inmemantlr.utils.FileUtils


fun main(args: Array<String>) {
    // TODO: The running time comparison below needs to be done precisely using JMH
    // #1. Testing inmemantlr API
    var start = System.nanoTime()
    val ptg = ParseTreeGenerator()
    val pt = ptg.parse(
        "D:\\Dev\\code-sim-checker\\src\\main\\antlr4\\Java8.g4",
        "D:\\Dev\\code-sim-checker\\src\\main\\resources\\SimpleMethodBodyChangeBefore.java"
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
    
    // #2. Testing manual antlr implementation
    
    start = System.nanoTime()

    val simpleJavaDiff = SimpleJavaDiff(
        "D:\\Dev\\code-sim-checker\\src\\main\\resources\\SimpleMethodBodyChangeBefore.java",
        "D:\\Dev\\code-sim-checker\\src\\main\\resources\\SimpleMethodBodyChangeAfter.java"
    )
    
    simpleJavaDiff.generateDiff()
    
    finish = System.nanoTime()
    timeElapsed = finish - start
    print(timeElapsed)
    
}