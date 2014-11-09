package com.example.tungsten.handler;

import com.example.calculator.TCalculatorService;
import com.example.calculator.TDivisionByZeroException;
import com.example.calculator.TOperation;
import com.example.tungsten.TCircuit;
import com.example.tungsten.TIllegalResistorValueException;
import com.example.tungsten.TTungstenAlphaService;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TungstenAlphaServiceHandler implements TTungstenAlphaService.Iface {
    
    @Autowired
    TCalculatorService.Client calculatorClient;
    
    @Override
    public int resistance(TCircuit circuit, int r1, int r2) throws TException {
        if(r1 < 0 || r2 < 0) {
            throw new TIllegalResistorValueException();
        }
        
        switch(circuit) {
            case PARALLEL:
                int dividend = calculatorClient.calculate(r1, r2, TOperation.MULTIPLY);
                
                int divisor = calculatorClient.calculate(r1, r2, TOperation.ADD);
                
                try {
                    return calculatorClient.calculate(dividend, divisor, TOperation.DIVIDE);
                } catch(TDivisionByZeroException e) {
                    throw new TIllegalResistorValueException();
                }
            case SERIES:
                return calculatorClient.calculate(r1, r2, TOperation.ADD);
            default:
                throw new TException("Unknown circuit " + circuit);
        }
    }
}
