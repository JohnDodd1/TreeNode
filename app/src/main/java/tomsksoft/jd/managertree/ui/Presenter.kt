package tomsksoft.jd.managertree.ui

import android.util.SparseBooleanArray
import tomsksoft.jd.managertree.R
import tomsksoft.jd.managertree.app.DataProvider
import tomsksoft.jd.managertree.tree.Component
import tomsksoft.jd.managertree.tree.NodeModel

class Presenter(private val view: IView) {
    private val expanded = SparseBooleanArray()
    private val head = DataProvider.company.headComponent

    fun onCreate() {
        updateModels()
    }

    private fun updateModels() {
        val models = mutableListOf<NodeModel>()
        getLevel(head) { component, level ->
            models.add(createModel(component, level))
            return@getLevel expanded[component.id]
        }
        view.updateAdapter(models)
    }

    private fun getLevel(component: Component, level: Int = 0, callback: (Component, Int) -> Boolean) {
        if (!callback(component, level))
            return
        if (component.hasChildren()) {
            component.getChildren().forEach { getLevel(it, level + 1, callback) }
        }
    }

    fun onItemClicked(model: NodeModel) {
        expanded.put(model.id, !expanded[model.id])
        updateModels()
    }

    private fun createModel(component: Component, level: Int): NodeModel {
        val res = if (!component.hasChildren())
            R.drawable.ic_person_black_24dp
        else R.drawable.ic_chevron_right_black_24dp

        return NodeModel(component.name, level, component.id, component.hasChildren(), res,
                if (component.hasChildren()) expanded[component.id] else false)
    }

}