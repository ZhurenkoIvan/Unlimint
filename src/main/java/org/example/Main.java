package org.example;

import org.example.config.AppConfig;
import org.example.threads.ParseThread;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Hello world!
 *
 */
public class Main
{
    public static void main(String[] args )
    {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        String currentDirectory = System.getProperty("user.dir");
        if (args.length < 1) {
            System.out.println("Не переданы файлы для парсинга");
            return;
        }
        for (String arg : args) {
            ParseThread parseThread = context.getBean("parseThread", ParseThread.class);
            parseThread.setFilePath(currentDirectory + "\\" + arg);
            parseThread.setContext(context);
            parseThread.start();
        }
    }
}
