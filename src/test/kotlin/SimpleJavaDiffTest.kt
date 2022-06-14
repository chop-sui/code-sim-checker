//import org.junit.jupiter.api.Assertions.*
//import org.junit.jupiter.api.Test
//
//internal class SimpleJavaDiffTest {
//
//    private lateinit var simpleJavaDiff: SimpleJavaDiff
//    private lateinit var srcPath: String
//    private lateinit var dstPath: String
//
//    private fun setup(srcPath: String, dstPath: String) {
//        simpleJavaDiff = SimpleJavaDiff(srcPath, dstPath)
//    }
//
//    @Test
//    fun `should diff for body changes`() {
//        setup(
//            "D:\\Dev\\code-sim-checker\\src\\main\\resources\\SimpleMethodBodyChangeBefore.java",
//            "D:\\Dev\\code-sim-checker\\src\\main\\resources\\SimpleMethodBodyChangeBefore.java"
//        )
//        val expected = listOf("foo")
//        val actual = simpleJavaDiff.generateDiff()
//
//        assertEquals(expected, actual)
//
//    }
//
//    @Test
//    fun `should diff for new method addition`() {
//        setup(
//            "D:\\Dev\\code-sim-checker\\src\\main\\resources\\NewMethodBefore.java",
//            "D:\\Dev\\code-sim-checker\\src\\main\\resources\\NewMethodAfter.java"
//        )
//        val expected = emptyList<String?>() // TODO: This should actually generate data with class FQN with empty methods list
//        val actual = simpleJavaDiff.generateDiff()
//
//        assertEquals(expected, actual)
//    }
//
//    @Test
//    fun `should diff for method removal`() {
//        setup(
//            "D:\\Dev\\code-sim-checker\\src\\main\\resources\\RemoveMethodBefore.java",
//            "D:\\Dev\\code-sim-checker\\src\\main\\resources\\RemoveMethodAfter.java"
//        )
//
//        val expected = listOf("bar")
//        val actual = simpleJavaDiff.generateDiff()
//
//        assertEquals(expected, actual)
//    }
//
//}