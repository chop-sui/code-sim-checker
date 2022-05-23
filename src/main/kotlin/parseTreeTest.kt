import org.snt.inmemantlr.GenericParserToGo
import org.snt.inmemantlr.tree.ParseTree

import java.io.File

fun parse(gFile: File, cFile: File): ParseTree {
    return GenericParserToGo(gFile).parse(cFile, "compilationUnit")
}