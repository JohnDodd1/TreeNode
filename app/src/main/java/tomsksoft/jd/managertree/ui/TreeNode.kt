package tomsksoft.jd.managertree.ui

import android.view.View
import android.widget.TextView
import tellh.com.recyclertreeview_lib.LayoutItemType
import tellh.com.recyclertreeview_lib.TreeNode
import tellh.com.recyclertreeview_lib.TreeViewBinder
import tomsksoft.jd.managertree.R

class Dir(val name: String) : LayoutItemType {
    override fun getLayoutId() = R.layout.tree_node
}

class NodeBinder : TreeViewBinder<TreeHolder>() {

    override fun provideViewHolder(itemView: View) = TreeHolder(itemView)

    override fun bindView(holder: TreeHolder, position: Int, node: TreeNode<*>) {
        holder.name.text = (node.content as Dir).name
    }

    override fun getLayoutId() = R.layout.tree_node
}

/*
class TreeHolder : TreeViewBinder.ViewHolder {
    val name: TextView

    constructor(root: View) : super(root) {
        name = root.findViewById(R.id.node_text_view)
    }
}*/
