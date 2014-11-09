package com.example.tungsten;

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
import com.example.calculator.CalculatorApplication;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {TungstenApplication.class, CalculatorApplication.class})
@WebAppConfiguration
@IntegrationTest({"server.port:9000", "tungsten.calculator_endpoint:http://localhost:9000/calculator/"})
public class TungstenApplicationTest {
    
    @Autowired
    protected TProtocolFactory protocolFactory;

    @Value("${local.server.port}")
    protected int port;

    protected TTungstenAlphaService.Client client;

    @Before
    public void setUp() throws Exception {
        THttpClient tHttpClient = new THttpClient("http://localhost:" + port + "/alpha/");

        TProtocol protocol = protocolFactory.getProtocol(tHttpClient);

        client = new TTungstenAlphaService.Client(protocol);
    }
    
    @Test
    public void testResistance() throws Exception {
        assertEquals(10, client.resistance(TCircuit.SERIES, 3, 7));
        
        assertEquals(6, client.resistance(TCircuit.PARALLEL, 15, 10));
    }

    @Test(expected = TIllegalResistorValueException.class)
    public void testNegativeResistorValue() throws Exception {
        client.resistance(TCircuit.SERIES, -3, 7);
    }

    @Test(expected = TIllegalResistorValueException.class)
    public void testIllegalResistorValue() throws Exception {
        client.resistance(TCircuit.PARALLEL, 0, 0);
    }
}