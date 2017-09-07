package tomsksoft.jd.managertree.tree

import com.github.vivchar.rendererrecyclerviewadapter.ItemModel
import tomsksoft.jd.managertree.app.DataProvider
import tomsksoft.jd.managertree.delegated_adapter.AdapterConstants.LEAF
import tomsksoft.jd.managertree.delegated_adapter.AdapterConstants.NODE
import java.util.*

data class NodeComponent(override val name: String,
                         override val id: Int = DataProvider.atomic.incrementAndGet(),
                         private val components: ArrayList<Component> = ArrayList()) : Component {
    override fun getChildren() = components

    override fun iterator() = CompositeIterator(components.iterator())

    fun add(component: Component): NodeComponent {
        components.add(component)
        return this
    }

    fun remove(component: Component) = components.remove(component)

    fun addAll(components: List<Component>): NodeComponent {
        this.components.addAll(components)
        return this
    }

    override fun hasChildren() = !components.isEmpty()
}


data class LeafComponent(override val name: String, override val id: Int = DataProvider.atomic.incrementAndGet()) : Component {

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


class Company(val headComponent: NodeComponent = NodeComponent("Главный менеджер", DataProvider.atomic.incrementAndGet())) {
    private var callback: (() -> Unit)? = null

    fun subscribe(callback: () -> Unit) {
        this.callback = callback
    }

    fun dispose() {
        callback = null
    }

    fun next() {
        callback?.invoke()
    }
}


data class NodeModel(val name: String,
                     val level: Int = 0,
                     val id: Int,
                     val hasChildren: Boolean = false,
                     val isExpanded: Boolean) : BaseModel(id) {
    override fun getType() = NODE

    companion object {
        val TYPE = NODE
    }
}

data class LeafModel(val name: String,
                     val level: Int = 0, val id: Int) : BaseModel(id) {
    override fun getType() = LEAF

    companion object {
        val TYPE = LEAF
    }

}

abstract class BaseModel(val baseId: Int) : ItemModel

interface Component : Iterable<Component> {
    val name: String

    val id: Int

    fun hasChildren(): Boolean

    fun getChildren(): MutableList<Component>
}