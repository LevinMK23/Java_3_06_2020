package homework.server;

import homework.utils.Logger;
import homework.api.Auth;
import homework.db.DBController;
import homework.db.SQLRequest;
import homework.db.User;

import java.util.Objects;


public class AuthService {
    private final static Logger log = new Logger(ServerWorker.class);
    private static DBController dbController = new DBController();
    private static SQLRequest request = new SQLRequest();

    public static void dbConnect() {
            dbController.connect();
    }

    public static void dbDisconnect() {
            dbController.disconnect();
    }

    public static void addUser(String login, String password, String username) {
        dbController.addUser(new User(login, password, login), request.addUser());
    }

    public static boolean getNameByLoginAndPass(Auth auth) {
        User user = dbController.selectUserByLogin(auth.getLogin(), request.selectUserByLogin());
        if (Objects.equals(user.getId(), 0)) {
            auth.setName(auth.getLogin());
            addUser(auth.getLogin(), auth.getPassword(), auth.getName());
            log.appInfo("getNameByLoginAndPass", "Создан пользователь " + auth.getLogin());
            return true;
        } else {
            if (Objects.equals(auth.getPassword().hashCode(), Integer.parseInt(user.getPassword()))) {
                auth.setLogin(user.getLogin());
                user = selectUserByName(auth.getLogin());
                auth.setName(user.getName());
                log.appInfo("getNameByLoginAndPass", "Пользователь " + auth.getLogin() + " найден и проверен");
                return true;
            }
        }
        log.appError("getNameByLoginAndPass", "Некорретные данные пользователя " + auth.getLogin());
        return false;
    }

    public static User selectUserByName(String login) {
        return dbController.selectUserByLogin(login, request.selectUserByLogin());
    }

    public static void updateName(String login, String newName) {
        dbController.updateName(login, newName, request.updateName());
    }
}