package org.springframework.data.proxy;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.lang.reflect.Method;

import org.junit.Test;

import com.thoughtworks.proxy.Invoker;
import com.thoughtworks.proxy.factory.StandardProxyFactory;
import com.thoughtworks.proxy.toys.decorate.DecoratingInvoker;
import com.thoughtworks.proxy.toys.decorate.Decorator;

public class ProxyFactoryTest {
    

    Decorator<CalculatorService> decorator = new Decorator<CalculatorService>() {
        @Override
        public Object[] beforeMethodStarts(CalculatorService proxy, Method method, Object[] args) {
            System.out.println("before 1");
            return args;
        }
        
        @Override
        public Object decorateResult(CalculatorService proxy, Method method, Object[] args,
                Object result) {
            System.out.println("after 1");
            return result;
        }
    };
    
    
    @Test
    public void createProxy() {
        StandardProxyFactory factory = new StandardProxyFactory();
        CalculatorServiceImpl impl = new CalculatorServiceImpl();
        Invoker invoker = new DecoratingInvoker<CalculatorService>(impl, decorator);
        
        
        CalculatorService proxy = factory.createProxy(invoker, CalculatorService.class);
        int result = proxy.add(2, 3);
        assertThat(result, is(5));
    }

    @Test
    public void createInvokerChain() {
        StandardProxyFactory factory = new StandardProxyFactory();
        CalculatorServiceImpl impl = new CalculatorServiceImpl();
        Invoker invoker1 = new DecoratingInvoker<CalculatorService>(impl, decorator);
        
        Invoker invoker2 = new DecoratingInvoker<CalculatorService>(invoker1, new Decorator() {}) {
            
            @Override
            public Object invoke(Object arg0, Method arg1, Object[] arg2) throws Throwable {
                System.out.println("before 2");
                Object result =  super.invoke(arg0, arg1, arg2);
                System.out.println("after 2");
                return result;
            }
            
        };
        
        
        CalculatorService proxy = factory.createProxy(invoker2, CalculatorService.class);
        int result = proxy.add(2, 3);
        assertThat(result, is(5));
    }

}
