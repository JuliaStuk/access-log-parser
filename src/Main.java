import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int lineCount = 0;
        int maxLength = 0;
        int minLength = Integer.MAX_VALUE;
        String path = new Scanner(System.in).nextLine();
        File file = new File(path);
        boolean fileExist = file.exists();
        boolean isDirectory = file.isDirectory();

        if (!fileExist || isDirectory) {
            System.out.println("Путь к папке или несуществующему файлу");
        }
        try {
            FileReader fileReader = new FileReader(path);
            BufferedReader reader =
                    new BufferedReader(fileReader);
            String line;
            while ((line = reader.readLine()) != null) {
                lineCount++;
                int length = line.length();
                if (length > maxLength) {
                    maxLength = length;
                }
                if (length < minLength) {
                    minLength = length;
                }
                if (length > 500) {
                    throw new LineException("Строка превышает 1024 символа.");
                }
            }
        } catch (LineException lx) {
            lx.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        System.out.println("Общее количество строк в файле: " + lineCount);
        System.out.println("Длина самой длинной строки: " + maxLength);
        System.out.println("Длина самой короткой строки: " + minLength);
    }
}

class LineException extends RuntimeException {
    public LineException(String message) {
        super(message);
    }
}