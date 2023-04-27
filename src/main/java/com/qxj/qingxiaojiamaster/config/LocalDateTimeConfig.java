package com.qxj.qingxiaojiamaster.config;

/**
 * @author : 15754
 * @version 1.0.0
 * @since : 2023/4/27 09:29
 *
 **/

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Configuration
public class LocalDateTimeConfig {

    @Bean
    public Converter<String, LocalDateTime> localDateTimeConverter() {
        return new LocalDateTimeConverter();
    }

    public static class LocalDateTimeConverter implements Converter<String, LocalDateTime> {
        @Override
        public LocalDateTime convert(String s) {
            return LocalDateTime.parse(s, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        }
    }
}
