package homework.client;

import homework.utils.Logger;
import homework.api.Auth;
import homework.api.Event;
import homework.api.Message;

import com.google.gson.Gson;

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
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class ClientController implements Initializable {
    @FXML
    TextArea chatArea;
    @FXML
    TextField msgField;
    @FXML
    TextField nameField;
    @FXML
    TextField eventField;
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
    private boolean isAuthorised = false;
    private ClientWorker worker = new ClientWorker();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setAuthorised(false);
        try {
            clientSocket = new Socket(IP, PORT);
            log.appInfo("initialize", "Установлено подключение к серверу " + clientSocket.getInetAddress() + ":" + clientSocket.getPort());
            Thread waitLogin = new Thread(() -> {
                try {
                    Thread.sleep(30000);
                    if (!isAuthorised) {
                        clientSocket.close();
                        isConnected = false;
                        log.appInfo("initialize", "Подключение закрыто по таймауту");
                    }
                } catch (Exception e) {
                    log.appError("initialize", "Ошибка во время работы, " + e.getMessage());
                }
            });
        waitLogin.setDaemon(true);
        waitLogin.start();
        } catch (Exception e) {
            log.appError("initialize", "Ошибка подключения к серверу, " + e.getMessage());
        }
    }

    public void login() {
        try {
            if (Objects.isNull(clientSocket) || clientSocket.isClosed()) {
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
                                checkHistory();
                                log.appInfo("login", "Пользователь авторизован");
                                setAuthorised(true);
                                break;
                            }
                            case "/newMessage": {
                                chatArea.appendText(message.getBody());
                                worker.writeHistory(message.getBody());
                                break;
                            }
                            case "/clientList": {
                                String[] clients = message.getBody().replace("[", "").replace("]", "").split(",");
                                updateClientList(clients);
                                break;
                            }
                            case "/event": {
                                Event event = gson.fromJson(message.getBody(), Event.class);
                                event(event);
                                break;
                            }
                            default:
                                log.appError("connect", "Неизвестный запрос: " + message.getPath() + " - " + message.getBody());
                                break;
                        }
                    }
                } catch (Exception e) {
                    log.appError("connect", "Ошибка принятия сообщения, " + e.getMessage());
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

    private void event(Event event) {
        if (event.getType().equals(Event.Type.info)) {
            log.appInfo("connect", event.getMessage());
        } else if (event.getType().equals(Event.Type.error)) {
            log.appError("connect", event.getMessage());
        }
        eventField.setText(event.getType() + ": " + event.getMessage());
    }

    private void authentication() throws Exception {
        Auth auth = new Auth(loginField.getText(), passwordField.getText());
        out.writeUTF(gson.toJson(new Message("/auth", gson.toJson(auth))));
        log.appInfo("authentication", "Пользователь " + auth.getLogin() + " пробует залогиниться");
        loginField.clear();
        passwordField.clear();
        out.flush();
    }

    private void setAuthorised(boolean isAuthorised) {
        this.isAuthorised = isAuthorised;
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
            eventField.clear();
        }
    }

    private void checkHistory() {
        try {
            List<String> historyMessages = worker.checkHistory();
            for (String message : historyMessages) {
                chatArea.appendText(message);
            }
        } catch (Exception e) {
            log.appError("", "Ошибка считывания истории, " + e.getMessage());
        }
    }

    private void updateClientList(String[] clients) {
        Platform.runLater(() -> {
            clientList.getItems().clear();
            for (String client : clients) {
                        clientList.getItems().add(client.replaceAll(" ", ""));
                    }
        });
    }

    public void sendMessage() {
        if (!msgField.getText().replaceAll(" ", "").equalsIgnoreCase("")) {
            socketSend(gson.toJson(new Message("/newMessage", msgField.getText() + "\n")));
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
