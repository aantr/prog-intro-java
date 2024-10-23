//package wordStat;

import java.util.Arrays;

public class MyArray {
    int[] arr;
    int length;

    public MyArray() {
        arr = new int[1];
        length = 0;
    }

    public void add(int el) {
        while (arr.length <= length) {
            arr = Arrays.copyOf(arr, arr.length * 2);
        }
        arr[length] = el;
        length++;
    }

    public int get(int idx) {
        if (idx < 0 || idx >= length) {
            throw new ArrayIndexOutOfBoundsException("MyArray.get index of bounds");
        }
        return arr[idx];
    }

    public int[] getArray() {
        return Arrays.copyOf(arr, length);
    }
}
