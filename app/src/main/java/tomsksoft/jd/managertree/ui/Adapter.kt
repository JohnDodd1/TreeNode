package tomsksoft.jd.managertree.ui

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import android.widget.ImageView
import android.widget.TextView
import com.github.vivchar.rendererrecyclerviewadapter.ViewRenderer
import tomsksoft.jd.managertree.R
import tomsksoft.jd.managertree.app.autoNotify
import tomsksoft.jd.managertree.app.inflate
import tomsksoft.jd.managertree.app.update
import tomsksoft.jd.managertree.tree.NodeModel

class Adapter(private val listener: Listener) : RecyclerView.Adapter<NodeHolder>() {
    private val models: MutableList<NodeModel> = arrayListOf()

    override fun onBindViewHolder(holder: NodeHolder, position: Int, payloads: MutableList<Any>) {
        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
        } else {
            if (payloads.firstOrNull() as Boolean) {
                val expanded = models[position].isExpanded
                holder.image.apply {
                    rotation = 0f
                    startAnimation(if (expanded) createAnimator(0f, 90f) else createAnimator(90f, 0f))
                }
            }
        }
    }

    override fun onBindViewHolder(holder: NodeHolder, position: Int) {
        holder.apply {
            val model = models[position]
            offset.text = "      ".repeat(model.nodeLevel)
            name.text = model.name
            with(image) {
                setImageResource(model.imageResId)
                rotation = if (model.isExpanded) 90f else 0f
            }
            itemView.setOnClickListener { listener.onItemClicked(model) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = NodeHolder(parent.inflate(R.layout.tree_node))

    override fun getItemCount() = models.size

    fun updateItems(models: MutableList<NodeModel>) {
        val old = this.models.toList()
        this.models.update(models)
        autoNotify(old, this.models) { o, n -> o.id == n.id }
    }
}

class NodeRenderer(viewType: Int, context: Context, private val listener: Listener) : ViewRenderer<NodeModel, NodeHolder>(viewType, context) {
    override fun createViewHolder(parent: ViewGroup?) = NodeHolder(parent!!.inflate(R.layout.tree_node))

    override fun bindView(item: NodeModel, holder: NodeHolder, payloads: MutableList<Any?>) {
        if (payloads.isEmpty()) {
            super.bindView(item, holder, payloads)
        } else {
            if (payloads.firstOrNull() as Boolean) {
                holder.image.apply {
                    rotation = 0f
                    startAnimation(if (item.isExpanded) createAnimator(0f, 90f) else createAnimator(90f, 0f))
                }
            }
        }
    }

    override fun bindView(model: NodeModel, holder: NodeHolder) {
        holder.apply {
            offset.text = "      ".repeat(model.nodeLevel)
            name.text = model.name
            with(image) {
                setImageResource(model.imageResId)
                rotation = if (model.isExpanded) 90f else 0f
            }
            itemView.setOnClickListener { listener.onItemClicked(model) }
        }

    }

}

class NodeHolder(root: View) : RecyclerView.ViewHolder(root) {
    val name: TextView = root.findViewById(R.id.node_text_view)
    val offset: TextView = root.findViewById(R.id.node_offset)
    val image: ImageView = root.findViewById(R.id.node_image_view)
}

fun createAnimator(from: Float, to: Float)
        = RotateAnimation(from, to, Animation.RELATIVE_TO_SELF,
        0.5f, Animation.RELATIVE_TO_SELF, 0.5f).apply {
    fillAfter = true
    duration = 400

}

interface Listener {
    fun onItemClicked(model: NodeModel)
}
