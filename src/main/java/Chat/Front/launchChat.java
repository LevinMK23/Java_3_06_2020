package Chat.Front;

import Chat.Front.Chat;

import javax.swing.*;
import java.sql.SQLException;

public class launchChat {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(()-> {
           new AuthFrame();
        });

    }
}
