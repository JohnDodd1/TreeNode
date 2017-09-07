package tomsksoft.jd.managertree.app

import tomsksoft.jd.managertree.tree.Company
import tomsksoft.jd.managertree.tree.Component
import tomsksoft.jd.managertree.tree.LeafComponent
import tomsksoft.jd.managertree.tree.NodeComponent
import java.util.*
import java.util.concurrent.atomic.AtomicInteger
import kotlin.concurrent.timer

class DataProvider private constructor() {
    companion object {
        private val random = Random()
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

            while (iterator.hasNext() && random.nextBoolean()) {
                next = iterator.next()
            }
            if (next is NodeComponent) {
                next.add(if (random.nextBoolean()) LeafComponent("L - ${random.nextInt(1000)}")
                else NodeComponent("M - ${random.nextInt(1000)}"))
            }
        }

        private fun removeRandomComponent() {
            val iterator = company.headComponent.iterator()
            var next: Component? = null

            while (iterator.hasNext() && random.nextBoolean()) {
                next = iterator.next()
            }
            if (next is NodeComponent && next.hasChildren()) {
                next.remove(next.getChildren().first())
            }

        }

        private fun createRandomCompany(): Company {
            val top = mutableListOf<Component>()
            for (i in 1..3)
                top.add(NodeComponent("M - $i"))

            /*for (i in 1..(2 + random.nextInt(2))) {
                val m1 = NodeComponent("M - $i")
                top.add(m1)

                for (j in 1..(1 + random.nextInt(3))) {
                    val m2 = NodeComponent("M[$i,$j]")
                    m1.add(m2)

                    for (k in 1..(2 + random.nextInt(3))) {
                        m2.add(LeafComponent("L[$i,$j,$k]"))
                    }
                }
            }*/

            return Company().apply { headComponent.addAll(top) }
        }
    }
}