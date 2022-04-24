package org.example.config;

import org.example.threads.ConvertThread;
import org.example.operational_classes.Converter;
import org.example.operational_classes.ParserCSV;
import org.example.operational_classes.ParserJSON;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
@ComponentScan(basePackages = "org.example")
public class AppConfig {

    @Bean
    @Scope("prototype")
    public ConvertThread convertThreadBean() {
        return new ConvertThread(converterBean());
    }

    @Bean
    @Scope("prototype")
    public ParserCSV parserCSVBean() {
        return new ParserCSV();
    }

    @Bean
    @Scope("prototype")
    public ParserJSON parserJSONBean() {
        return new ParserJSON();
    }

    @Bean
    @Scope("prototype")
    public Converter converterBean() {
        return new Converter();
    }
}
