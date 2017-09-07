package tomsksoft.jd.managertree.app

import tomsksoft.jd.managertree.tree.components.Company
import tomsksoft.jd.managertree.tree.components.Component
import tomsksoft.jd.managertree.tree.components.LeafComponent
import tomsksoft.jd.managertree.tree.components.NodeComponent
import java.util.*
import java.util.concurrent.atomic.AtomicInteger
import kotlin.concurrent.timer

class DataProvider private constructor() {
    companion object {
        private val random by lazy { Random() }
        val company by lazy { createRandomCompany() }
        val atomic by lazy { AtomicInteger() }

        init {
            company.next()
            timer(period = 1000, daemon = true) {
                if (random.nextBoolean())
                    addRandomComponent()
                else removeRandomComponent()
                company.next()
            }
        }

        private fun addRandomComponent() {
            val iterator = company.headComponent.iterator()
            var next: Component? = null

            while (iterator.hasNext() && random.nextBoolean()) next = iterator.next()

            if (next is NodeComponent)
                next.add(if (random.nextBoolean()) createRandomLeaf() else createRandomNode())
        }

        private fun removeRandomComponent() {
            val iterator = company.headComponent.iterator()
            var next: Component? = null

            while (iterator.hasNext() && random.nextBoolean()) next = iterator.next()

            if (next is NodeComponent && next.hasChildren()) next.remove(next.getChildren().first())
        }

        private fun createRandomCompany() = Company().apply {
            headComponent.addAll((1..3).map { NodeComponent("M - $it") })
        }

        private fun createRandomNode() = NodeComponent("M - ${random.nextInt(1000)}")

        private fun createRandomLeaf() = LeafComponent("L - ${random.nextInt(1000)}")
    }
}