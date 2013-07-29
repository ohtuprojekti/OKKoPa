package fi.helsinki.cs.okkopa;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class OkkopaMain {

    public static void main(String[] args) {
        
        ApplicationContext ctx = new FileSystemXmlApplicationContext("src/main/resources/spring-context.xml");
        ctx.getBean("okkopaRunner");
        
     //   Settings st = (Settings) ctx.getBean("productionSettings");
     //   System.out.println(st.getSettings().getProperty("mail.imap.host"));
        
        ScheduledExecutorService scheduler =
                Executors.newScheduledThreadPool(1);
        OkkopaRunner runner = (OkkopaRunner) ctx.getBean("okkopaRunner");
        scheduler.scheduleAtFixedRate(runner, 0, 60, TimeUnit.SECONDS);
    }
}