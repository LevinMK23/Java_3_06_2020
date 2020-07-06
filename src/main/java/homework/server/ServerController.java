package homework.server;

import com.google.gson.Gson;
import homework.api.Auth;
import homework.api.Event;
import homework.api.Message;
import homework.utils.Logger;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static homework.api.Event.Type.error;

public class ServerController {
    private ServerWorker server;
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private Gson gson = new Gson();
    private Logger log = new Logger(ServerController.class);
    private String login;
    private String userName;
    private Vector<ServerController> clientsList = new Vector<>();

    public String getUserName() {
        return userName;
    }

    ServerController(ServerWorker server, Socket socket) {
        try {
            this.server = server;
            this.socket = socket;
            this.in = new DataInputStream(socket.getInputStream());
            this.out = new DataOutputStream(socket.getOutputStream());
            ExecutorService service = Executors.newFixedThreadPool(5);
            Runnable  task =  () -> {
                while (true) {
                    try {
                        while (true) {
                            if (!Objects.equals(in.available(), 0)) {
                                Message message = gson.fromJson(in.readUTF(), Message.class);
                                switch (message.getPath()) {
                                    case "/auth":
                                        Auth auth = gson.fromJson(message.getBody(), Auth.class);
                                        boolean isUser = AuthService.getNameByLoginAndPass(auth);
                                        if (isUser) {
                                            if (!isUserConnected(auth.getLogin())) {
                                                login = auth.getLogin();
                                                userName = auth.getName();
                                                log.appInfo("ServerController", "Пользователь " + login + " подключился");
                                                subscribe(ServerController.this);
                                                socketSend(gson.toJson(new Message("/authOK", String.valueOf(true))));
                                            } else {
                                                socketSend(gson.toJson(new Message("/newMessage", "Bot: Пользователь уже авторизован \n")));
                                            }
                                        } else {
                                            socketSend(gson.toJson(new Message("/event", gson.toJson(new Event(error, "Введены некорректные данные для " + auth.getLogin())))));
                                        }
                                        break;
                                    case "/newMessage":
                                        socketSend(gson.toJson(new Message("/newMessage", login + "@" + userName + ": " + message.getBody())));
                                        break;
                                    case "/changeName":
                                        if (!message.getBody().equalsIgnoreCase("")) {
                                            AuthService.updateName(login, message.getBody());
                                            userName = message.getBody();
                                            broadcastClientList();
                                        }
                                        break;
                                    default:
                                        log.appError("ServerController", "Неизвестный запрос: " + message.getPath() + " - " + message.getBody());
                                        break;
                                }
                            }
                        }
                    } catch (Exception e) {
                        log.appError("ServerController", "Ошибка во время работы сервера " + login + ", " + e.getMessage());
                    } finally {
                        try {
                            if (Objects.nonNull(in))
                                in.close();
                            if (Objects.nonNull(out)) {
                                out.close();
                            }
                            if (!socket.isClosed())  {
                                socket.close();
                            }
                        } catch (Exception e) {
                            log.appError("ServerController", "Ошибка во время закрытия подключения с " + login + ", " + e.getMessage());
                        }
                        unsubscribe(ServerController.this);
                        broadcastMsg("Bot: " + login + "@" + userName + " вышел из чата");
                    }
                }
            };
            service.submit(task);
        } catch (Exception e) {
            log.appError("ServerController", "Ошибка работы приложения, " + e.getMessage());
        }
    }

    public void broadcastClientList() {
        try {
            List<String> clients = new ArrayList<>();
            for (ServerController client : clientsList) {
                clients.add(client.userName);
            }
            socketSend(gson.toJson(new Message("/clientList", clients.toString())));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void subscribe(ServerController client) {
        clientsList.add(client);
        broadcastClientList();
    }


    public void broadcastMsg(String msg) {
        for (ServerController o : clientsList) {
            o.socketSend(gson.toJson(new Message("/newMessage", msg + "\n")));
        }
    }

    /*public void sendPrivateMsg(String userSender, String userReceiver, String msg) {
        for (ServerController o : clientsList) {
            if (o.getUserName().equals(userReceiver) || o.getUserName().equals(userSender)) {
                o.socketSend(gson.toJson(new Message("/newMessage", userSender + " private: " + msg + "\n")));
            }
        }
    }*/

    public boolean isUserConnected(String userName) {
        for (ServerController o : clientsList) {
            if (o.userName.equals(userName)) {
                return true;
            }
        }
        return false;
    }

    public void unsubscribe(ServerController client) {
        clientsList.remove(client);
        broadcastClientList();
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
