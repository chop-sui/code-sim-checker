import model.Method
import model.Modifier
import model.Parameter


class JavaMethodListener : Java8BaseListener() {
    
    val collectedMethods: MutableList<Method> = arrayListOf()
    
    override fun enterMethodDeclaration(ctx: Java8Parser.MethodDeclarationContext?) {
        
        val method = Method()
        
        method.returnType.name = ctx?.methodHeader()?.result()?.text.toString()
        method.name = ctx?.methodHeader()?.methodDeclarator()?.Identifier()?.text.toString()
        method.modifiers = ctx?.methodModifier()?.map { it -> Modifier(it.text) }!!
        
        // FIXME: Is there a way to get all parameters at once, and NOT by getting the last parameter and the rest like below?
        method.parameters =
            (ctx.methodHeader()?.methodDeclarator()?.formalParameterList()?.formalParameters()?.formalParameter()?.map {
                Parameter(it.unannType().text, it.variableDeclaratorId().Identifier().text)
            } as MutableList<Parameter>?)!!
    
        ctx.methodHeader()?.methodDeclarator()?.formalParameterList()?.lastFormalParameter()?.formalParameter()?.let {
            method.parameters.add(Parameter(it.unannType().text, it.variableDeclaratorId().Identifier().text))
        }
        
        collectedMethods.add(method)
    }

}