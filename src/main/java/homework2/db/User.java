package homework2.db;

public class User {
    private int id;
    private String login, password, name;

    public User() {
    }

    public User(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public User(int id, String login, String name) {
        this.id = id;
        this.login = login;
        this.name = name;
    }

    public User(String login, String password, String name) {
        this.login = login;
        this.password = password;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String toString() {
        return "id: " + id + ", " +
                "login: " + login + ", " +
                "name: " + name;
    }

    public String auth() {
        return "{ \"name\":\"" + name + "\", " +
                "\"password\":\"" + password + "\" }";
    }
}