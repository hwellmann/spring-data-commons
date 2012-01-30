package org.springframework.data.proxy;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInvocation;

import com.thoughtworks.proxy.Invoker;

public class InvokerMethodInvocation implements MethodInvocation {
    
    private Invoker invoker;
    private Method method;
    private Object[] args;
    
    public InvokerMethodInvocation(Invoker invoker, Method method, Object[] args) {
        this.invoker = invoker;
        this.method = method;
        this.args = args;
    }

    public Object[] getArguments() {
        return args;
    }

    public Object proceed() throws Throwable {
        return invoker.invoke(invoker, method, args);
    }

    public Object getThis() {
        return invoker;
    }

    public AccessibleObject getStaticPart() {
        return null;
    }

    public Method getMethod() {
        return method;
    }
}
