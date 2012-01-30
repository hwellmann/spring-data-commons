package org.springframework.data.proxy;

import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInterceptor;

import com.thoughtworks.proxy.Invoker;

@SuppressWarnings("serial")
public class MethodInterceptorInvoker implements Invoker {
    
    private Invoker invoker;
    private MethodInterceptor interceptor;

    public MethodInterceptorInvoker(Invoker target, MethodInterceptor interceptor) {
        this.invoker = target;
        this.interceptor = interceptor;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        InvokerMethodInvocation mi = new InvokerMethodInvocation(invoker, method, args);
        Object result = interceptor.invoke(mi);
        return result;
    }

}
