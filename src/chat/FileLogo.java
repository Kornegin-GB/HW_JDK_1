package chat;

import java.io.FileWriter;
import java.io.IOException;

public class FileLogo {
    public void readLogo(String message) {
        try (FileWriter writer = new FileWriter("logo.txt", true)) {
            // запись всей строки
            String text = message;
            writer.write(text);
            // запись по символам
            writer.append('\n');

            writer.flush();
        } catch (IOException ex) {

            System.out.println(ex.getMessage());
        }
    }
}
