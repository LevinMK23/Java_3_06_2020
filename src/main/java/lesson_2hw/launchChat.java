package lesson_2hw;

import java.sql.SQLException;

public class launchChat {
    public static void main(String[] args) {
        try {
            new Chat(args[0].hashCode()); //use argument as password
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
