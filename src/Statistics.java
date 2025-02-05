import java.time.Duration;
import java.time.LocalDateTime;

public class Statistics {
    long totalTraffic;
    LocalDateTime minTime;
    LocalDateTime maxTime;

    public Statistics() {
        totalTraffic = 0;
        minTime = null;
        maxTime = null;

    }

    public void addEntry(LogEntry logEntry){
        totalTraffic += logEntry.getResponseSize();
        if (minTime == null || logEntry.getDateTime().isBefore(minTime)) {
            minTime = logEntry.getDateTime();
        }
        if (maxTime == null || logEntry.getDateTime().isAfter(maxTime)) {
            maxTime = logEntry.getDateTime();
        }
    }
    public int  getTrafficRate(){
        if (minTime != null || maxTime != null ){
        Duration duration = Duration.between(minTime, maxTime);
        long diffInHours = duration.toHours();
        return (int) (totalTraffic/diffInHours);
        }
        return 0;
    }

    public long getTotalTraffic() {
        return totalTraffic;
    }

    public LocalDateTime getMinTime() {
        return minTime;
    }

    public LocalDateTime getMaxTime() {
        return maxTime;
    }
}
