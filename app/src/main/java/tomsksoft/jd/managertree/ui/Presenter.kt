package tomsksoft.jd.managertree.ui

import android.util.SparseBooleanArray
import com.github.vivchar.rendererrecyclerviewadapter.ItemModel
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import tomsksoft.jd.managertree.app.DataProvider
import tomsksoft.jd.managertree.app.log
import tomsksoft.jd.managertree.tree.Component
import tomsksoft.jd.managertree.tree.LeafModel
import tomsksoft.jd.managertree.tree.NodeComponent
import tomsksoft.jd.managertree.tree.NodeModel

class Presenter(private val view: IView) {
    private val expanded = SparseBooleanArray()
    private val head = DataProvider.company.headComponent

    fun onCreate() {
        "onCreate".log()
        DataProvider.company.subscribe { updateModels() }
    }

    private fun updateModels() {
        doAsync {
            val models = mutableListOf<ItemModel>()
            getLevel(head) { component, level ->
                if (component is NodeComponent)
                    models.add(createNodeModel(component, level))
                else
                    models.add(createLeafModel(component, level))
                return@getLevel expanded[component.id]
            }

            uiThread { view.updateAdapter(models) }
        }

    }

    private fun getLevel(component: Component, level: Int = 0, callback: (Component, Int) -> Boolean) {
        if (!callback(component, level))
            return
        if (component.hasChildren()) {
            component.getChildren().forEach { getLevel(it, level + 1, callback) }
        }
    }

    private fun createLeafModel(component: Component, level: Int) = LeafModel(component.name, level, component.id)

    private fun createNodeModel(component: Component, level: Int)
            = NodeModel(component.name, level, component.id, component.hasChildren(),
            if (component.hasChildren()) expanded[component.id] else false)


    fun onNodeClicked(model: NodeModel) {
        expanded.put(model.id, !expanded[model.id])
        updateModels()
    }

    fun onLeafClicked(model: LeafModel) {

    }

    fun onStop() {
        DataProvider.company.dispose()
        val cursor = Cursor()

    }


    class Cursor{
        private val q = 2
    }
}