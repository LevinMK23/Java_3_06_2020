package lesson1.liquids;

public class Test {
    public static void main(String[] args) {

        Glass<Metal> mg = new Glass<>(new Metal());
        Glass<Water> wg = new Glass<>(new Water());

        System.out.println(mg.getLiquidWeight());
        System.out.println(wg.getLiquidWeight());

        System.out.println(mg.getLiquidName());
        System.out.println(wg.getLiquidName());
    }
}
