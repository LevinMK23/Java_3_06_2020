package lesson7;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBCore {

    private static Connection conn;
    private static Statement stmt;

    static {
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\mlev1219\\IdeaProjects\\cs_may\\Java_3_06_2020\\myDB");
            stmt = conn.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void createTable(Class<?> clazz) throws SQLException {
        if (clazz.isAnnotationPresent(Table.class)) {
            StringBuilder sqlQuery = new StringBuilder();
            sqlQuery.append("CREATE TABLE IF NOT EXISTS ")
                    .append(clazz.getDeclaredAnnotation(Table.class).name())
                    .append("(");
            for (Field field : clazz.getDeclaredFields()) {
                if (field.isAnnotationPresent(TableField.class)) {
                    sqlQuery.append(field.getName())
                            .append(" ")
                            .append(field.getDeclaredAnnotation(TableField.class).sqlType())
                            .append(", ");
                }
            }
            sqlQuery.delete(sqlQuery.length() - 2, sqlQuery.length());
            sqlQuery.append(");");
            System.out.println(sqlQuery);
            stmt.execute(sqlQuery.toString());
        }
    }

    private static Object toSQLText(Object o) {
        if (o instanceof String) {
            return "'" + o + "'";
        }
        return o;
    }

    public static void insertObject(Object o) throws NoSuchFieldException, IllegalAccessException, SQLException {
        Class<?> clazz = o.getClass();
        if (clazz.isAnnotationPresent(Table.class)) {
            StringBuilder query = new StringBuilder("insert into ");
            query.append(clazz.getDeclaredAnnotation(Table.class).name()).append(" values(");
            if (clazz.isAnnotationPresent(Table.class)) {
                for (Field field : clazz.getDeclaredFields()) {
                    if (field.isAnnotationPresent(TableField.class)) {
                        field.setAccessible(true);
                        Object value = field.get(o);
                        query.append(toSQLText(value)).append(", ");
                    }
                }
            }
            query.delete(query.length() - 2, query.length()).append(");");
            System.out.println(query);
            // clazz.getDeclaredField("studentId").get(o);
            // String insertQuery = "insert into ? (?,?,?.....) values(?,?,?....)";
            stmt.execute(query.toString());
        }
    }
}
