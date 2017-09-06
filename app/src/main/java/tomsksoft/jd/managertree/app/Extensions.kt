package tomsksoft.jd.managertree.app

import android.support.annotation.LayoutRes
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

fun Any.log() = Log.d("myLogs", this.toString())

fun <T> MutableList<T>.update(list: List<T>) = this.apply {
    clear()
    addAll(list)
}

fun ViewGroup.inflate(@LayoutRes res: Int, attachToRoot: Boolean = false): View
        = LayoutInflater.from(context).inflate(res, this, attachToRoot)


fun <T> RecyclerView.Adapter<*>.autoNotify(old: List<T>, new: List<T>, compare: (T, T) -> Boolean) {
    DiffUtil.calculateDiff(object : DiffUtil.Callback() {
        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int)
                = compare(old[oldItemPosition], new[newItemPosition])

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int)
                = old[oldItemPosition] == new[newItemPosition]

        override fun getOldListSize() = old.size
        override fun getNewListSize() = new.size

        override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
            return true
        }
    }).apply { dispatchUpdatesTo(this@autoNotify) }
}

