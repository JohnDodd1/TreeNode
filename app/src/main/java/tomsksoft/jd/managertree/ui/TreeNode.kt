package tomsksoft.jd.managertree.ui

import android.view.View
import tellh.com.recyclertreeview_lib.LayoutItemType
import tellh.com.recyclertreeview_lib.TreeNode
import tellh.com.recyclertreeview_lib.TreeViewBinder
import tomsksoft.jd.managertree.R

class Dir(val name: String) : LayoutItemType {
    override fun getLayoutId() = R.layout.tree_node
}

class NodeBinder : TreeViewBinder<NodeHolder>() {

    override fun provideViewHolder(itemView: View) = NodeHolder(itemView)

    override fun bindView(holder: NodeHolder, position: Int, node: TreeNode<*>) {
        holder.name.text = (node.content as Dir).name
    }

    override fun getLayoutId() = R.layout.tree_node
}

/*
class NodeHolder : TreeViewBinder.ViewHolder {
    val name: TextView

    constructor(root: View) : super(root) {
        name = root.findViewById(R.id.node_text_view)
    }
}*/
