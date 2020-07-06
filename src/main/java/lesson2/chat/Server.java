package lesson2.chat;

import lesson2.DBHelper;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private ServerSocket srv;
    private DBHelper helper;
    private ConcurrentLinkedDeque<AuthHandler> clients = new ConcurrentLinkedDeque<>();
    private boolean isAuth = false;
    private boolean isRunning = true;

    public DBHelper getHelper() {
        return helper;
    }

    public boolean isAuth() {
        return isAuth;
    }

    public void setAuth(boolean auth) {
        isAuth = auth;
    }

    //private ConcurrentLinkedDeque<AuthHandler>.

    public Server() throws IOException {
        srv = new ServerSocket(8189);
        System.out.println("server started!");
        helper = new DBHelper();
        helper.connect();
        ExecutorService service = Executors.newFixedThreadPool(10);
        while (isRunning) {
            try {
                Socket socket = srv.accept();
                System.out.println("Client accepted!");
                AuthHandler handler = new AuthHandler(this, socket);
                clients.add(handler);
                service.execute(handler);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        service.shutdown();
    }

    public void broadCast(String message) throws IOException {
        for(AuthHandler handler : clients) {
            handler.sendMessage(message);
        }
    }

    public static void main(String[] args) throws IOException {
        new Server();
    }
}
