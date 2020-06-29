package homework2.server;

import homework2.utils.Logger;

import java.net.ServerSocket;
import java.net.Socket;


public class Server {
    private final Logger log = new Logger(Server.class);

    public Server() {
        ServerSocket serverSocket = null;
        Socket socket = null;
        try {
            AuthService.dbConnect();
            serverSocket = new ServerSocket(8189);
            log.appInfo("Server", "Server started, waiting for clients..");

            while (true) {
                socket = serverSocket.accept();
                log.appInfo("Server", "Client connected..");
                new ServerController(this, socket);
            }
        } catch (Exception e) {
            log.appError("Server", "Server initialization error, " + e.getMessage());
        } finally {
            try {
                if (socket != null) {
                    socket.close();
                }
                if (serverSocket != null) {
                    serverSocket.close();
                }
            } catch (Exception e) {
                log.appError("Server", "Socket closing error, " + e.getMessage());
            }
            AuthService.dbDisconnect();
        }
    }



}
