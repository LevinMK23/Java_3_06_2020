package lesson6;

import java.util.*;

public class ArraysMethods {
    /**
     * @param arr should not be null
     * @return null
     */
    Integer[] all_after_last_4(int [] arr){
        int length=arr.length-1;
        int n=arr[length];
        ArrayList<Integer>result=new ArrayList<>();
        while (n != 4 && length > 0){
            result.add(n);
            n=arr[--length];
        }
        if (result.size() == 0 && n!=4) throw new RuntimeException("no four in array");
        Collections.reverse(result);
        Integer [] res_arr = new Integer[result.size()];
        res_arr=result.toArray(res_arr);
        return res_arr;
    }
    boolean contains_1_or_4(Integer[]arr){
        HashSet<Integer> set= new HashSet<>(Arrays.asList(arr));return set.contains(1)||set.contains(4);
    }
}
