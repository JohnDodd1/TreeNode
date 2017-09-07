package tomsksoft.jd.managertree.delegated_adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import tomsksoft.jd.managertree.R
import tomsksoft.jd.managertree.app.inflate

class Adapter : ViewTypeDelegateAdapter {
    private val models = arrayListOf<ViewType>()

    override fun onCreateViewHolder(parent: ViewGroup) = Holder(parent.inflate(R.layout.line_node))

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType) {

    }
}

class Holder(root: View) : RecyclerView.ViewHolder(root)

interface ViewTypeDelegateAdapter {
    fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder

    fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType)
}