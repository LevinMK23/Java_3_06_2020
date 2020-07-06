package Chat.Back;

import java.io.IOException;
import java.net.ServerSocket;
import Chat.Socket;
import com.sun.security.ntlm.Client;

import java.util.LinkedList;

public class Server_app {
  ServerSocket server;
    LinkedList<Thread> users_threads =new LinkedList<>();
    LinkedList<Socket> userOnline=new LinkedList<>();
    Server_app() throws IOException {
        server=new ServerSocket(Socket.port);
        Thread listen_for_user_auth=new Thread(()->{
            while (true){
                try {
                    java.net.Socket socket=server.accept();
                    Thread t1=new Thread(()->{
                       Socket client=(Socket)socket;
                       userOnline.add(client);
                        try {
                            String message=client.getReader().readLine();
                            userOnline.stream().filter((user)->!(user).equals(client)).forEach((user)-> {
                                try {
                                    user.write_in(message);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            });
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                    t1.start();
                    users_threads.add(t1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        listen_for_user_auth.start();
    }
}
