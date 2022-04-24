package org.example.threads;

import org.example.config.AppConfig;
import org.example.data.Order;
import org.example.enums.FileType;
import org.example.operational_classes.ParserCSV;
import org.example.operational_classes.ParserJSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Component
@Scope("prototype")
public class ParseThread extends Thread {

    private final ParserCSV parserCSV;
    private final ParserJSON parserJSON;

    private String filePath;
    private int lineCount;

    @Autowired
    public ParseThread(ParserCSV parserCSV, ParserJSON parserJSON) {
        this.parserCSV = parserCSV;
        this.parserJSON = parserJSON;
    }

    @Override
    public void run() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        List<String> file;
        try {
            file = Files.readAllLines(Paths.get(filePath));
        } catch (IOException e) {
            System.out.println("Файл не найден");
            return;
        }
        String fileFormat = filePath.substring(filePath.indexOf('.'));
        FileType fileType = checkFileType(fileFormat);
        if (fileType == null) {
            System.out.println("Формат " + fileFormat + " файлов не поддерживается. Обратитесь к разработчику");
            return;
        }

        for (String line : file) {
            lineCount++;
            Order order = null;
            String result;

            try {
                switch (fileType) {
                    case CSV:  order = parserCSV.parse(line); break;
                    case JSON: order = parserJSON.parse(line); break;
                }
                 result = "OK";
            } catch (Exception e) {
                order = new Order();
                result = e.getMessage();
            }

            ConvertThread convertThread = context.getBean("convertThreadBean", ConvertThread.class);
            convertThread.setOrder(order);
            convertThread.setResult(result);
            convertThread.setLine(lineCount);
            convertThread.setPath(filePath);
            convertThread.start();
        }
    }

    private FileType checkFileType(String fileFormat) {
        if (fileFormat.equals(".json")) {
            return FileType.JSON;
        }
        if (fileFormat.equals(".csv")) {
            return FileType.CSV;
        }
        return null;
    }

    public ParserCSV getParserCSV() {
        return parserCSV;
    }

    public ParserJSON getParserJSON() {
        return parserJSON;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
