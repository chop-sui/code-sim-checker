import com.fasterxml.jackson.databind.ObjectMapper
import com.sparrow.sptracer.staticdiff.actions.Diff
import com.sparrow.sptracer.staticdiff.io.ActionsIoUtils
import com.sparrow.sptracer.staticdiff.matchers.DiffProperties
import com.sparrow.sptracer.staticdiff.util.ZipUtil
import java.io.File
import java.io.FileInputStream
import java.util.zip.ZipInputStream

fun main(args: Array<String>) {
    // TODO: The running time needs to be checked precisely using JMH
    var start = System.nanoTime()

    val simpleJavaDiff = SimpleJavaDiff(
        "D:\\Dev\\static-diff-ziptests\\ZipTests\\RxJava-3.1.2.zip",
        "D:\\Dev\\static-diff-ziptests\\ZipTests\\RxJava-3.1.3.zip"
    )

    simpleJavaDiff.generateDiff()

    var finish = System.nanoTime()
    var timeElapsed = finish - start
    println(timeElapsed)

    // SPTracer Diff test
    val diffs: List<Diff>
    val properties = DiffProperties()

    // FIXME: sptracer diff module returns empty list when there is no package declaration!

    start = System.nanoTime()

    val srcMap: MutableMap<String, String> = ZipUtil.getZipFiles(ZipInputStream(FileInputStream("D:\\Dev\\static-diff-ziptests\\ZipTests\\RxJava-3.1.2.zip")))
    val dstMap: MutableMap<String, String> = ZipUtil.getZipFiles(ZipInputStream(FileInputStream("D:\\Dev\\static-diff-ziptests\\ZipTests\\RxJava-3.1.3.zip")))

    diffs = Diff.computeForZipFile(srcMap, dstMap, null, null, properties)

    val serializer: ActionsIoUtils.ActionSerializerForZip = ActionsIoUtils.toJsonForZip(diffs)

    serializer.writeTo(System.out)

    finish = System.nanoTime()
    timeElapsed = finish - start
    val mapper = ObjectMapper()
    mapper.writeValue(File("test.json"), timeElapsed)
}