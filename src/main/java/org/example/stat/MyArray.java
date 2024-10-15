import java.util.Arrays;

public class MyArray {
    private Object[] arr;
    public int length;
    public final Class<?> type;

    public MyArray(Class<?> t) {
        type = t;
        if (type == Integer.class) {
            arr = new Integer[1];
        } else if (type == String.class) {
            arr = new String[1];
        }
        length = 1;
    }

    public void add(Object t) {
        if (length >= arr.length) {
            arr = Arrays.copyOf(arr, arr.length * 2);
        }
        arr[length++] = t;
    }

    public Object get(int idx) {
        return arr[idx];
    }

    public Object[] getArray() {
        return Arrays.copyOf(arr, length);
    }
}

