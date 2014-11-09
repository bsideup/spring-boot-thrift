package com.example.calculator;

import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.protocol.TProtocolFactory;
import org.apache.thrift.transport.THttpClient;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = CalculatorApplication.class)
@WebAppConfiguration
@IntegrationTest("server.port:0")
public class CalculatorApplicationTest {
    
    @Autowired
    protected TProtocolFactory protocolFactory;

    @Value("${local.server.port}")
    protected int port;
    
    protected TCalculatorService.Client client;

    @Before
    public void setUp() throws Exception {
        THttpClient tHttpClient = new THttpClient("http://localhost:" + port + "/calculator/");
        
        TProtocol protocol = protocolFactory.getProtocol(tHttpClient);
        
        client = new TCalculatorService.Client(protocol);
    }

    @Test
    public void testAdd() throws Exception {
        assertEquals(5, client.calculate(2, 3, TOperation.ADD));
    }

    @Test
    public void testSubtract() throws Exception {
        assertEquals(3, client.calculate(5, 2, TOperation.SUBTRACT));
    }
    
    @Test
    public void testMultiply() throws Exception {
        assertEquals(10, client.calculate(5, 2, TOperation.MULTIPLY));
    }
    
    @Test
    public void testDivide() throws Exception {
        assertEquals(2, client.calculate(10, 5, TOperation.DIVIDE));
    }
    
    @Test(expected = TDivisionByZeroException.class)
    public void testDivisionByZero() throws Exception {
        client.calculate(10, 0, TOperation.DIVIDE);
    }
}