package lesson_2hw;

import javax.swing.*;
import java.sql.SQLException;

public class Chat {
    private ChatFrame chatFrame;
    private DBHelper dataBase;
    User user;
    public Chat(int password) throws SQLException {
        dataBase=new DBHelper();
        user=dataBase.getUserByPassword(password);
        if (user==null){System.err.print("Incorrect password");return;}
        SwingUtilities.invokeLater(()->chatFrame=new ChatFrame(this));
        update();


    }
    private void update() throws SQLException {
        while (true){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            dataBase.getUsers().forEach((s)->chatFrame.getUsersField().setText(chatFrame.getUsersField().getText()+s));
            dataBase.getMessages().forEach((s)->chatFrame.getTextField().setText(chatFrame.getTextField().getText()+s));


        }
    }

    public DBHelper getDataBase() {
        return dataBase;
    }
}
