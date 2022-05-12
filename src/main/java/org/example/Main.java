package org.example;

import org.example.config.AppConfig;
import org.example.threads.ParseThread;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Hello world!
 */
public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        if (args.length < 1) {
            System.out.println("Не переданы файлы для парсинга");
            return;
        }
        for (String arg : args) {
            ParseThread parseThread = context.getBean("parseThread", ParseThread.class);
            try {
                parseThread.setFilePath(arg);
                parseThread.setContext(context);
                parseThread.start();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
