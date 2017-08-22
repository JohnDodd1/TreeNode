package tomsksoft.jd.managertree.app

import tomsksoft.jd.managertree.tree.Company
import tomsksoft.jd.managertree.tree.Component
import tomsksoft.jd.managertree.tree.ManagerComposite
import tomsksoft.jd.managertree.tree.ManagerLeaf
import java.util.*

class DataProvider private constructor(){
    companion object {
        val company by lazy { createRandomCompany() }
    }
}

private fun createRandomCompany(): Company {
    val random = Random()
    val composites = mutableListOf<Component>()
    for (i in 1..(2 + random.nextInt(2))) {
        val element = ManagerComposite("M$i")
        for (j in 1..(3 + random.nextInt(2)))
            element.add(ManagerLeaf("L[$i,$j]"))
        composites.add(element)
    }

    return Company().apply { headComponent.addAll(composites) }
}