package org.springframework.data.proxy;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.junit.Test;

public class ProxyToysProxyFactoryTest {
    
    @Test
    public void testProxyFactory() {
        ProxyToysProxyFactory factory = new ProxyToysProxyFactory();
        CalculatorService target = new CalculatorServiceImpl();
        
        MethodInterceptor interceptor1 = new MethodInterceptor() {

            public Object invoke(MethodInvocation invocation) throws Throwable {
                System.out.println("before 1");
                Object result = invocation.proceed();
                System.out.println("after 1");
                return result;
            }            
        };
        
        MethodInterceptor interceptor2 = new MethodInterceptor() {

            public Object invoke(MethodInvocation invocation) throws Throwable {
                System.out.println("before 2");
                Object result = invocation.proceed();
                System.out.println("after 2");
                return result;
            }            
        };
        
        factory.setTarget(target);
        factory.setInterfaces(new Class<?>[] {CalculatorService.class });
        factory.addAdvice(interceptor1);
        factory.addAdvice(interceptor2);
        CalculatorService proxy = factory.getProxy();
        int result = proxy.add(2, 3);
        assertThat(result, is(5));
        
    }

}
