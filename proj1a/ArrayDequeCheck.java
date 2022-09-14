public class ArrayDequeCheck {
    public static void main(String[] args) {
        ArrayDeque<Integer> testDeque = new ArrayDeque<>();
        for (int i = 0; i < 78; i++) {
            testDeque.addFirst(i);
        }
        System.out.println(testDeque.isEmpty());
    }
}
