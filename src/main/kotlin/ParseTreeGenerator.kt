import org.snt.inmemantlr.GenericParser
import org.snt.inmemantlr.listener.DefaultTreeListener
import org.snt.inmemantlr.tree.ParseTree
import org.snt.inmemantlr.utils.FileUtils
import java.io.File

class ParseTreeGenerator {
    
    fun parse(gFilePath: String, scFilePath: String): ParseTree {
        val gFile = File(gFilePath)
        val gp = GenericParser(gFile)
        val sc = FileUtils.loadFileContent(scFilePath)
    
        val dList = DefaultTreeListener()
    
        gp.setListener(dList)
        gp.compile()
    
        gp.parse(sc, "compilationUnit", GenericParser.CaseSensitiveType.NONE)
    
        return dList.parseTree
    }
}