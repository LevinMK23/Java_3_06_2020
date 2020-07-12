import homework.utils.Logger;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.*;


@RunWith(Parameterized.class)
public class jUnitTest extends Assert {
    private Logger log = new Logger(jUnitTest.class);
    private static final int parametersCount = 2;
    private static final int arrayCount = 7;
    private static final int arrayLength = 10;
    private final int[] compareArray;
    private final int[] calc;
    private homework.jUnit.Test testClass;

    public jUnitTest(int[] compareArray, int[] calc) {
        this.compareArray = compareArray;
        this.calc = calc;
    }

    @Parameterized.Parameters
    public static List<int[][]> isEmptyData() {
        Random random = new Random();
        List<int[][]> arraysList = new ArrayList<>();
        for (int i = 0; i < arrayCount; i++) {
            int[][] array = new int[parametersCount][arrayLength];
            for (int j = 0; j < arrayLength; j++) {
                array[0][j] = random.nextInt(10);
                array[1][j] = random.nextInt(10);
            }
            arraysList.add(array);
        }
        return arraysList;
    }

    @Test
    public void testArr() {
        assertFalse(testClass.findNumbers(compareArray));
    }

    @Before
    public void initTest() {
        testClass = new homework.jUnit.Test();
    }

    @Test(expected = RuntimeException.class)
    public void testException() {
        int[] arr = testClass.funcWithError(calc);
        log.appInfo("", "Вернулся массив - " + Arrays.toString(arr));
    }
}
