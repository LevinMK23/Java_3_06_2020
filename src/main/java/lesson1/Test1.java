package lesson1;

public class Test1 {
    public static void main(String[] args) {
        Gen1<String> gen1 = new Gen1<>("1241");
        System.out.println(gen1.getValue());
        gen1.setValue("1341");
        gen1.getTypeByValue("124124");

    }
}
