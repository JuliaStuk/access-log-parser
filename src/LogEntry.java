import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Locale;

public class LogEntry {
    private final String ipAddress;
    private final LocalDateTime dateTime;
    private final HttpMethod method;
    private final String path;
    private final int responseCode;
    private final int responseSize;
    private final String referer;
    private final UserAgent userAgent;

    public LogEntry(String logLine) {

        int length = logLine.length();
        if (length > 1024) {
            throw new LineException("Строка превышает 1024 символа.");
        }
        //ipaddress
        String[] fragments = logLine.split("-");
        this.ipAddress = fragments[0].trim();

        //data
        LocalDateTime dateTime = null;
        int startIndex = logLine.indexOf("[");
        if (startIndex >= 0) {
            int finishIndex = logLine.indexOf("]", startIndex);
            if (finishIndex >= 0) {
                var dateTimeStr = logLine.substring(startIndex + 1, finishIndex);
                DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                .parseCaseInsensitive()
                        .appendPattern("dd/MMM/yyyy:HH:mm:ss Z")
                        .toFormatter(Locale.ENGLISH);
                dateTime = LocalDateTime.parse(dateTimeStr, formatter);
            }
        }
        this.dateTime = dateTime;

        //method
        fragments = logLine.split( "[ \"]");
        this.method = HttpMethod.valueOf(fragments[6].trim());

        //url
        fragments = logLine.split(" ");
        this.path = fragments[6].trim();

        fragments = logLine.split(" ");
        this.responseCode = Integer.parseInt(fragments[8].trim());

        fragments = logLine.split(" ");
        this.responseSize = Integer.parseInt(fragments[9].trim());

        fragments = logLine.split("[ \"]");
        this.referer = fragments[8].trim();

        String userAgent = new String();
        int finishIndex = logLine.lastIndexOf("\"");
        if (finishIndex >= 0) {
            startIndex = new StringBuilder(logLine).reverse().toString().indexOf("\"", logLine.length() - finishIndex);
            if (startIndex >= 0) {
                userAgent = logLine.substring(logLine.length() - startIndex, finishIndex - 1);
            }
        }
        this.userAgent = new UserAgent(logLine);

    }

    public String getIpAddress() {
        return ipAddress;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public int getResponseSize() {
        return responseSize;
    }

    public String getReferer() {
        return referer;
    }
  public UserAgent getUserAgent() {
        return userAgent;
    }

    public enum HttpMethod {
        GET, POST, PUT, DELETE, PATCH, HEAD, OPTIONS, TRACE
    }
}
