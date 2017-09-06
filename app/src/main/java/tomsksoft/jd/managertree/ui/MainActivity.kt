package tomsksoft.jd.managertree.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import com.github.vivchar.rendererrecyclerviewadapter.RendererRecyclerViewAdapter
import kotlinx.android.synthetic.main.activity_main.*
import tomsksoft.jd.managertree.R
import tomsksoft.jd.managertree.tree.NodeModel

class MainActivity : AppCompatActivity(), IView {
    private val callback = object : RendererRecyclerViewAdapter.DiffCallback<NodeModel>() {
        override fun areItemsTheSame(oldItem: NodeModel?, newItem: NodeModel?) = oldItem?.id == newItem?.id

        override fun areContentsTheSame(oldItem: NodeModel?, newItem: NodeModel?) = oldItem == newItem

        override fun getChangePayload(oldItem: NodeModel?, newItem: NodeModel?): Any? {
            return true
        }
    }


    private val presenter = Presenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /*recycler.adapter = Adapter(object : Listener {
            override fun onItemClicked(model: NodeModel) {
                presenter.onItemClicked(model)
            }
        })*/

        val renderer = RendererRecyclerViewAdapter()
        renderer.registerRenderer(NodeRenderer(NodeModel.TYPE, this, object : Listener {
            override fun onItemClicked(model: NodeModel) {
                presenter.onItemClicked(model)
            }
        }))
        recycler.adapter = renderer

        recycler.layoutManager = LinearLayoutManager(this)
        recycler.itemAnimator = DefaultItemAnimator().apply {
            addDuration = 200
            removeDuration = 200
            changeDuration = 200
            moveDuration = 200
        }
        presenter.onCreate()

    }

    override fun updateAdapter(models: MutableList<NodeModel>) {
        (recycler.adapter as RendererRecyclerViewAdapter).apply {
            setItems(models, callback)
        }
    }
}

interface IView {
    fun updateAdapter(models: MutableList<NodeModel>)
}