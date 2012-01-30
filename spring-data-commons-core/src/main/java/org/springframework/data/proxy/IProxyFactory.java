package org.springframework.data.proxy;

import org.aopalliance.aop.Advice;

public interface IProxyFactory {
    void setTarget(Object target);
    void setInterfaces(Class<?>[] interfaces);
    void addAdvice(Advice advice);
    <T> T getProxy();
}
