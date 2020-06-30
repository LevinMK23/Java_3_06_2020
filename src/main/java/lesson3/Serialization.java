package lesson3;

import lesson2.User;

import java.io.*;

public class Serialization {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
//        User user = new User(1, "Ivan", "123");
//        User user1 = new User(2, "Petr", "321");
//        ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(new File("file.txt"), true));
//        os.writeObject(user);
//        os.writeObject(user1);
//        os.close();
        ObjectInputStream is = new ObjectInputStream(new FileInputStream(new File("file.txt")));
        User u1 = (User) is.readObject();
        User u2 = (User) is.readObject();
        System.out.println(u1);
        System.out.println(u2);
    }
}
