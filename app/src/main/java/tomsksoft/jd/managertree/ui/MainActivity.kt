package tomsksoft.jd.managertree.ui

import android.graphics.Rect
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.github.vivchar.rendererrecyclerviewadapter.ItemModel
import com.github.vivchar.rendererrecyclerviewadapter.RendererRecyclerViewAdapter
import kotlinx.android.synthetic.main.activity_main.*
import tomsksoft.jd.managertree.R
import tomsksoft.jd.managertree.tree.BaseModel
import tomsksoft.jd.managertree.tree.LeafModel
import tomsksoft.jd.managertree.tree.NodeModel

class MainActivity : AppCompatActivity(), IView {
    private val callback = createCallback { old, new -> old?.baseId == new?.baseId }
    private val presenter = Presenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val renderer = RendererRecyclerViewAdapter()
        renderer.registerRenderer(NodeRenderer(NodeModel.TYPE, this, object : NodeRenderer.Listener {
            override fun onItemClicked(model: NodeModel) {
                presenter.onNodeClicked(model)
            }
        }))

        renderer.registerRenderer(LeafRenderer(LeafModel.TYPE, this, object : LeafRenderer.Listener {
            override fun onItemClicked(model: LeafModel) {
                presenter.onLeafClicked(model)
            }
        }))
        recycler.adapter = renderer
        val layoutManager = LinearLayoutManager(this)
        recycler.layoutManager = layoutManager
        recycler.addItemDecoration(Decorator())
        recycler.itemAnimator = DefaultItemAnimator().apply {
            addDuration = 200
            removeDuration = 200
            changeDuration = 200
            moveDuration = 200
        }
        presenter.onCreate()
    }

    override fun onStop() {
        presenter.onStop()
        super.onStop()
    }

    override fun updateAdapter(models: List<ItemModel>) {
        (recycler.adapter as RendererRecyclerViewAdapter).apply {
            setItems(models, callback)
        }
    }
}

interface IView {
    fun updateAdapter(models: List<ItemModel>)
}

fun createCallback(compare: (BaseModel?, BaseModel?) -> Boolean) = object : RendererRecyclerViewAdapter.DiffCallback<BaseModel>() {
    override fun areItemsTheSame(oldItem: BaseModel?, newItem: BaseModel?) = compare(oldItem, newItem)

    override fun areContentsTheSame(oldItem: BaseModel?, newItem: BaseModel?) = oldItem == newItem

    override fun getChangePayload(oldItem: BaseModel?, newItem: BaseModel?): Any? {
        return true
    }
}

class Decorator : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        if (parent.getChildAdapterPosition(view) != 0)
            outRect.set(0, 16, 0, 0)
    }
}