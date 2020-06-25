package lesson2;

import org.sqlite.JDBC;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBHelper {

    private Statement stmt;
    private Connection conn;

    public void connect() {
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection(JDBC.PREFIX + "myDB");
            stmt = conn.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void disconnect() {
        try {
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void insert(User user) throws SQLException {
        String insertQuery = String.format("insert into users values(%d, %d, '%s', '%s');",
                user.getId(), user.getAge(), user.getName(), user.getAddress());
        stmt.execute(insertQuery);
    }

    public void delete(int userId) {
    }

    public void init() throws SQLException {
        String createTable = "create table if not exists users(id INTEGER, age INTEGER, name TEXT, address TEXT);";
        stmt.execute(createTable);
    }

    public void update(User user) {
    }

    public List<User> select() throws SQLException {
        String query = "select * from users;";
        ResultSet rs = stmt.executeQuery(query);
        ArrayList<User> users = new ArrayList<>();
        while (rs.next()) {
            String name = rs.getString("name");
            String address = rs.getString("address");
            int id = rs.getInt("id");
            int age = rs.getInt("age");
            users.add(new User(age, id, name, address));
        }
        return users;
    }
}
