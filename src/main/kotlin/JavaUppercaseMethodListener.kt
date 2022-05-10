import org.antlr.v4.runtime.tree.TerminalNode


class JavaUppercaseMethodListener : Java8BaseListener() {
    val errors: MutableList<String> = arrayListOf()
    
    override fun enterMethodDeclarator(ctx: Java8Parser.MethodDeclaratorContext?) {
        val node: TerminalNode? = ctx?.Identifier()
        val methodName: String? = node?.text
        val firstChar: Char? = methodName?.get(0)
        
        if (firstChar?.isUpperCase() == true) {
            val error: String = String.format("Method $methodName is uppercased!")
            errors.add(error)
        }
    }
}