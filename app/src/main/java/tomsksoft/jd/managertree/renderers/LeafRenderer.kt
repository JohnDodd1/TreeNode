package tomsksoft.jd.managertree.renderers

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.RelativeLayout.LayoutParams
import android.widget.TextView
import com.github.vivchar.rendererrecyclerviewadapter.ViewRenderer
import tomsksoft.jd.managertree.R
import tomsksoft.jd.managertree.app.inflate
import tomsksoft.jd.managertree.models.LeafModel

class LeafRenderer(viewType: Int, context: Context, private val listener: Listener) : ViewRenderer<LeafModel, LeafRenderer.LeafHolder>(viewType, context) {
    override fun createViewHolder(parent: ViewGroup?) = LeafHolder(parent!!.inflate(R.layout.line_leaf))

    override fun bindView(model: LeafModel, holder: LeafHolder) {
        holder.apply {
            itemView.layoutParams = LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
                    .apply { setMargins(96 * model.level, 0, 0, 0) }
            name.text = model.name
            itemView.setOnClickListener { listener.onItemClicked(model) }
        }
    }

    class LeafHolder(root: View) : RecyclerView.ViewHolder(root) {
        val name: TextView = root.findViewById(R.id.leaf_text_view)
    }

    interface Listener {
        fun onItemClicked(model: LeafModel)
    }
}
