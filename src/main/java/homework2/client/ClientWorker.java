package homework2.client;

import homework2.utils.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class ClientWorker extends Application {
    private Logger log = new Logger(ClientWorker.class);

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
