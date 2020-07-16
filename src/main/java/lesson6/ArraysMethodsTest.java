package lesson6;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class ArraysMethodsTest {
    ArraysMethods methods=new ArraysMethods();
    @Test
    void all_after_last_4() {
        int [] expected={5,5};
        Integer [] res=this.methods.all_after_last_4(new int[]{1,2,3,2,2,4,4,1,1,1,4,5,5});
        assertArrayEquals(expected, Arrays.stream(res).mapToInt(Integer::intValue).toArray());

        expected= new int[]{};
        res=this.methods.all_after_last_4(new int[]{1,2,3,2,2,4,4,1,1,1,4,5,5,4});
        assertArrayEquals(expected, Arrays.stream(res).mapToInt(Integer::intValue).toArray());

        expected= new int[]{1, 3};
        res=this.methods.all_after_last_4(new int[]{4,1,3});
        assertArrayEquals(expected, Arrays.stream(res).mapToInt(Integer::intValue).toArray());


    }

    @Test
    void contains_1_or_4() {
        Integer [] a1={1 ,2 ,3};
        assertTrue(methods.contains_1_or_4(a1));
        Integer [] a2={2,2,299,9922,525251,515177,75623};
        assertFalse(methods.contains_1_or_4(a2));
        Integer[]a3={};
        assertFalse(methods.contains_1_or_4(a3));
    }
}