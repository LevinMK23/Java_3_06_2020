package homework.customTest;

class TestUtil {
    private int a, b;

    TestUtil(int[] data) {
        this.a = data[0];
        this.b = data[1];
    }

    int sum(){
        return a + b;
    }

    int sub(){
        return a - b;
    }

    int mul(){
        return a * b;
    }

    int div(){
        return a / b;
    }

    int mod(){
        return a % b;
    }
}
