import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.concurrent.TimeUnit;

public class InstantTest {

    public static void main(String[] args) {
        Instant instant = Instant.now().plusMillis(TimeUnit.HOURS.toMillis(8));
        System.out.println(instant);

        LocalDate date = LocalDate.now();
        System.out.println(date);

        LocalDateTime time = LocalDateTime.now();
        String str = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(time);
        System.out.println(time);
        System.out.println(str);

        System.out.println(LocalDate.parse("2020-01-01"));
        System.out.println(LocalDateTime.parse("2020-01-01 01:01:01"));
        System.out.println();
        TemporalAccessor t = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").parse("2020-01-01 01:01:01");
        System.out.println(t);

    }

}
