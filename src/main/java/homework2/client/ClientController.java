package homework2.client;

import com.google.gson.Gson;
import homework2.utils.Logger;
import homework2.api.Auth;
import homework2.api.Event;
import homework2.api.Message;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;


public class ClientController implements Initializable {
    @FXML
    TextArea chatArea;
    @FXML
    TextField msgField;
    @FXML
    TextField nameField;
    @FXML
    HBox upperPan;
    @FXML
    HBox bottomPan;
    @FXML
    TextField loginField;
    @FXML
    PasswordField passwordField;
    @FXML
    ListView<String> clientList;

    private final String IP = "localhost";
    private final int PORT = 8189;
    private Socket clientSocket;
    private boolean isConnected = false;
    private String login;
    private DataInputStream in;
    private DataOutputStream out;
    private Gson gson = new Gson();
    private Logger log = new Logger(ClientController.class);
    private AtomicBoolean isAuthorised = new AtomicBoolean();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setAuthorised(false);
        try {
            clientSocket = new Socket(IP, PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Thread timer = new Thread(() -> {
            try {
                Thread.sleep(30000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (!isAuthorised.get()) {
                try {
                    clientSocket.close();
                    isConnected = false;
                    log.appInfo("initialize", "Подключение закрыто по таймауту");
                } catch (IOException e) {
                    log.appError("initialize", "Ошибка закрытия подключения, " + e.getMessage());
                }
            }
        });
        timer.setDaemon(true);
        timer.start();
    }

    private void setAuthorised(boolean isAuthorised) {
        this.isAuthorised.set(isAuthorised);
        if (!isAuthorised) {
            upperPan.setVisible(true);
            upperPan.setManaged(true);
            bottomPan.setVisible(false);
            bottomPan.setManaged(false);
            clientList.setVisible(false);
            clientList.setManaged(false);
        } else {
            upperPan.setVisible(false);
            upperPan.setManaged(false);
            bottomPan.setVisible(true);
            bottomPan.setManaged(true);
            clientList.setVisible(true);
            clientList.setManaged(true);
        }
    }

    public void login() {
        try {
            if (clientSocket.isClosed()) {
                clientSocket = new Socket(IP, PORT);
            }
            if (isConnected) {
                authentication();
            } else {
                connect();
                isConnected = true;
                authentication();
            }
        } catch (Exception e) {
            log.appError("login", "Ошибка авторизации пользователя, " + e.getMessage());
        }
    }

    private void authentication() throws Exception {
        Auth auth = new Auth(loginField.getText(), passwordField.getText());
        out.writeUTF(gson.toJson(new Message("/auth", gson.toJson(auth))));
        log.appInfo("login", "Пользователь " + auth.getLogin() + " пробует залогиниться");
        loginField.clear();
        passwordField.clear();
        out.flush();
    }

    private void connect() {
        try {
            in = new DataInputStream(clientSocket.getInputStream());
            out = new DataOutputStream(clientSocket.getOutputStream());
            setAuthorised(false);

            Thread t1 = new Thread(() -> {
                try {
                    while (true) {
                        Message message = gson.fromJson(in.readUTF(), Message.class);
                        switch (message.getPath()) {
                            case "/authOK": {
                                setAuthorised(true);
                                log.appInfo("login", "Пользователь авторизован");
                                break;
                            }
                            case "/newMessage": {
                                chatArea.appendText(message.getBody());
                                break;
                            }
                            case "/clientList": {
                                String[] clients = message.getBody().replace("[", "").replace("]", "").split(",");
                                Platform.runLater(() -> {
                                    clientList.getItems().clear();
                                    for (String client : clients) {
                                        clientList.getItems().add(client.replaceAll(" ", ""));
                                    }
                                });
                                break;
                            }
                            case "/event": {
                                Event event = gson.fromJson(message.getBody(), Event.class);
                                if (event.getType().equals(Event.Type.info)) {
                                    log.appInfo("connect", event.getMessage());
                                } else if (event.getType().equals(Event.Type.error)) {
                                    log.appError("connect", event.getMessage());
                                }
                                break;
                            }
                            default:
                                log.appError("connect", "Неизвестный запрос: " + message.getPath() + " - " + message.getBody());
                                break;
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        clientSocket.close();
                        isConnected = false;
                        log.appInfo("connect", "Подключение закрыто");
                    } catch (IOException e) {
                        log.appError("connect", "Ошибка закрытия подключения, " + e.getMessage());
                    }
                    setAuthorised(false);
                }
            });
            t1.setDaemon(true);
            t1.start();
        } catch (IOException e) {
            log.appError("connect", "Ошибка работы приложения, " + e.getMessage());
        }
    }

    public void sendMsg() {
        if (!msgField.getText().replaceAll(" ", "").equalsIgnoreCase("")) {
            socketSend(gson.toJson(new Message("/newMessage", msgField.getText() + " \n")));
            msgField.clear();
            msgField.requestFocus();
        }
    }

    public void changeName() {
        if (!nameField.getText().replaceAll(" ", "").equalsIgnoreCase("")) {
            log.appInfo("changeName", "Изменяю имя на " + nameField.getText());
            socketSend(gson.toJson(new Message("/changeName", nameField.getText())));
            nameField.clear();
        }
    }

    private void socketSend(String message) {
        try {
            out.writeUTF(message);
            out.flush();
        } catch (Exception e) {
            log.appError("socketSend", "Ошибка отпрвки сообщения, " + e.getMessage());
        }
    }
}
