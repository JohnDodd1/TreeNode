package tomsksoft.jd.managertree.tree.components

import tomsksoft.jd.managertree.app.DataProvider
import tomsksoft.jd.managertree.tree.CompositeIterator

data class NodeComponent(override val name: String) : Component {
    override val id: Int = DataProvider.atomic.incrementAndGet()

    private val components = arrayListOf<Component>()

    override fun getChildren() = components

    override fun iterator() = CompositeIterator(components.iterator())

    fun add(component: Component) = components.add(component)

    fun remove(component: Component) = components.remove(component)

    fun addAll(list: List<Component>) = components.addAll(list)

    override fun hasChildren() = !components.isEmpty()
}
