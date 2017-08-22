package tomsksoft.jd.managertree.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import tomsksoft.jd.managertree.R

class MainActivity : AppCompatActivity(), IView {
    val presenter = Presenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        presenter.onCreate()
    }

}

interface IView {
}

