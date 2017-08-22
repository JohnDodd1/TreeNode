package tomsksoft.jd.managertree.tree

data class ManagerComposite(override val name: String,
                            private val components: ArrayList<Component> = ArrayList<Component>()
                            ) : Component {

    override fun iterator() = CompositeIterator(components.iterator())

    fun add(component: Component): ManagerComposite {
        components.add(component)
        return this
    }

    fun addAll(components: List<Component>): ManagerComposite {
        this.components.addAll(components)
        return this
    }
}

data class ManagerLeaf(override val name: String) : Component {

    override fun iterator() = object : Iterator<Component> {
        override fun next() = throw UnsupportedOperationException("Leafs have no derivatives")
        override fun hasNext() = false
    }
}

data class Company(val headComponent: ManagerComposite = ManagerComposite("Главный менеджер"))

interface Component : Iterable<Component>{
    val name: String

    fun log() = name.log()
}