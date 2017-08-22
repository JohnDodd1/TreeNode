package tomsksoft.jd.managertree.tree

import java.util.*

class CompositeIterator(iterator: Iterator<Component>) : Iterator<Component> {
    private val stack = Stack<Iterator<Component>>()

    init {
        stack.push(iterator)
    }

    override fun hasNext(): Boolean {
        return if (stack.isEmpty()) false else {
            if (stack.peek().hasNext()) true
            else {
                stack.pop()
                hasNext()
            }
        }
    }

    override fun next(): Component {
        return if (!hasNext()) throw UnsupportedOperationException("Wrong next call") else {
            val component = stack.peek().next()
            if (component is ManagerComposite) {
                stack.push(component.iterator())
            }
            component
        }
    }

}