package tomsksoft.jd.managertree.delegated_adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.github.vivchar.rendererrecyclerviewadapter.ViewRenderer
import tomsksoft.jd.managertree.R
import tomsksoft.jd.managertree.app.inflate
import tomsksoft.jd.managertree.tree.LeafModel
import tomsksoft.jd.managertree.tree.NodeModel

class NodeRenderer(viewType: Int, context: Context, private val listener: Listener) : ViewRenderer<NodeModel, NodeRenderer.NodeHolder>(viewType, context) {
    override fun createViewHolder(parent: ViewGroup?) = NodeHolder(parent!!.inflate(R.layout.line_node))

    override fun bindView(item: NodeModel, holder: NodeHolder, payloads: MutableList<Any?>) {
        if (payloads.isEmpty()) {
            super.bindView(item, holder, payloads)
        } else {
            holder.image.apply {
                rotation = 0f
                startAnimation(if (item.isExpanded) createAnimator(0f, 90f) else createAnimator(90f, 0f))
                visibility = if (item.hasChildren) View.VISIBLE else View.INVISIBLE
            }
        }
    }

    override fun bindView(model: NodeModel, holder: NodeHolder) {
        holder.apply {
            itemView.layoutParams = RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                    .apply { setMargins(64 * model.level, 0, 0, 0) }
            name.text = model.name
            image.rotation = if (model.isExpanded) 90f else 0f
            image.visibility = if (model.hasChildren) View.VISIBLE else View.INVISIBLE
            itemView.setOnClickListener { listener.onItemClicked(model) }
        }
    }

    class NodeHolder(root: View) : RecyclerView.ViewHolder(root) {
        val name: TextView = root.findViewById(R.id.leaf_text_view)
        val image: ImageView = root.findViewById(R.id.leaf_image_view)
    }

    interface Listener {
        fun onItemClicked(model: NodeModel)
    }

}

class LeafRenderer(viewType: Int, context: Context, private val listener: Listener) : ViewRenderer<LeafModel, LeafRenderer.LeafHolder>(viewType, context) {
    override fun createViewHolder(parent: ViewGroup?) = LeafHolder(parent!!.inflate(R.layout.line_leaf))

    override fun bindView(model: LeafModel, holder: LeafHolder) {
        holder.apply {
            itemView.layoutParams = RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
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

fun createAnimator(from: Float, to: Float)
        = RotateAnimation(from, to, Animation.RELATIVE_TO_SELF,
        0.5f, Animation.RELATIVE_TO_SELF, 0.5f).apply {
    fillAfter = true
    duration = 400

}
