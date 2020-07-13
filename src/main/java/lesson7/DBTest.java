package lesson7;

import java.sql.SQLException;

public class DBTest {
    public static void main(String[] args) throws SQLException, NoSuchFieldException, IllegalAccessException {
        //DBCore.createTable(Student.class);
        DBCore.insertObject(new Student("Ivan", 1, 112233));
    }
}
