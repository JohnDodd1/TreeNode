package tomsksoft.jd.managertree.tree.components

class Company {
    val headComponent = NodeComponent("General director")

    private var callback: (() -> Unit)? = null

    fun subscribe(callback: () -> Unit) {
        this.callback = callback
        callback()
    }

    fun dispose() {
        callback = null
    }

    fun next() = callback?.invoke()

}
