package hare.utils

class Queue<Value> {
    // MARK: - Node

    private data class Node<Value>(val value: Value, var next: Node<Value>? = null)

    // MARK: - Public properties

    val size: Int
        get() = nodesCount

    // MARK:- Private properties

    private var head: Node<Value>? = null
    private var tail: Node<Value>? = null
    private var nodesCount = 0

    // MARK: - Public methods

    fun enqueue(values: List<Value>) {
        values.forEach { enqueue(it) }
    }

    fun enqueue(value: Value) {
        val newNode = Node(value)
        tail?.next = newNode
        tail = newNode
        head = if (head == null) newNode else head
        nodesCount += 1
    }

    fun dequeue(): Value? {
        val returnValue = head?.value
        head = head?.next
        tail = if (head == null) null else tail
        return returnValue
    }
}

