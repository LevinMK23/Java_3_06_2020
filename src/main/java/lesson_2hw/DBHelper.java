package lesson_2hw;

import java.sql.*;
import java.util.LinkedList;

public class DBHelper {
    private Connection connection;

    public DBHelper() {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            connection = DriverManager.getConnection("jdbc:mysql://localhost/chat" +
                    "user=root&password=sudo");
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Exception occurred on connecting to database\n" + e.getMessage());
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }
    public LinkedList<String> getUsers() throws SQLException {
        LinkedList<String> users=new LinkedList<>();
        Statement statement=connection.createStatement();
        ResultSet set=statement.executeQuery("select 'nick' from users;");
        while (set.next()){
           users.add(set.getString("nick"));

        }
        return users;
    }
    public LinkedList<String> getMessages() throws SQLException {
        LinkedList<String> messages=new LinkedList<>();
        Statement statement=connection.createStatement();
        ResultSet set= statement.executeQuery("select 'message' from messages;");
        while (set.next()){
            messages.add(set.getString("message"));
        }
        return messages;
    }
    public User getUserByPassword(int hashPassword) throws SQLException {
        Statement statement=connection.createStatement();
        String request = "select * from users where password=?";
        PreparedStatement preparedStatement=connection.prepareStatement(request);
        preparedStatement.setInt(1,hashPassword);
        User user =null ;
        ResultSet set=preparedStatement.executeQuery();
        while (set.next()){
            String login=set.getString("login");
            String  email=set.getString("email");
            int password=set.getInt("password");
            String nick=set.getString("nick");
            user=new User(login,email,password,nick);
        }
        return user;

    }
    public void sendMessage(String message) throws SQLException {
        String request = "inset into messages (message)"+ "values(?);";
        PreparedStatement preparedStatement=connection.prepareStatement(request);
        String m=message.toLowerCase();
        if (m.contains("drop table"))return;// TODO: 7/2/2020 add data sanitation to prevent sql injection
        preparedStatement.setString(1,message);
        preparedStatement.execute();

    }
    public void changeNick(String nick,User user) throws SQLException {
        user.setNick_name(nick);
        String request = "update users set nick = ?"+ "where login=?";
        PreparedStatement preparedStatement=connection.prepareStatement(request);
        preparedStatement.setString(1,nick);
        preparedStatement.setString(2,user.getLogin());
        preparedStatement.execute();
    }

}
