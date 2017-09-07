package tomsksoft.jd.managertree.tree.components

import tomsksoft.jd.managertree.app.DataProvider

data class LeafComponent(override val name: String) : Component {
    override val id = DataProvider.atomic.incrementAndGet()

    override fun getChildren() = throw UnsupportedOperationException("Leafs have no derivatives")

    override fun hasChildren() = false

    private val iterator by lazy {
        object : Iterator<Component> {
            override fun next() = throw UnsupportedOperationException("Leafs have no derivatives")
            override fun hasNext() = false
        }
    }

    override fun iterator() = iterator
}
