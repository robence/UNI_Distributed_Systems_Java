package com.company;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

class TimeLogger {
    static void logTime(LocalDateTime startedAt) {
        LocalDateTime now = LocalDateTime.now();
        long millis = ChronoUnit.MILLIS.between(startedAt, now);

        System.out.println("Started At: " + startedAt);
        System.out.println("Finished At " + now);
        System.out.println("Elapsed Time: " + millis + " millis");
    }
}
