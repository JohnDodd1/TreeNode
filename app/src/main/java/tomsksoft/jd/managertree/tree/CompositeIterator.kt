package tomsksoft.jd.managertree.tree

import tomsksoft.jd.managertree.app.log
import java.util.*

class CompositeIterator(iterator: Iterator<Component>) : Iterator<Component> {
    private val stack = Stack<Iterator<Component>>()

    init {
        stack.push(iterator)
    }

    override fun next(): Component {
        return if (hasNext()) {
            val component = stack.peek().next()
            if (component.hasChildren()) {
                stack.push(component.iterator())
            }
            component
        } else throw NoSuchElementException()
    }

    override fun hasNext(): Boolean {
        return if (stack.isEmpty()) false else {
            if (!stack.peek().hasNext()) {
                stack.pop()
                return hasNext()
            } else return true
        }
    }

    fun getLevel() = stack.size


}