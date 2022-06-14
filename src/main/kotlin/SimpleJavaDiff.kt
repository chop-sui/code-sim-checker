import com.sparrow.sptracer.staticdiff.util.ZipUtil
import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream
import org.antlr.v4.runtime.tree.ParseTree
import org.antlr.v4.runtime.tree.ParseTreeWalker
import java.io.FileInputStream
import java.util.zip.ZipInputStream


class SimpleJavaDiff(srcPath: String, dstPath: String) {

    var srcMap: MutableMap<String, String>? = null
    var dstMap: MutableMap<String, String>? = null
    private lateinit var java8Lexer: JavaLexer
    private lateinit var tokens: CommonTokenStream
    private lateinit var java8Parser: JavaParser
    private val walker = ParseTreeWalker()
    private lateinit var methodListener: JavaMethodListener

    init {
        srcMap = ZipUtil.getZipFiles(ZipInputStream(FileInputStream(srcPath)))
        dstMap = ZipUtil.getZipFiles(ZipInputStream(FileInputStream(dstPath)))
    }

    // FIXME: Currently, only finds the method names
    fun generateDiff(): List<String?> {

        val diffMethods: MutableList<String?> = mutableListOf()
        var srcParseTree: ParseTree
        var dstParseTree: ParseTree
        var srcMethods: MutableMap<String?, String?>
        var dstMethods: MutableMap<String?, String?>

        if (srcMap == null || dstMap == null) {
            println("Error loading source code content")
        } else {
            srcMap?.keys?.forEach { it ->
                val dstKey = "RxJava-3.1.3/" + it.substringAfter("/")
                if (dstMap?.contains(dstKey) == true) { // FIXME: Just for testing.. hardcoded "mockito-4.2.0"
                    srcParseTree = generateParseTree(srcMap!![it]!!)
                    dstParseTree = generateParseTree(dstMap!![dstKey]!!)

                    println("Parsed!")

                    srcMethods = walkParseTree(srcParseTree)
                    dstMethods = walkParseTree(dstParseTree)

                    println("Walked!")

                    srcMethods.keys.forEach { key ->
                        if (dstMethods.keys.contains(key)) {
                            if (srcMethods[key] != dstMethods[key]) {
                                println(key)
                                println("srcMethod: ${srcMethods[key]}")
                                println("dstMethod: ${dstMethods[key]}")
                                diffMethods.add(key)
                            }
                        } else {
                            println(key)
                            diffMethods.add(key)
                        }
                    }

                    println("Diffed!")
                }
            }
        }
        return diffMethods
    }

    private fun generateParseTree(classContent: String): ParseTree {
        java8Lexer = JavaLexer(CharStreams.fromString(classContent))
        tokens = CommonTokenStream(java8Lexer)
        java8Parser = JavaParser(tokens)
        return java8Parser.compilationUnit()
    }

    private fun walkParseTree(tree: ParseTree): MutableMap<String?, String?> {
        methodListener = JavaMethodListener()
        walker.walk(methodListener, tree)
        return methodListener.methods
    }

}