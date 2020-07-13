package lesson7;

//@Green
public class Example3 {

//    @Green
//    int a;


    @Red(level = 7)
    public void foo1() {
        System.out.println("foo1 invoked");
    }

    @Green(level = 35)
    public void foo2() {
        System.out.println("foo2 invoked");
    }

}
