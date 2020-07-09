package Chat.Back;

import java.io.IOException;
import java.net.ServerSocket;
import Chat.Socket;
import com.sun.security.ntlm.Client;

import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server_app {
  ServerSocket server;
    LinkedList<Thread> users_threads =new LinkedList<>();
    ExecutorService executorService = Executors.newFixedThreadPool(10);
    LinkedList<Socket> userOnline=new LinkedList<>();
    Server_app() throws IOException {
        server=new ServerSocket(Socket.port);
        Thread listen_for_user_auth=new Thread(()->{
            while (!executorService.isShutdown()){
                try {
                    java.net.Socket socket=server.accept();
                    executorService.execute((()->{
                        Socket client=(Socket)socket;
                        userOnline.add(client);
                        while (!executorService.isShutdown())
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
                    }));

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        listen_for_user_auth.start();
        // TODO: 7/9/2020 add button for shutdown
        executorService.shutdown();
    }
}
