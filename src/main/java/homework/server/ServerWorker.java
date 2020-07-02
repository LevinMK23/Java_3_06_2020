package homework.server;

import homework.utils.Logger;

import java.net.ServerSocket;
import java.net.Socket;


class ServerWorker {
    private final Logger log = new Logger(ServerWorker.class);

    ServerWorker() {
        ServerSocket serverSocket = null;
        Socket socket = null;
        try {
            AuthService.dbConnect();
            serverSocket = new ServerSocket(8189);
            log.appInfo("Server", "Сервер запущен, ожидание подключений");
            while (true) {
                socket = serverSocket.accept();
                log.appInfo("Server", "Подключился клиент " + socket.getLocalAddress() + ":" + socket.getPort());
                new ServerController(this, socket);
            }
        } catch (Exception e) {
            log.appError("Server", "Ошибка запуска сервера, " + e.getMessage());
        } finally {
            try {
                if (socket != null) {
                    socket.close();
                }
                if (serverSocket != null) {
                    serverSocket.close();
                }
            } catch (Exception e) {
                log.appError("Server", "Ошибка закрытия подключения, " + e.getMessage());
            }
            AuthService.dbDisconnect();
        }
    }



}
