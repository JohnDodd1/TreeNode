package tomsksoft.jd.managertree.ui.main

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import com.github.vivchar.rendererrecyclerviewadapter.ItemModel
import com.github.vivchar.rendererrecyclerviewadapter.RendererRecyclerViewAdapter
import kotlinx.android.synthetic.main.activity_main.*
import tomsksoft.jd.managertree.R
import tomsksoft.jd.managertree.models.BaseModel
import tomsksoft.jd.managertree.models.LeafModel
import tomsksoft.jd.managertree.models.NodeModel
import tomsksoft.jd.managertree.renderers.LeafRenderer
import tomsksoft.jd.managertree.renderers.NodeRenderer

class MainActivity : AppCompatActivity(), IView {
    private val callback by lazy { createCallback { old, new -> old.baseId == new.baseId } }
    private val presenter = Presenter(this)
    private val adapter = RendererRecyclerViewAdapter().apply {
        registerRenderer(NodeRenderer(NodeModel.TYPE, this@MainActivity, object : NodeRenderer.Listener {
            override fun onItemClicked(model: NodeModel) {
                presenter.onNodeClicked(model)
            }
        }))
        registerRenderer(LeafRenderer(LeafModel.TYPE, this@MainActivity, object : LeafRenderer.Listener {
            override fun onItemClicked(model: LeafModel) {
                presenter.onLeafClicked(model)
            }
        }))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recycler.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            addItemDecoration(VerticalSpaceDecorator())
            itemAnimator = DefaultItemAnimator().apply {
                addDuration = 200
                removeDuration = 200
                changeDuration = 200
                moveDuration = 200
            }
            adapter = this@MainActivity.adapter
        }
    }

    override fun updateAdapter(models: List<ItemModel>) {
        runOnUiThread { adapter.setItems(models, callback) }
    }

    override fun onStart() {
        presenter.onStart()
        super.onStart()
    }

    override fun onStop() {
        presenter.onStop()
        super.onStop()
    }
}

interface IView {
    fun updateAdapter(models: List<ItemModel>)
}

fun createCallback(compare: (BaseModel, BaseModel) -> Boolean) = object : RendererRecyclerViewAdapter.DiffCallback<BaseModel>() {
    override fun areItemsTheSame(oldItem: BaseModel, newItem: BaseModel) = compare(oldItem, newItem)

    override fun areContentsTheSame(oldItem: BaseModel, newItem: BaseModel) = oldItem == newItem

    override fun getChangePayload(oldItem: BaseModel, newItem: BaseModel): Any? {
        return true
    }
}