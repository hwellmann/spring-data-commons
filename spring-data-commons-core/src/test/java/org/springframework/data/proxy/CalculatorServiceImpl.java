package org.springframework.data.proxy;

public class CalculatorServiceImpl implements CalculatorService {

    public int add(int op1, int op2) {
        System.out.println("adding");
        return op1 + op2;
    }

}
