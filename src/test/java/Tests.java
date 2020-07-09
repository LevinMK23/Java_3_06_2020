import lesson6.testing.TestUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class Tests {

    private TestUtils reference;

    private int a, b, c;

    public Tests(int a, int b, int c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    @Parameterized.Parameters
    public static Object[][] genParams() {
        return new Object[][] {
                {1, 2, 3}, {2, 2, 4}
        };
    }

    @Before
    public void initTest() {
        reference = new TestUtils();
    }

    @Test
    public void testSum() {
        int sum = reference.sum(a, b);
        Assert.assertEquals(c, sum);
    }

    @Test(expected = RuntimeException.class)
    public void testException() {
        int x = reference.funcWithError(34);
        Assert.assertEquals(3, x);
    }

    @Test
    public void testArray() {
        Assert.assertArrayEquals(new int[]{1, 2, 3}, reference.getArray());
    }

    @Test(timeout = 300)
    public void testPerformance() {
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
