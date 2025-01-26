import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int lineCount = 0;
        int ya = 0;
        int gl = 0;
        String line;
        String[] parts;
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

            String firstBrackets = new String();
            String fragment;
            while ((line = reader.readLine()) != null) {
                lineCount++;
                int length = line.length();
                if (length > 1024) {
                    throw new LineException("Строка превышает 1024 символа.");
                }
               int finishIndex = new StringBuilder(line).reverse().toString().indexOf(")");
                if (finishIndex>=0) {
                    int startIndex = new StringBuilder(line).reverse().toString().indexOf("(",finishIndex);
                    if (startIndex >= 0) {
                        firstBrackets = line.substring(line.length() - startIndex, line.length() - finishIndex - 1);
                    }
                }
               parts = firstBrackets.split(";");
                if (parts.length >= 2) {
                    fragment = parts[1].trim();
                    parts = fragment.split("/");
                //    System.out.println(parts[0]);
                }
                if (parts[0].equals("YandexBot")){
                    ya++;
                }
                if (parts[0].equals("Googlebot")){
                    gl++;
                }
            }
        } catch (LineException lx) {
            lx.printStackTrace();
        } catch (Exception ex) {
  //          System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!");
            ex.printStackTrace();
        }

        System.out.println("Общее количество YandexBot: " + ya);
        System.out.println("Общее количество Googlebot: " + gl);
        System.out.println("Доля запросов YandexBot: " + (double)ya/lineCount);
        System.out.println("Доля запросов Googlebot: " + (double)gl/lineCount);
        System.out.println("Общее количество строк в файле: " + lineCount);
    }
}

class LineException extends RuntimeException {
    public LineException(String message) {
        super(message);
    }
}