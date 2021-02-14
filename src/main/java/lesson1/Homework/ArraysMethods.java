package lesson1.Homework;

import com.sun.istack.internal.NotNull;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class ArraysMethods<T> {
    public T[] swap(T[] array, int pos1, int pos2){
        if (pos1<0||pos1>=array.length||pos2<0||pos2>=array.length){throw new ArrayIndexOutOfBoundsException("incorrect input params");}
        T o =array[pos1];
        array[pos1]=array[pos2];
        array[pos2]=o;
        return array;
    }

    public   ArrayList<T> changeToList(@NotNull T array[] ){

        return new ArrayList<T>(Arrays.asList(array));
    }
}
