package lesson1.liquids;

public class Glass<T extends Liquid> {

    private T liquid;

    public Glass(T liquid) {
        this.liquid = liquid;
    }

    int getLiquidWeight() {
        return liquid.getWeight();
    }

    String getLiquidName() {
        return liquid.getClass().getSimpleName();
    }
}
