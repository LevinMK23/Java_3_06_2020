package homework.client;

import homework.utils.Logger;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Client extends Application {
    private Logger log = new Logger(Client.class);

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent loader = FXMLLoader.load(getClass().getResource("/client.fxml"));
        primaryStage.setTitle("Chat");
        primaryStage.setScene(new Scene(loader, 600, 400));
        primaryStage.show();
        log.appInfo("start", "Приложение успешно запущено");
    }

    public static void main(String[] args) {
        launch(args);
    }
}
