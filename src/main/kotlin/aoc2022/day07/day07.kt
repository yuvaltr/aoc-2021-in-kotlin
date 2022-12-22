package aoc2022.day07

import common.TreeNode
import readInput


interface IElement {
    val name: String
}


data class File(
    override val name: String,
    val size: Int
): IElement {
    override fun toString(): String {
        return "File '$name' (size: $size bytes)"
    }
}


data class Directory(
    override val name: String,
    var totalSize: Int = 0
): IElement {
    override fun toString(): String {
        return "Directory '$name'"
    }
}


fun readTreeFromInput(input: List<String>): TreeNode<IElement> {
    check(input[0].startsWith("$ cd"))
    val root = TreeNode<IElement>(Directory(name = input[0].split(" ")[2]))
    var currDir = root

    var i = 1
    do {
        val prompt = input[i]
        try {
            if (!prompt.startsWith("$")) {
                val name = prompt.split(" ")[1]
                if (prompt.startsWith("dir")) {
                    currDir.addChild(Directory(name = name))
                } else {
                        val fileSize = prompt.split(" ")[0].toInt()
                    currDir.addChild(File(name = name, size = fileSize))
                    (currDir.value as Directory).totalSize += fileSize
                    var parent = currDir.parent
                    while (parent != null) {
                        (parent.value as Directory).totalSize += fileSize
                        parent = parent.parent
                    }
                }

            } else if (prompt == "$ ls") {
                continue

            } else if (prompt == "$ cd ..") {
                    currDir = currDir.parent!!

            } else if (prompt.startsWith("$ cd")) {
                val name = prompt.split(" ")[2]
                val child = currDir.children.singleOrNull { it.value.name == name }
                    ?: throw IllegalStateException("didn't find child '$name'")

                check(child.value is Directory) {
                    "unexpected prompt in line $i: '$prompt' (not a directory)"
                }

                currDir = child

            } else {
                throw IllegalStateException("unknown command")
            }

        } catch (ex: Exception) {
            throw IllegalStateException("unexpected prompt in line $i: '$prompt' ($ex)")
        }
    } while (++i in input.indices)

    return root
}


fun part1() {
    fun sumDirsLess100k(root: TreeNode<IElement>): Int {
        val s = root.children
            .filter { it.value is Directory }
            .sumOf { sumDirsLess100k(it) }

        return s + (root.value as Directory).totalSize.let { if (it < 100000) it else 0 }
    }

    fun sumDirs(inputFilename: String) {
        val input = readInput(inputFilename)
        val root = readTreeFromInput(input)

        println("part1 ($inputFilename) = ${sumDirsLess100k(root)}")
    }

    sumDirs("aoc2022/day07/test.txt")
    sumDirs("aoc2022/day07/input.txt")
}


fun part2() {
    fun findTotalSizeOfDirectoryToDelete(root: TreeNode<IElement>, spaceToFree: Int): Int? {
        (root.value as Directory).let { curr ->
            if (curr.totalSize < spaceToFree)
                return null

            // the current directory is larger than the required space needed, so either:
            // find a nested directory to delete
            return root.children
                .filter { it.value is Directory }
                .filter { (it.value as Directory).totalSize >= spaceToFree }
                .mapNotNull { findTotalSizeOfDirectoryToDelete(it, spaceToFree) }
                .minByOrNull { it }

            // or delete the directory itself
                ?: curr.totalSize
        }
    }

    fun freeSpace(inputFilename: String) {
        val input = readInput(inputFilename)
        val root = readTreeFromInput(input)

        val spaceToFree = (root.value as Directory).totalSize - 40_000_000
        check(spaceToFree > 0)

        println("part2 ($inputFilename) = ${findTotalSizeOfDirectoryToDelete(root, spaceToFree)}")
    }

    freeSpace("aoc2022/day07/test.txt")
    freeSpace("aoc2022/day07/input.txt")
}


fun main() {
    part1()
    part2()
}