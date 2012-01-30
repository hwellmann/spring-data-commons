package org.springframework.data.proxy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;

import com.thoughtworks.proxy.Invoker;
import com.thoughtworks.proxy.ProxyFactory;
import com.thoughtworks.proxy.factory.StandardProxyFactory;
import com.thoughtworks.proxy.toys.delegate.DelegatingInvoker;

public class ProxyToysProxyFactory implements IProxyFactory {
    
    private Object target;
    private Class<?>[] interfaces;
    private List<MethodInterceptor> interceptors = new ArrayList<MethodInterceptor>();
    private ProxyFactory factory;
    
    public void setProxyFactory(ProxyFactory factory) {
        this.factory = factory;
    }
    
    public ProxyFactory getProxyFactory() {
        if (factory == null) {
            factory = new StandardProxyFactory();
        }
        return factory;
    }

    public void setTarget(Object target) {
        this.target = target;
    }

    public void setInterfaces(Class<?>[] interfaces) {
        this.interfaces = interfaces;
    }

    public void addAdvice(Advice advice) {
        if (advice instanceof MethodInterceptor) {
            MethodInterceptor interceptor = (MethodInterceptor) advice;
            interceptors.add(interceptor);
        }
        else {
            throw new IllegalArgumentException("advice must be a MethodInterceptor");
        }
    }

    @SuppressWarnings("unchecked")
    public <T> T getProxy() {
        Invoker decorated = new DelegatingInvoker<Object>(target);
        Collections.reverse(interceptors);
        for (MethodInterceptor interceptor : interceptors) {
            decorated = new MethodInterceptorInvoker(decorated, interceptor);            
        }
        Object proxy = getProxyFactory().createProxy(decorated, interfaces);
        return (T) proxy;
    }
}
