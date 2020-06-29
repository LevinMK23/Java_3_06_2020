package homework2.db;

import homework2.utils.Logger;

import java.util.List;


public class DBWorker {
    private final static Logger log = new Logger(DBWorker.class);
    private static SQLRequest request = new SQLRequest();

    public static void main(String[] args) {
        List<User> users;
        User user = new User("Mark", "sdlka@13s", "Mark");
        DBController dbController = new DBController();
        dbController.connect();
        dbController.createTable(request.createTable());
        dbController.addUser(user, request.addUser());
        user = null;
        user = dbController.selectUserById(1, request.selectUserById());
        log.appInfo("main", "После создания пользователя - " + user.toString());
        user = dbController.selectUserByLogin("Mark", request.selectUserByLogin());
        log.appInfo("main", "Выбор по логину - " + user.toString());
        users = dbController.selectUsers(request.selectUsers());
        log.appInfo("main", "Все пользователи таблицы - " + users.toString());
        dbController.updateName("Mark", "Pavel", request.updateName());
        user = dbController.selectUserById(1, request.selectUserById());
        log.appInfo("main", "После изменения имени пользователя - " + user.toString());
        /*dbController.removeUserById(1, request.removeUserById());
        dbController.removeUserByLogin("Mark", request.removeUserByLogin());*/
        users = dbController.selectUsers(request.selectUsers());
        log.appInfo("main", "После удаления пользователя - " + users.toString());
        dbController.disconnect();
    }
}
