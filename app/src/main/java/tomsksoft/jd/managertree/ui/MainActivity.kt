package tomsksoft.jd.managertree.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import tomsksoft.jd.managertree.R
import tomsksoft.jd.managertree.tree.NodeModel

class MainActivity : AppCompatActivity(), IView {
    private val presenter = Presenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recycler.adapter = Adapter(object : Listener {
            override fun onItemClicked(model: NodeModel) {
                presenter.onItemClicked(model)
            }
        })
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
        (recycler.adapter as Adapter).apply {
            updateItems(models)
        }
    }
}

interface IView {
    fun updateAdapter(models: MutableList<NodeModel>)
}

