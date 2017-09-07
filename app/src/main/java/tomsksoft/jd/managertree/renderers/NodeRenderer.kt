package tomsksoft.jd.managertree.renderers

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import android.widget.ImageView
import android.widget.RelativeLayout.LayoutParams
import android.widget.TextView
import com.github.vivchar.rendererrecyclerviewadapter.ViewRenderer
import tomsksoft.jd.managertree.R
import tomsksoft.jd.managertree.app.inflate
import tomsksoft.jd.managertree.models.NodeModel

class NodeRenderer(viewType: Int, context: Context, private val listener: Listener)
    : ViewRenderer<NodeModel, NodeRenderer.NodeHolder>(viewType, context) {

    override fun createViewHolder(parent: ViewGroup?) = NodeHolder(parent!!.inflate(R.layout.line_node))

    override fun bindView(item: NodeModel, holder: NodeHolder, payloads: MutableList<Any?>) {
        if (payloads.isEmpty())
            super.bindView(item, holder, payloads)
        else {
            holder.image.apply {
                if (item.hasChildren) {
                    rotation = 0f
                    startAnimation(if (item.isExpanded) createAnimation(0f, 90f) else createAnimation(90f, 0f))
                    visibility = VISIBLE
                } else visibility = INVISIBLE
            }
        }
    }

    override fun bindView(model: NodeModel, holder: NodeHolder) {
        holder.apply {
            itemView.layoutParams = LayoutParams(MATCH_PARENT, WRAP_CONTENT)
                    .apply { setMargins(64 * model.level, 0, 0, 0) }
            name.text = model.name
            image.rotation = if (model.isExpanded) 90f else 0f
            image.visibility = if (model.hasChildren) VISIBLE else INVISIBLE
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

    private fun createAnimation(from: Float, to: Float)
            = RotateAnimation(from, to, Animation.RELATIVE_TO_SELF,
            0.5f, Animation.RELATIVE_TO_SELF, 0.5f).apply {
        fillAfter = true
        duration = 400
    }
}
