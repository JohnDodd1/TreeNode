package tomsksoft.jd.managertree.app

import tomsksoft.jd.managertree.tree.Company
import tomsksoft.jd.managertree.tree.Component
import tomsksoft.jd.managertree.tree.Node
import tomsksoft.jd.managertree.tree.Leaf
import java.util.*
import java.util.concurrent.atomic.AtomicInteger

class DataProvider private constructor() {
    companion object {
        val company by lazy { createRandomCompany() }
        val atomic by lazy { AtomicInteger() }
    }
}

private fun createRandomCompany(): Company {
    val random = Random()
    val top = mutableListOf<Component>()

    for (i in 1..(2 + random.nextInt(2))) {
        val m1 = Node("M - $i")
        top.add(m1)

        for (j in 1..(1 + random.nextInt(3))) {
            val m2 = Node("M[$i,$j]")
            m1.add(m2)

            for (k in 1..(2 + random.nextInt(3))) {
                m2.add(Leaf("L[$i,$j,$k]"))
            }
        }
    }

    return Company().apply { headComponent.addAll(top) }
}



private fun createComposite(name: String, f: () -> Component): Node {
    val composite = Node(name)
    for (i in 1..3) {
        composite.add(f())
    }
    return composite
}