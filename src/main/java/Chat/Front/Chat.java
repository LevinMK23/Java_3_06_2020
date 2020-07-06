package Chat.Front;

import Chat.User;
import lombok.SneakyThrows;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.sql.SQLException;

public class Chat {
    private PrintWriter writer;
    private File log_file;
    private ChatFrame chatFrame;
    private User user;
    private client client_socket;
    @SneakyThrows
    public Chat(User user) throws SQLException {
        log_file=new File("logs.txt");
        log_file.createNewFile();
        writer=new PrintWriter(log_file);
        this.user=user;
        client_socket=new client();
        SwingUtilities.invokeLater(()->chatFrame=new ChatFrame(this));
        append_logs();
        update();


    }
    private void update() throws SQLException {

        while (true){
            try {
                Thread.sleep(100);
                String Line= client_socket.getReader().readLine();

                chatFrame.getTextField().setText(chatFrame.getUsersField().getText() + Line);
            } catch (IOException | InterruptedException es) {
                es.printStackTrace();
            }
            //dataBase.getUsers().forEach((s)->chatFrame.getUsersField().setText(chatFrame.getUsersField().getText()+s));
            //dataBase.getMessages().forEach((s)->chatFrame.getTextField().setText(chatFrame.getTextField().getText()+s));


        }
    }
    public void sendMessage(String message){
        try {
            client_socket.write_in(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public User getUser(){
        return user;
    }
    public void log(String m){
        writer.append(m);
    }
    public void append_logs() throws IOException {
        File file=new File("logs.txt");
        Process p =Runtime.getRuntime().exec("tail -"+100+" "+file);
        BufferedReader reader =new BufferedReader(new InputStreamReader(p.getInputStream()));
        StringBuilder builder=new StringBuilder();
        String l=null;
        while ((l=reader.readLine())!=null){
            builder.append(l);
        }
        chatFrame.getTextField().setText(builder.toString());
    }

   /* public DBHelper getDataBase() {
        return dataBase;
    }*/
}
