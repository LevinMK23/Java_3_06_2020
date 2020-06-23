package lesson1.key_points;

public class Two<A, B> {

    A left;
    B right;

    public Two(A left, B right) {
        this.left = left;
        this.right = right;
    }

    public A getLeft() {
        return left;
    }

    public B getRight() {
        return right;
    }
}
