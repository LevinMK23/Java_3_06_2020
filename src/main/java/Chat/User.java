package Chat;

import lombok.Data;

@Data
public class User {
    private int USER_STATE;
    public final static int correct = 1;
    public final static int incorrect_login =2;
    public final static int incorrect_password=3;
    private String login,email;
    private long hash_password;
    private String nick_name;

    public User(String login, String email, long hash_password, String nick_name,int USER_STATE) {
        this.login = login;
        this.USER_STATE=USER_STATE;
        this.email = email;
        this.hash_password = hash_password;
        this.nick_name = nick_name;
    }
    public User(int USER_STATE) {
    }
}
