package lesson_2hw;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class ChatFrame extends JFrame {
    private TextField textField;
    private TextField UsersField;
    private TextField userInput;
    private JButton send;
    private Chat chat;

    ChatFrame(Chat chat){
        this.chat=chat;
        this.setSize(600,600);
        textField = new TextField();
        UsersField = new TextField();
        userInput=new TextField();
        send =new JButton("Отправить");
        textField.setEditable(false);
        UsersField.setEditable(false);
        send.addActionListener((actionEvent)->{
            String message =userInput.getText();
            userInput.setText("");
            User user =chat.user;
            message+="                  from:"+user.getNick_name();
            try {
                chat.getDataBase().sendMessage(message);
            } catch (SQLException e) {
                e.printStackTrace();
            }

        });
        this.add(textField,BorderLayout.CENTER);
        this.add(UsersField,BorderLayout.WEST);
        BorderLayout panel =new BorderLayout();
        this.setLocationRelativeTo(null);
        JPanel jPanel=new JPanel();
        jPanel.setLayout(panel);
        this.add(jPanel,BorderLayout.NORTH);
        jPanel.add(send,BorderLayout.WEST);
        jPanel.add(userInput,BorderLayout.EAST);
        this.setVisible(true);
    }

    public TextField getTextField() {
        return textField;
    }

    public TextField getUsersField() {
        return UsersField;
    }

    public TextField getUserInput() {
        return userInput;
    }

    public JButton getSend() {
        return send;
    }

    public Chat getChat() {
        return chat;
    }
}
