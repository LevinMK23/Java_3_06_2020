package lesson2;
import lombok.Data;

import java.io.Serializable;

@Data
public class User implements Serializable {
    private int id;
    private String login, password;

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public User(int id, String login, String password) {
        this.id = id;
        this.login = login;
        this.password = password;
    }
}
