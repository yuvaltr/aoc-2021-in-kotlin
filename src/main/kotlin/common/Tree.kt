package common

class TreeNode<T>(
    var value: T,
    var parent: TreeNode<T>? = null
) {
    var children: MutableList<TreeNode<T>> = mutableListOf()

    fun addChild(value: T) {
        val node = TreeNode(value = value, parent = this)
        children.add(node)
    }

    override fun toString(): String {
        var s = "$value"
        if (children.isNotEmpty()) {
            s += " {" + children.map { it.toString() } + " }"
        }
        return s
    }
}
