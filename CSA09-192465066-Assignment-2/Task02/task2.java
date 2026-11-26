public class task2 {
    static long total;

    public static void main(String[] args) {
        int[] sizes = {1000, 10000, 50000};
        int repeats = 100;

        System.out.println("Size,ArrayList end ns,LinkedList end ns,ArrayList front ns,LinkedList front ns,ArrayDeque ends ns");
        System.out.println("Size,ArrayList middle ns,LinkedList middle ns,ArrayList get ns,LinkedList get ns,ArrayList iterate ns,LinkedList iterate ns");

        for (int n : sizes) {
            java.util.ArrayList<Integer> arrayList = new java.util.ArrayList<Integer>();
            java.util.LinkedList<Integer> linkedList = new java.util.LinkedList<Integer>();
            java.util.ArrayDeque<Integer> deque = new java.util.ArrayDeque<Integer>();

            for (int i = 0; i < n; i++) {
                arrayList.add(i);
                linkedList.add(i);
                deque.add(i);
            }

            long start = System.nanoTime();
            for (int i = 0; i < repeats; i++) arrayList.add(i);
            long arrayEnd = (System.nanoTime() - start) / repeats;

            start = System.nanoTime();
            for (int i = 0; i < repeats; i++) linkedList.add(i);
            long linkedEnd = (System.nanoTime() - start) / repeats;

            start = System.nanoTime();
            for (int i = 0; i < repeats; i++) { arrayList.add(0, i); arrayList.remove(0); }
            long arrayFront = (System.nanoTime() - start) / repeats;

            start = System.nanoTime();
            for (int i = 0; i < repeats; i++) { linkedList.addFirst(i); linkedList.removeFirst(); }
            long linkedFront = (System.nanoTime() - start) / repeats;

            start = System.nanoTime();
            for (int i = 0; i < repeats; i++) {
                deque.addFirst(i);
                deque.removeFirst();
                deque.addLast(i);
                deque.removeFirst();
            }
            long dequeEnds = (System.nanoTime() - start) / repeats;

            start = System.nanoTime();
            for (int i = 0; i < repeats; i++) { arrayList.add(arrayList.size() / 2, i); arrayList.remove(arrayList.size() / 2); }
            long arrayMiddle = (System.nanoTime() - start) / repeats;

            start = System.nanoTime();
            for (int i = 0; i < repeats; i++) { linkedList.add(linkedList.size() / 2, i); linkedList.remove(linkedList.size() / 2); }
            long linkedMiddle = (System.nanoTime() - start) / repeats;

            start = System.nanoTime();
            for (int i = 0; i < repeats; i++) total = total + arrayList.get(n / 2);
            long arrayGet = (System.nanoTime() - start) / repeats;

            start = System.nanoTime();
            for (int i = 0; i < repeats; i++) total = total + linkedList.get(n / 2);
            long linkedGet = (System.nanoTime() - start) / repeats;

            start = System.nanoTime();
            for (int value : arrayList) total = total + value;
            long arrayIterate = System.nanoTime() - start;

            start = System.nanoTime();
            for (int value : linkedList) total = total + value;
            long linkedIterate = System.nanoTime() - start;

            System.out.println(n + "," + arrayEnd + "," + linkedEnd + "," + arrayFront + "," + linkedFront + "," + dequeEnds);
            System.out.println(n + "," + arrayMiddle + "," + linkedMiddle + "," + arrayGet + "," + linkedGet + "," + arrayIterate + "," + linkedIterate);
        }

        System.out.println("ArrayList is suitable for indexed access. ArrayDeque is suitable for an Up Next queue at both ends.");
    }
}