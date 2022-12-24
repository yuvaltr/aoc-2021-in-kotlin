package common

class IntMat(array: Array<IntArray>) {
    private val mat: Array<IntArray> = array

    fun rowIndices() = mat.indices
    fun colIndices() = mat[0].indices

    fun row(i: Int) = mat[i]
    fun col(j: Int) = rowIndices().map { mat[it][j] }.toIntArray()

    operator fun get(i: Int) = mat[i]
    operator fun get(i: Int, j: Int) = mat[i][j]
}

fun readMatrix(input: List<String>): IntMat {
    val arr = input
        .map {  line ->
            line.toCharArray().map { (it - '0') }.toIntArray()
        }
        .toTypedArray()

    return IntMat(arr)
}
