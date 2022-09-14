public class LinkedListDeque<T> {

    private class TypeNode {
        private T val;
        private TypeNode next;
        private TypeNode prev;

        public TypeNode(T item, TypeNode next, TypeNode prev) {
            this.val = item;
            this.next = next;
            this.prev = prev;
        }
    }

    private TypeNode headSentiNode;
    private int size;
    private TypeNode endSentiNode;
    private TypeNode last;
    private TypeNode first;

    /**
     * initialization an empty linked list deque.
     * */
    public LinkedListDeque() {
        size = 0;
        headSentiNode = new TypeNode(null, null, null);
        endSentiNode = new TypeNode(null, null, null);
        headSentiNode.prev = endSentiNode;
        headSentiNode.next = endSentiNode;
        endSentiNode.prev = headSentiNode;
        endSentiNode.next = headSentiNode;
        last = endSentiNode.prev;
        first = headSentiNode.next;
    }
    /**
    public LinkedListDeque(LinkedListDeque other) {
        headSentiNode = new TypeNode(null, null, null);
        endSentiNode = new TypeNode(null, null, null);
        headSentiNode.prev = endSentiNode;
        headSentiNode.next = endSentiNode;
        endSentiNode.prev = headSentiNode;
        endSentiNode.next = headSentiNode;
        if (other.size == 0) {
            this.size = 0;
        } else {
            this.size = other.size;
            TypeNode pointer = other.first;
            TypeNode prepointer = headSentiNode;
            while (pointer != other.endSentiNode) {
                TypeNode newNode = new TypeNode(pointer.val, null, null);
                prepointer.next = newNode;
                newNode.prev = prepointer;
                prepointer = prepointer.next;
                pointer = pointer.next;
            }
            prepointer.next = endSentiNode;
            endSentiNode.prev = prepointer;
            first = headSentiNode.next;
            last = endSentiNode.prev;
        }
    }
     */

    /**
     * @param val value to be added at the first of list.
     */
    public void addFirst(T val) {
        TypeNode newNode = new TypeNode(val, first, headSentiNode);
        headSentiNode.next = newNode;
        first.prev = newNode;
        first = newNode;
        last = endSentiNode.prev;
        size += 1;
    }

    /**
     * @return number of elements in list.
     */
    public int size() {
        return this.size;
    }

    /**
     * add an element to the last of list.
     * @param val element to be added.
     */
    public void addLast(T val) {
        TypeNode newNode = new TypeNode(val, endSentiNode, last);
        endSentiNode.prev = newNode;
        last.next = newNode;
        last = newNode;
        first = headSentiNode.next;
        size += 1;
    }

    /**
     * check if number of elements in list is zero.
     * @return true if list is empty, otherwise false.
     */
    public boolean isEmpty() {
        return this.size == 0;
    }

    /**
     * Removes and returns the item at the front of the deque. If no such item exists, returns null.
     * @return first item or null in case first item not exists.
     */
    public T removeFirst() {
        if (this.size == 0) {
            return null;
        }
        TypeNode returnNode = this.first;
        TypeNode nextFirst = this.first.next;
        nextFirst.prev = this.headSentiNode;
        this.headSentiNode.next = nextFirst;
        this.first = nextFirst;
        this.size -= 1;
        this.last = this.endSentiNode.prev;
        return returnNode.val;
    }

    /**
     * Removes and returns the item at the back of the deque. If no such item exists, returns null.
     * @return last item or null in case last item not exists.
     */
    public T removeLast() {
        if (this.size == 0) {
            return null;
        }
        TypeNode returnNode = this.last;
        TypeNode nextLast = this.last.prev;
        this.endSentiNode.prev = nextLast;
        nextLast.next = this.endSentiNode;
        this.size -= 1;
        this.last = nextLast;
        this.first = this.headSentiNode.next;
        return returnNode.val;
    }

    public void printDeque() {
        TypeNode pointer = this.first;
        for (int i = 0; i < this.size; i++) {
            System.out.print(first.val + " ");
        }
        System.out.println();
    }

    /**
     * Gets the item at the given index.
     * @param index index to be found.
     * @return return element at given index. If index is invalid, return null.
     */
    public T get(int index) {
        if (index + 1 > this.size) {
            return null;
        }
        TypeNode pointer = this.first;
        for (int i = 0; i < index; i++) {
            pointer = pointer.next;
        }
        return pointer.val;
    }

    private T getRecursiveNode(TypeNode p, int ind) {
        if (ind == 0) {
            return p.val;
        }
        return getRecursiveNode(p.next, ind - 1);
    }

    public T getRecursive(int index) {
        if (index + 1 > this.size) {
            return null;
        }
        return getRecursiveNode(this.first, index);
    }
}
