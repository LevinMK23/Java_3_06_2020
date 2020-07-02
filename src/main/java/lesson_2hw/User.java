package lesson_2hw;

import lombok.Data;

@Data
public class User {
    private String login,email;
    private long hash_password;
    private String nick_name;

    public User(String login, String email, long hash_password, String nick_name) {
        this.login = login;
        this.email = email;
        this.hash_password = hash_password;
        this.nick_name = nick_name;
    }
}
