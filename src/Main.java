import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int lineCount = 0;
        String line;
        String path = new Scanner(System.in).nextLine();
        File file = new File(path);
        boolean fileExist = file.exists();
        boolean isDirectory = file.isDirectory();
        Statistics statistic = new Statistics();

        if (!fileExist || isDirectory) {
            System.out.println("Путь к папке или несуществующему файлу");
        }
        try {
            FileReader fileReader = new FileReader(path);
            BufferedReader reader =
                    new BufferedReader(fileReader);

            while ((line = reader.readLine()) != null) {
                lineCount++;

                LogEntry logEntry = new LogEntry(line);
                statistic.addEntry(logEntry);

                System.out.println("IP Address: " + logEntry.getIpAddress());
                System.out.println("Date and Time: " + logEntry.getDateTime().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
                System.out.println("HTTP Method: " + logEntry.getMethod());
                System.out.println("Path: " + logEntry.getPath());
                System.out.println("Response Code: " + logEntry.getResponseCode());
                System.out.println("Response Size: " + logEntry.getResponseSize());
                System.out.println("Referer: " + logEntry.getReferer());
                System.out.println("Operating System: " + logEntry.getUserAgent().getOperatingSystem());
                System.out.println("Browser: " + logEntry.getUserAgent().getBrowser());
                System.out.println("Total Traffic: " + statistic.getTotalTraffic());
            }
        } catch (LineException lx) {
            lx.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

class LineException extends RuntimeException {
    public LineException(String message) {
        super(message);
    }
}