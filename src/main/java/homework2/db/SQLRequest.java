package homework2.db;

public class SQLRequest {
    public String createTable() {
        return "create table if not exists users (id INTEGER PRIMARY KEY, login TEXT, password INTEGER, name TEXT)";
    }

    public String addUser() {
        return "insert into users (login, password, name) values (?, ?, ?)";
    }

    public String removeUserById() {
        return "delete from users where id = ?";
    }

    public String removeUserByLogin() {
        return "delete from users where login = ?";
    }

    public String selectUserById() {
        return  "select id, login, password, name from users where id = ?";
    }

    public String selectUserByLogin() {
        return  "select id, login, password, name from users where login = ?";
    }

    public String selectUsers() {
        return  "select id, login, name from users";
    }

    public String updateName() {
        return "update users SET name = ? where login = ?";
    }
}
