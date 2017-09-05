package tomsksoft.jd.managertree.tree

import tomsksoft.jd.managertree.app.DataProvider
import tomsksoft.jd.managertree.delegated_adapter.AdapterConstants.LEAF
import tomsksoft.jd.managertree.delegated_adapter.AdapterConstants.NODE
import tomsksoft.jd.managertree.delegated_adapter.ViewType
import java.util.*

data class Node(override val name: String,
                override val id: Int = DataProvider.atomic.incrementAndGet(),
                private val components: ArrayList<Component> = ArrayList()) : Component {
    override fun getChildren() = components

    override fun iterator() = CompositeIterator(components.iterator())

    fun add(component: Component): Node {
        components.add(component)
        return this
    }

    fun addAll(components: List<Component>): Node {
        this.components.addAll(components)
        return this
    }

    override fun hasChildren() = !components.isEmpty()
}


data class Leaf(override val name: String, override val id: Int = DataProvider.atomic.incrementAndGet()) : Component {

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


class Company(val headComponent: Node = Node("Главный менеджер", DataProvider.atomic.incrementAndGet()))


data class NodeModel(val name: String,
                     val nodeLevel: Int = 0,
                     val id: Int,
                     val hasChildren: Boolean = false,
                     val imageResId: Int,
                     val isExpanded: Boolean) : ViewType {
    override fun getViewType() = NODE
}

data class LeafModel(val name: String,
                     val nodeLevel: Int = 0,
                     val id: Int,
                     val imageResId: Int) : ViewType {
    override fun getViewType() = LEAF
}

interface Component : Iterable<Component> {
    val name: String

    val id: Int

    fun hasChildren(): Boolean

    fun getChildren(): MutableList<Component>
}