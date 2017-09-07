package tomsksoft.jd.managertree.models

data class NodeModel(val name: String,
                     val level: Int = 0,
                     val id: Int,
                     val hasChildren: Boolean = false,
                     val isExpanded: Boolean) : BaseModel(id) {

    override fun getType() = TYPE

    companion object {
        val TYPE = 1
    }
}
