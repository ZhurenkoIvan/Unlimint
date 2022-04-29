package org.example;

import org.example.config.AppConfig;
import org.example.threads.ParseThread;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Hello world!
 */
public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        String currentDirectory = System.getProperty("user.dir");
        if (args.length < 1) {
            System.out.println("Не переданы файлы для парсинга");
            return;
        }
        for (String arg : args) {
            ParseThread parseThread = context.getBean("parseThread", ParseThread.class);
            try {
                parseThread.setFilePath(getFileDirectory(currentDirectory, arg));
                parseThread.setContext(context);
                parseThread.start();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    static private String getFileDirectory(String userDirectory, String arg) throws Exception {
        //Если нужно пройти вверх по папкам
        while (arg.contains("..\\")) {
            try {
                userDirectory = userDirectory.substring(0, userDirectory.lastIndexOf("\\"));
            } catch (StringIndexOutOfBoundsException e) {
                throw new Exception("Путь к файлу указан неверно. " + arg);
            }
            arg = arg.replaceFirst("\\.\\.\\\\", "\\\\");
        }
        //Если нужно пройти ниже по папкам
        if (arg.contains("\\")) {
            Pattern pattern = Pattern.compile("\\\\[A-Za-z0-9.]+");
            Matcher matcher = pattern.matcher(arg);
            while (matcher.find()) {
                if (matcher.group().contains(".")) {
                    break;
                }
                String additionalPath = matcher.group();
                userDirectory += additionalPath;
                arg = arg.replaceFirst("\\\\[A-Za-z0-9.]+", "");
            }
            userDirectory += arg;
        }
        return userDirectory;
    }
}
