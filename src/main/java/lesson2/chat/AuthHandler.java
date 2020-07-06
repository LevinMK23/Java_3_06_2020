package lesson2.chat;

import lesson2.User;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.sql.SQLException;

public class AuthHandler implements Runnable {

    private Server srv;
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private boolean isAuth = false;

    public boolean isAuth() {
        return isAuth;
    }

    public AuthHandler(Server srv, Socket socket) throws IOException {
        this.srv = srv;
        this.socket = socket;
        in = new DataInputStream(socket.getInputStream());
        out = new DataOutputStream(socket.getOutputStream());
    }

    @Override
    public void run() {
        while (true) {
            try {
                String clientRequest = in.readUTF();
                System.out.println("Client request: " + clientRequest);
                String[] params = clientRequest.split(" ");
                String login = params[1];
                String password = params[2];
                User user = srv.getHelper().select(login);
                if (user == null) {
                    out.writeUTF("user is not exists");
                    out.flush();
                } else if (!user.getPassword().equals(password)) {
                    out.writeUTF("wrong password");
                    out.flush();
                } else {
                    out.writeUTF("OK");
                    out.flush();
                    break;
                }
            } catch (IOException | SQLException e) {
                e.printStackTrace();
            }
        }

        while (true) {
            String message = null;
            try {
                message = in.readUTF();
                System.out.println("readed: " + message);
                srv.broadCast(message);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        // Simple chat activity
    }

    public void sendMessage(String message) throws IOException {
        out.writeUTF(message);
        out.flush();
    }
}
