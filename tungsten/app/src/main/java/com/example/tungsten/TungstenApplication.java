package com.example.tungsten;

import com.example.calculator.TCalculatorService;
import com.example.tungsten.handler.TungstenAlphaServiceHandler;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocolFactory;
import org.apache.thrift.server.TServlet;
import org.apache.thrift.transport.THttpClient;
import org.apache.thrift.transport.TTransportException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Servlet;

@Configuration
@EnableAutoConfiguration
@ConfigurationProperties("tungsten")
@ComponentScan
public class TungstenApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(TungstenApplication.class, args);
    }
    
    private String calculatorEndpoint;

    public String getCalculatorEndpoint() {
        return calculatorEndpoint;
    }

    public void setCalculatorEndpoint(String calculatorEndpoint) {
        this.calculatorEndpoint = calculatorEndpoint;
    }

    @Bean
    public TProtocolFactory tProtocolFactory() {
        return new TBinaryProtocol.Factory();
    }
    
    @Bean
    public Servlet alpha(TProtocolFactory protocolFactory, TungstenAlphaServiceHandler handler) {
        return new TServlet(new TTungstenAlphaService.Processor<TungstenAlphaServiceHandler>(handler), protocolFactory);
    }
    
    @Bean
    public THttpClient calculatorHttpClient() throws TTransportException {
        return new THttpClient(calculatorEndpoint);
    }
    
    @Bean
    public TCalculatorService.Client calculatorClient(THttpClient calculatorClient) {
        TBinaryProtocol protocol = new TBinaryProtocol(calculatorClient);
        
        return new TCalculatorService.Client(protocol);
    }
}
