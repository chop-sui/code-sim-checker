package model

data class Method(
    var name: String = "",
    var modifiers: List<Modifier> = emptyList(),
    val returnType: ReturnType = ReturnType(""),
    var parameters: MutableList<Parameter> = mutableListOf(),
)
