import model.Method
import model.Parameter


class JavaMethodListener : JavaParserBaseListener() {
    
    val collectedMethods: MutableList<Method> = arrayListOf()
    val methods: MutableMap<String?, String?> = mutableMapOf()
    
    override fun enterMethodDeclaration(ctx: JavaParser.MethodDeclarationContext?) {
        
        val methodName = ctx?.identifier()?.text
        val returnType = ctx?.typeTypeOrVoid()?.text
        val parameters = mutableListOf<Parameter>()
    
        ctx?.formalParameters()?.formalParameterList()?.formalParameter()?.forEach {
            val param = Parameter(it.typeType().text, it.variableDeclaratorId().identifier().text)
            parameters.add(param)
        }
    
        val methodBody: String? = ctx?.methodBody()?.text
        
        methods[methodName] = methodBody

    }

}