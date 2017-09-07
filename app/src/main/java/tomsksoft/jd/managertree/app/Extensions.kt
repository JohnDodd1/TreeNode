package tomsksoft.jd.managertree.app

import android.support.annotation.LayoutRes
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

fun Any.log() = Log.d("myLogs", this.toString())

fun ViewGroup.inflate(@LayoutRes res: Int, attachToRoot: Boolean = false): View
        = LayoutInflater.from(context).inflate(res, this, attachToRoot)
