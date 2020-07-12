package homework.jUnit;

import homework.utils.Logger;
import org.apache.commons.lang3.ArrayUtils;

import java.util.*;


public class Test {
    private Logger log = new Logger(Test.class);

    public int[] funcWithError(int[] array) {
        log.appInfo("funcWithError", "Пришел массив: " + Arrays.toString(array));
        int index = ArrayUtils.lastIndexOf(array,4);
        if (!Objects.equals(index, -1)) {
            return Arrays.stream(array).skip(index + 1).toArray();
        } else {
            throw new RuntimeException();
        }
    }

    public boolean findNumbers(int[] array) {
        log.appInfo("findNumbers", "Пришел массив: " + Arrays.toString(array));
        int[] testArray = Arrays.stream(array).filter(x -> x != 4).filter(x -> x != 1).toArray();
        return Objects.equals(testArray.length, array.length);
    }
}
