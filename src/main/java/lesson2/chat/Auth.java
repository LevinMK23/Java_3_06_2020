package lesson2.chat;

import javax.swing.*;
import java.awt.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Auth extends JFrame {

    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;

    public Auth() throws HeadlessException, IOException {
        socket = new Socket("localhost", 8189);
        in = new DataInputStream(socket.getInputStream());
        out = new DataOutputStream(socket.getOutputStream());
        setSize(500, 500);
        JPanel panel = new JPanel(new GridLayout(4, 1));
        JTextField login = new JTextField(), password = new JTextField();
        JButton send = new JButton("auth");
        JLabel label = new JLabel();
        panel.add(label);
        panel.add(login);
        panel.add(password);
        panel.add(send);
        add(panel);
        send.addActionListener(event -> {
            try {
                String request = String.format("./auth %s %s", login.getText(), password.getText());
                out.writeUTF(request);
                out.flush();
                String response = in.readUTF();
                label.setText(response);
                if (response.equals("OK")) {
                    new JFrame();
                    dispose();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) throws IOException {
        new Auth();
    }
}
