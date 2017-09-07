package tomsksoft.jd.managertree.models

data class LeafModel(val name: String,
                     val level: Int = 0, val id: Int) : BaseModel(id) {

    override fun getType() = TYPE

    companion object {
        val TYPE = 2
    }

}
