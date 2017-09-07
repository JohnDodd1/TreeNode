package tomsksoft.jd.managertree.tree.components


interface Component : Iterable<Component> {
    val name: String

    val id: Int

    fun hasChildren(): Boolean

    fun getChildren(): MutableList<Component>
}