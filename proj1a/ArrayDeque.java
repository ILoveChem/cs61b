public class ArrayDeque<T> {
    /**
     * headIndex: the first null-empty element in deque.
     * endIndex: the last null-empty element in deque + 1.
     */
    private int endIndex;
    private T[] items;
    private int headIndex;
    private int size;
    private double usage;

    /**
     * Create an empty array deque.
     */
    public ArrayDeque() {
        items = (T []) new Object[8];
        endIndex = 0;
        headIndex = 0;
        size = 0;
        usage = 0;
    }

    /**
     * create a deep copy of given array deque.
     * @param other array deque to be copied.
     */
    public ArrayDeque(ArrayDeque<T> other) {
        this.size = other.size;
        T[] newDeque = (T []) new Object[other.items.length];
        this.items = newDeque;
        this.headIndex = other.headIndex;
        this.endIndex = other.endIndex;
        this.usage = other.usage;
        // if other is full.
        if (other.headIndex == other.endIndex && other.size != 0) {
            System.arraycopy(other.items, other.headIndex, this.items,
                    0, other.items.length - other.headIndex);
            System.arraycopy(other.items, 0, this.items,
                    other.items.length - other.headIndex, other.headIndex);
            this.headIndex = 0;
            this.endIndex = this.size;
        }
        // if not circular.
        if (other.headIndex < other.endIndex) {
            System.arraycopy(other.items, other.headIndex, this.items, other.headIndex, other.size);
        }
        // if circular.
        if (other.headIndex > other.endIndex) {
            for (int i = other.headIndex; i < other.items.length; i++) {
                this.items[i] = other.items[i];
            }
            for (int i = 0; i < other.endIndex; i++) {
                this.items[i] = other.items[i];
            }
        }
    }

    /**
     * Add boxes so that the length of items is newSize.
     * @param newSize new size of items.
     */
    private void addSize(int newSize) {
        // addSize(int newSize) method is only called when usage == 1.
        T[] newDeque = (T []) new Object[newSize];
        // if not circular.
        if (endIndex == items.length) {
            System.arraycopy(items, 0, newDeque, 0, size);
        } else {
            //if circular.
            int addNum = newSize - items.length;
            System.arraycopy(items, 0, newDeque, 0, endIndex);
            System.arraycopy(items, headIndex, newDeque, headIndex + addNum, size - endIndex);
            headIndex += addNum;
        }
        items = newDeque;
        usage = 1.0 * size / items.length;
    }

    /**
     * Delete size so that the length of items is newSize.
     * @param newSize new size of items.
     */
    private void deleteSize(int newSize) {
        // deleteSize(int newSize) is called when usage == 0.25
        T[] newDeque = (T []) new Object[newSize];
        if (headIndex < endIndex) {
            System.arraycopy(items, headIndex, newDeque, 0, size);
        } else {
            System.arraycopy(items, headIndex, newDeque, 0, items.length - headIndex);
            System.arraycopy(items, 0, newDeque, items.length - headIndex, endIndex);
        }
        items = newDeque;
        headIndex = 0;
        endIndex = size;
        usage = 1.0 * size / items.length;
    }

    /**
     * adds an item of type T to the front of the deque
     * @param item item to be added.
     */
    public void addFirst(T item) {
        // if items is empty.
        if (size == 0) {
            endIndex += 1;
        }
        // if items is full.
        if (size == items.length) {
            addSize(size * 2);
            headIndex = (headIndex - 1 + items.length) % items.length;
        }
        // if items is not neither full nor empty.
        if (size < items.length && size > 0) {
            headIndex = (headIndex - 1 + items.length) % items.length;
        }
        items[headIndex] = item;
        size += 1;
        usage = 1.0 * size / items.length;
    }

    /**
     * Adds an item of type T to the back of the deque.
     * @param item element to be added.
     */
    public void addLast(T item) {
        // if items is empty, no need to modify.
        // if items is full, addSize.
        if (size == items.length) {
            addSize(size * 2);
        }
        // if not full or empty, no need to modify.
        items[endIndex] = item;
        endIndex += 1;
        size += 1;
        usage = 1.0 * size / items.length;
    }

    /**
     * Returns true if deque is empty, false otherwise.
     * @return Return true if empty otherwise false.
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Returns the number of items in the deque.
     * @return number of array deque.
     */
    public int size() {
        return size;
    }

    /**
     * Prints the items in the deque from first to last, separated by a space.
     * Once all the items have been printed, print out a new line.
     */
    public void printDeque() {
        if (size != 0) {
            if (endIndex > headIndex) {
                for (int i = headIndex; i < endIndex; i++) {
                    System.out.print(items[i] + " ");
                }
            } else {
                for (int i = headIndex; i < items.length; i++) {
                    System.out.print(items[i] + " ");
                }
                for (int j = 0; j < endIndex; j++) {
                    System.out.print(items[j] + " ");
                }
            }
        }
        System.out.println("");
    }

    /**
     * Removes and returns the item at the front of the deque. If no such item exists, returns null.
     * @return return first element in deque. if deque is empty, return null.
     */
    public T removeFirst() {
        // if deque is empty.
        if (size == 0) {
            return null;
        }
        T returnVal = items[headIndex];
        headIndex = (headIndex + 1) % items.length;
        size -= 1;
        usage = 1.0 * size / items.length;
        // determine if delete useless boxes.
        if (usage <= 0.25 && items.length > 8) {
            deleteSize(size / 2);
        }
        return returnVal;
    }

    /**
     * Removes and returns the item at the back of the deque. If no such item exists, returns null.
     * @return return last element in deque. If deque is empty, return null.
     */
    public T removeLast() {
        // if deque is empty.
        if (size == 0) {
            return null;
        }
        int newEnd = (endIndex - 1 + items.length) % items.length;
        T returnVal = items[newEnd];
        endIndex = newEnd;
        size -= 1;
        usage = 1.0 * size / items.length;
        if (usage <= 0.25 && items.length > 8) {
            deleteSize(size / 2);
        }
        return returnVal;
    }

    public T get(int index) {
        if (index + 1 > size) {
            return null;
        }
        return items[(headIndex + index) % items.length];
    }
}
