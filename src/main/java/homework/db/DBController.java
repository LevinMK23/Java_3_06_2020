package homework.db;

import homework.utils.Logger;

import org.sqlite.JDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class DBController {
    private final Logger log = new Logger(DBController.class);
    private PreparedStatement statement;
    private Connection connection;
    private SQLRequest request = new SQLRequest();

    public void connect() {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(JDBC.PREFIX + "sqlDB");
            createTable(request.createTable());
            log.appInfo("connect", "Создано подключение к базе данных " + connection.getMetaData().getURL());
        } catch (Exception e) {
            log.appError("connect", "Ошибка подключения, " + e.getMessage());
        }
    }

    public void createTable(String sql) {
        try {
            connection.prepareStatement(sql).execute();
            log.appInfo("createTable", "Проверено существование таблицы");
        } catch (Exception e) {
            log.appError("createTable", "Ошибка запроса к таблице, " + e.getMessage());
        } finally {
            closeStatement();
        }
    }

    public void disconnect() {
        try {
            closeStatement();
            connection.close();
        } catch (Exception e) {
            log.appError("disconnect", "Ошибка закрытия подключения, " + e.getMessage());
        }
    }

    public void addUser(User user, String sql) {
        try {
            statement = connection.prepareStatement(sql);
            statement.setString(1, user.getLogin());
            statement.setInt(2, user.getPassword().hashCode());
            statement.setString(3, user.getName());
            statement.execute();
            log.appInfo("addUser", "Создан пользовтаель " + user.getLogin());
        } catch (Exception e) {
            log.appError("addUser", "Ошибка создания пользователя, " + e.getMessage());
        } finally {
            closeStatement();
        }
    }

    public void removeUserByLogin(String login, String sql) {
        try {
            statement = connection.prepareStatement(sql);
            statement.setString(1, login);
            int isUpdate = statement.executeUpdate();
            if (Objects.equals(isUpdate, 0)) {
                log.appWarning("removeUser", "Пользователь " + login + " не найден");
            } else {
                log.appInfo("removeUser", "Пользователь " + login + " удален");
            }
        } catch (Exception e) {
            log.appError("removeUser", "Ошибка удаления пользователя, " + e.getMessage());
        } finally {
            closeStatement();
        }
    }

    public void removeUserById(int id, String sql) {
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            int isUpdate = statement.executeUpdate();
            if (Objects.equals(isUpdate, 0)) {
                log.appWarning("removeUser", "Пользователь " + id + " не найден");
            } else {
                log.appInfo("removeUser", "Пользователь " + id + " удален");
            }
        } catch (Exception e) {
            log.appError("removeUser", "Ошибка удаления пользователя, " + e.getMessage());
        } finally {
            closeStatement();
        }
    }

    public User selectUserById(int id, String sql) {
        User user = new User();
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                user.setId(result.getInt("id"));
                user.setLogin(result.getString("login"));
                user.setName(result.getString("name"));
                return user;
            } else {
                log.appError("selectUser", "Пользователь " + id + " не найден");
                return user;
            }
        } catch (Exception e) {
            log.appError("selectUser", "Ошибка считывание пользователя, " + e.getMessage());
            return user;
        } finally {
            closeStatement();
        }
    }

    public User selectUserByLogin(String login, String sql) {
        User user = new User();
        try {
            statement = connection.prepareStatement(sql);
            statement.setString(1, login);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                user.setId(result.getInt("id"));
                user.setLogin(result.getString("login"));
                user.setName(result.getString("name"));
                user.setPassword(String.valueOf(result.getInt("password")));
                return user;
            } else {
                log.appError("selectUser", "Пользователь " + login + " не найден");
                return user;
            }
        } catch (Exception e) {
            log.appError("selectUser", "Ошибка считывание пользователя, " + e.getMessage());
            return user;
        } finally {
            closeStatement();
        }
    }



    public List<User> selectUsers(String sql) {
        ArrayList<User> users = new ArrayList<>();
        try {
            ResultSet result = connection.prepareStatement(sql).executeQuery();
            while (result.next()) {
                users.add(new User(result.getInt("id"), result.getString("login"), result.getString("name")));
            }
            return users;
        } catch (Exception e) {
            log.appError("selectUsers", "Ошибка считывание пользователей, " + e.getMessage());
            return null;
        } finally {
            closeStatement();
        }
    }

    public void updateName(String name, String newName, String sql) {
        try {
            statement = connection.prepareStatement(sql);
            statement.setString(1, newName);
            statement.setString(2, name);
            int isUpdate = statement.executeUpdate();
            if (Objects.equals(isUpdate, 0)) {
                log.appWarning("updateName", "Пользователь " + name + " не найден");
            } else {
                log.appInfo("updateName", "Имя пользователя " + name + " изменено на " + newName);
            }
        } catch (Exception e) {
            log.appError("updateName", "Ошибка изменения имени для " + name + ", " + e.getMessage());
        } finally {
            closeStatement();
        }
    }

    private void closeStatement() {
        try {
            if (Objects.nonNull(statement)) {
                statement.close();
            }
        } catch (Exception e) {
            log.appError("", "Ошибка закрытия подключения, " + e.getMessage());
        }
    }

    public Connection getConnection() {
        return connection;
    }
}