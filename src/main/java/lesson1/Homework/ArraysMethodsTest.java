package lesson1.Homework;

import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ArraysMethodsTest {
    @Test
    void swapWithRightParams() {
        Integer [] test={1,2,3,4,5};
        ArraysMethods<Integer> methods=new ArraysMethods<Integer>();
        test=methods.swap(test,0,3);

        Integer [] result={4,2,3,1,5};
        assertArrayEquals(test,result,"swapping elements with right params");
        System.out.println("Swapping with right params: success");
    }
    @Test
    void swapWithBadParams(){
        Integer [] test={1,2,3,4,5};
        ArraysMethods<Integer> methods=new ArraysMethods<Integer>();
        Exception exception=assertThrows(ArrayIndexOutOfBoundsException.class,()->methods.swap(test,10,-1));
        String expected="incorrect input params";
        assertEquals(expected, exception.getMessage());
        System.out.println("incorrect input: success");
       // methods.swap(test,10,2);
    }

    @Test
    void changeToList() {
        Integer [] test={1,2,3,4,5};
        ArraysMethods<Integer> methods=new ArraysMethods<Integer>();
        methods.changeToList(test);
        ArrayList<Integer> result=new ArrayList<>();
        result.add(1);
        result.add(2);
        result.add(3);
        result.add(4);
        result.add(5);
        assertEquals(result,methods.changeToList(test));

    }

}