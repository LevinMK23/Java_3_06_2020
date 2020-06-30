package lesson2;

import java.sql.SQLException;

public class DBTest {
    public static void main(String[] args) throws SQLException {
        DBHelper helper = new DBHelper();
        helper.connect();
        helper.init();
        helper.insert(new User(1, "Petr", "123"));
        helper.insert(new User(2, "Ivan", "123"));
        System.out.println(helper.select("Petr"));
    }
}
