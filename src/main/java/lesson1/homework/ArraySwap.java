package lesson1.homework;

import java.util.ArrayList;

public class ArraySwap {

    ArrayList<int[]> arr1 = new ArrayList<>();

    public void swap(Object [] arr, int pos1, int pos2) {
        if (pos1 < 0 || pos1 >= arr.length) {
            throw new ArrayIndexOutOfBoundsException(pos1);
        }
        if (pos2 < 0 || pos2 >= arr.length) {
            throw new ArrayIndexOutOfBoundsException(pos2);
        }
        Object o1 = arr[pos2];
        arr[pos2] = arr[pos1];
        arr[pos1] = o1;
    }

}
