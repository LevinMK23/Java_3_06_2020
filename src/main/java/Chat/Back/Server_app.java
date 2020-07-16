package Chat.Back;

import java.io.IOException;
import java.net.ServerSocket;
import Chat.Socket;
import com.sun.security.ntlm.Client;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server_app {
  ServerSocket server;
    Logger logger = Logger.getLogger(Server_app.class);

    LinkedList<Thread> users_threads =new LinkedList<>();
    ExecutorService executorService = Executors.newFixedThreadPool(10);
    LinkedList<Socket> userOnline=new LinkedList<>();
    Server_app() throws IOException {
        BasicConfigurator.configure();
        logger.info("server started");
        server=new ServerSocket(Socket.port);
        Thread listen_for_user_auth=new Thread(()->{
            while (!executorService.isShutdown()){
                try {
                    java.net.Socket socket=server.accept();
                    logger.info("client with ip: "+socket.getInetAddress()+" connected");
                    executorService.execute((()->{
                        Socket client=(Socket)socket;
                        userOnline.add(client);
                        while (!executorService.isShutdown())
                        try {
                            String message=client.getReader().readLine();
                            logger.info("Message received : "+message);
                            userOnline.stream().filter((user)->!(user).equals(client)).forEach((user)-> {
                                try {
                                    user.write_in(message);
                                } catch (IOException e) {
                                    logger.error(e);
                                }
                            });
                        } catch (IOException e) {
                            logger.error(e);
                        }
                    }));

                } catch (IOException e) {
                    logger.error(e);
                }
            }
        });
        listen_for_user_auth.start();
        // TODO: 7/9/2020 add button for shutdown
        logger.info("server is shutting down");
        executorService.shutdown();
    }
}
