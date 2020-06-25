package lesson2;

import java.sql.SQLException;

public class DBTest {
    public static void main(String[] args) throws SQLException {
        DBHelper helper = new DBHelper();
        helper.connect();
        helper.init();
        helper.insert(new User(19, 2, "Petr", "Zagogrodniy 12-19"));
        System.out.println(helper.select());
    }
}
