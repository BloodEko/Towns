package de.bloodeko.towns;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;

public class TimeDemo {
    
    public static void main(String[] args) throws Exception {
        System.out.printf("now: %s%n", LocalDateTime.now());

        System.out.printf("Apr 15, 1994 @ 11:30am: %s%n",
                          LocalDateTime.of(1994, Month.APRIL, 15, 11, 30));

        System.out.printf("now (from Instant): %s%n",
                          LocalDateTime.ofInstant(Instant.now(), ZoneId.systemDefault()));

        System.out.printf("6 months from now: %s%n",
                          LocalDateTime.now().plusMonths(6));

        System.out.printf("6 months ago: %s%n",
                          LocalDateTime.now().minusMonths(6));
    }
    
    public static void display(int hour, int minute, int second) {
        System.out.println("hour:" + hour + " minute:" + minute + " second:" + second);
    }
    
    public static void testDuration() {
        Duration duration = Duration.ofMillis(5);
        System.out.println(duration.toString()
          .replace("PT", "")
          .replace("S", " Sekunden ")
          .replace("M", " Minuten ")
          .replace("H", " Stunden "));
    }
}
