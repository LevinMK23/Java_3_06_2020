package lesson2.chat;

import javax.swing.*;
import java.awt.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ChatMain extends JFrame {

    private Server srv;
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private boolean isAuth = false;

    public ChatMain(Socket socket, DataInputStream in, DataOutputStream out) throws HeadlessException {
        this.socket = socket;
        this.in = in;
        this.out = out;
        setSize(500, 500);
        setLocation(300, 300);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JPanel panel = new JPanel(new GridLayout(2, 1));
        JTextArea area = new JTextArea();
        area.setEnabled(false);
        JTextField txt = new JTextField();
        panel.add(area);
        panel.add(txt);
        add(panel);
        Thread daemonThread = new Thread(() -> {
            while (true) {
                String message = null;
                try {
                    message = in.readUTF();
                    area.append(message);
                    area.append("\n");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        daemonThread.setDaemon(true);
        daemonThread.start();
        txt.addActionListener(a -> {
            try {
                out.writeUTF(txt.getText());
                out.flush();
                txt.setText("");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        setVisible(true);
    }
}
