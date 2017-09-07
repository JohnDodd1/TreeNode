package tomsksoft.jd.managertree.ui.main

import android.util.SparseBooleanArray
import com.github.vivchar.rendererrecyclerviewadapter.ItemModel
import tomsksoft.jd.managertree.app.DataProvider
import tomsksoft.jd.managertree.models.LeafModel
import tomsksoft.jd.managertree.models.NodeModel
import tomsksoft.jd.managertree.tree.components.Component
import tomsksoft.jd.managertree.tree.components.NodeComponent
import kotlin.concurrent.thread

class Presenter(private val view: IView) {
    private val expanded by lazy { SparseBooleanArray() }

    private fun updateModels() {
        thread(isDaemon = true) {
            val models = mutableListOf<ItemModel>()
            getLevel(DataProvider.company.headComponent) { component, level ->
                if (component is NodeComponent)
                    models.add(createNodeModel(component, level))
                else
                    models.add(createLeafModel(component, level))
                return@getLevel expanded[component.id]
            }
            view.updateAdapter(models)
        }
    }

    private fun getLevel(component: Component, level: Int = 0, isExpanded: (Component, Int) -> Boolean) {
        if (!isExpanded(component, level))
            return
        if (component.hasChildren()) {
            component.getChildren().forEach { getLevel(it, level + 1, isExpanded) }
        }
    }

    private fun createLeafModel(component: Component, level: Int) = LeafModel(component.name, level, component.id)

    private fun createNodeModel(component: Component, level: Int)
            = NodeModel(component.name, level, component.id, component.hasChildren(), expanded[component.id])

    fun onNodeClicked(model: NodeModel) {
        expanded.put(model.id, !expanded[model.id])
        updateModels()
    }

    fun onLeafClicked(model: LeafModel) = Unit

    fun onStart() = DataProvider.company.subscribe { updateModels() }

    fun onStop() = DataProvider.company.dispose()

}
