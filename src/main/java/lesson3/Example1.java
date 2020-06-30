package lesson3;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Example1 {
    public static void main(String[] args) throws IOException {
        File file = new File("./reki.png");
        File copy = new File("./file.png");
        copy.createNewFile();
        System.out.println("length: " + file.length());
        //file.createNewFile();
        // file -> имя файла и байты данных
        // прочтем байты из файлы
        FileInputStream in = new FileInputStream(file);
        FileOutputStream out = new FileOutputStream(copy);
        long start = System.currentTimeMillis();
        byte [] buffer = new byte[8192];
        while (in.available() > 0) {
            int readed = in.read(buffer);
            out.write(buffer, 0, readed);
        }
        long end = System.currentTimeMillis();
        System.out.println("time: " + (end - start) + " ms.");
        out.close();
        //запишем байты из одного файла в другой
        System.out.println();
    }
}
