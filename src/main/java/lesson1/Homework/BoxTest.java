package lesson1.Homework;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoxTest {
    Box<Apple> appleBox=new Box<>();
    Box<Orange> orangeBox=new Box<>();
    private void init(){
        appleBox.add(new Apple());
        appleBox.add(new Apple());
        orangeBox.add(new Orange());
        orangeBox.add(new Orange());
    }
    @Test
    void getWeight() {
        init();
        assertEquals(2f,appleBox.getWeight());
        assertEquals(3f,orangeBox.getWeight());
    }

    @Test
    void compare() {
        init();
        assertFalse(appleBox.compare(orangeBox));
        appleBox.add(new Apple());
        assertTrue(appleBox.compare(orangeBox));
    }


    @Test
    void change() {
        init();
        Box <Apple> appleBox1=new Box<>();
        appleBox1=appleBox.change(appleBox1);
        assertEquals(2f,appleBox1.getWeight());
        assertEquals(appleBox.getWeight(),0);

    }
}