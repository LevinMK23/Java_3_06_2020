package lesson2;
import lombok.Data;

import java.io.Serializable;

@Data
public class User implements Serializable {
    private int age, id;
    private String name, address;

    public User(int age, int id, String name, String address) {
        this.age = age;
        this.id = id;
        this.name = name;
        this.address = address;
    }
}
