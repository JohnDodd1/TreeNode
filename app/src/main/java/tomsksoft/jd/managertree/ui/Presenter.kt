package tomsksoft.jd.managertree.ui

import tomsksoft.jd.managertree.tree.Company
import tomsksoft.jd.managertree.tree.Component
import tomsksoft.jd.managertree.tree.ManagerComposite
import tomsksoft.jd.managertree.tree.ManagerLeaf
import java.util.*

class Presenter(val view: IView) {
    val random = Random()

    fun onCreate() {
        val company = Company()
        val composites = mutableListOf<Component>()
        for (i in 1..(2 + random.nextInt(2))) {
            val element = ManagerComposite("M$i")
            for (j in 1..(3 + random.nextInt(2)))
                element.add(ManagerLeaf("L[$i,$j]"))
            composites.add(element)
        }

        company.headComponent.addAll(composites)

        val iterator = company.headComponent.iterator()
        while (iterator.hasNext()) {
            iterator.next().log()
        }
    }
}