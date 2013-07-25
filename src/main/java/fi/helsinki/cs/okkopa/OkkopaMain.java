package fi.helsinki.cs.okkopa;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class OkkopaMain {

    public static void main(String[] args) {
        ScheduledExecutorService scheduler =
                Executors.newScheduledThreadPool(1);
        OkkopaRunner runner = new OkkopaRunner();
        scheduler.scheduleAtFixedRate(runner, 0, 1000, TimeUnit.SECONDS);
    }
}