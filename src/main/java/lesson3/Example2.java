package lesson3;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Example2 {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner sc = new Scanner(new File("file.txt"));
        while (sc.hasNext()) {
            System.out.println(sc.next());
        }
        PrintWriter pr = new PrintWriter(new FileOutputStream(new File("file.txt"), true));
        pr.println("Hello world");
        pr.close();
    }
}
