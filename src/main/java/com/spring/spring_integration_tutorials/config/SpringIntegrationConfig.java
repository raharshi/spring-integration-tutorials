package com.spring.spring_integration_tutorials.config;

import java.io.File;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.InboundChannelAdapter;
import org.springframework.integration.annotation.Poller;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.file.FileReadingMessageSource;
import org.springframework.integration.file.FileWritingMessageHandler;
import org.springframework.integration.file.filters.CompositeFileListFilter;
import org.springframework.integration.file.filters.SimplePatternFileListFilter;

@Configuration
@EnableIntegration
public class SpringIntegrationConfig {

/*      Uncomment to Test Polling and independent channels



    // Poller to read the location for every second.
    // @Bean
    @InboundChannelAdapter(value = "fileInputChannel", poller= @Poller(fixedDelay = "1000"))
    public FileReadingMessageSource fileReadingMessageSource(){
        // Filter files while selection
        CompositeFileListFilter<File> filter = new CompositeFileListFilter<>();
        filter.addFilter(new SimplePatternFileListFilter("*.*"));
        // reading files 
        FileReadingMessageSource reader = new FileReadingMessageSource(); 
        reader.setDirectory(new File("C:\\Users\\raharship\\Documents\\Declarations"));
        reader.setFilter(filter);
        return reader;
    }

    @Bean
    @ServiceActivator(inputChannel = "fileInputChannel")
    public FileWritingMessageHandler fileWritingMessageHandler(){
        // writing files
        FileWritingMessageHandler writer = new FileWritingMessageHandler(new File("C:\\Users\\raharship\\Documents\\dummy"));
        writer.setAutoCreateDirectory(true);
        writer.setExpectReply(false);
        return writer;
    }

    */
}
