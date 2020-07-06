package Chat.Front;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicBoolean;

import Chat.Back.DBHelper;
import Chat.User;

public class AuthFrame extends JFrame {
    AuthFrame(){
        this.setSize(300,300);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        TextField login = new TextField("Login");
        JPasswordField passwordField=new JPasswordField("Password");
        JButton submit = new JButton("Submit");
        AtomicBoolean authComplete= new AtomicBoolean(true);
        submit.addActionListener((l)->{
            String login_ = login.getText();
            int password_ = new String  ( passwordField.getPassword() ).hashCode();
            try {
                User user=new DBHelper().getUser(password_,login_);
                switch (user.getUSER_STATE()){
                    case User.correct:
                        SwingUtilities.invokeLater(()-> {
                            try {
                                new Chat(user);
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        });
                        authComplete.set(true);
                        break;

                        case User.incorrect_login:
                            JOptionPane.showMessageDialog(this,"Incorrect login","Error",JOptionPane.ERROR_MESSAGE);
                            authComplete.set(false);
                        break;

                    case User.incorrect_password:
                        JOptionPane.showMessageDialog(this,"Incorrect password","Error",JOptionPane.ERROR_MESSAGE);
                        authComplete.set(false);
                        break;

                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            if (authComplete.get())setVisible(false);

        });

        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setVisible(true);

    }
}
