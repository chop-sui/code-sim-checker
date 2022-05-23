import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import org.antlr.v4.runtime.ParserRuleContext
import org.snt.inmemantlr.GenericParser
import org.snt.inmemantlr.listener.DefaultTreeListener
import org.snt.inmemantlr.tree.ParseTree
import org.snt.inmemantlr.utils.FileUtils
import java.io.File


fun main(args: Array<String>) {
    
    val f = File("D:\\Dev\\code-sim-checker\\src\\main\\antlr4\\Java8.g4")
    val gp = GenericParser(f)
    val sourceCode = FileUtils.loadFileContent("D:\\Dev\\code-sim-checker\\src\\main\\resources\\ParseTest.java")
    
    val dList = DefaultTreeListener()
    
    gp.setListener(dList)
    gp.compile()
    
    gp.parse(sourceCode, "compilationUnit", GenericParser.CaseSensitiveType.NONE)
    
    val pt: ParseTree = dList.parseTree
    
    // Pretty print json of ParseTree
    val mapper = ObjectMapper()
    mapper.enable(SerializationFeature.INDENT_OUTPUT)
    val jsonObject = mapper.readValue(pt.toJson(), Object::class.java)
    var prettyJsonString = mapper.writeValueAsString(jsonObject)
    //println(prettyJsonString)
    
    // Print parse tree nodes
    val nodes = pt.nodes
    nodes.forEach {
        println(it)
    }

}