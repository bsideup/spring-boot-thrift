package com.example.calculator;

import com.example.calculator.handler.CalculatorServiceHandler;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocolFactory;
import org.apache.thrift.server.TServlet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Servlet;

@Configuration
@EnableAutoConfiguration
@ComponentScan
public class CalculatorApplication {
    public static void main(String[] args) {
        SpringApplication.run(CalculatorApplication.class, args);
    }

    @Bean
    public TProtocolFactory tProtocolFactory() {
        return new TBinaryProtocol.Factory();
    }
    
    @Bean
    public Servlet calculator(TProtocolFactory protocolFactory, CalculatorServiceHandler handler) {
        return new TServlet(new TCalculatorService.Processor<CalculatorServiceHandler>(handler),protocolFactory);
    }
}
