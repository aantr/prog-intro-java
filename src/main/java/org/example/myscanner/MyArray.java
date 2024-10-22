package myscanner;

import java.util.Arrays;

public class MyArray {
    Object[] arr;
    int length;

    public MyArray(Class<?> type) {
        if (type == Integer.class) {
            arr = new Integer[1];
        } else if (type == String.class) {
            arr = new String[1];
        } else {
            arr = new Object[1];
        }
        length = 0;
    }

    public void add(Object el) {
        while (arr.length <= length) {
            arr = Arrays.copyOf(arr, arr.length * 2);
        }
        arr[length] = el;
        length++;
    }

    public Object get(int idx) {
        if (idx < 0 || idx >= length) {
            throw new ArrayIndexOutOfBoundsException("MyArray.get index of bounds");
        }
        return arr[idx];
    }

    public Object[] getArray() {
        return Arrays.copyOf(arr, length);
    }
}
