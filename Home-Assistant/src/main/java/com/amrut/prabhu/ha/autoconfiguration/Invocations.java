package com.amrut.prabhu.ha.autoconfiguration;

import java.lang.reflect.Method;
import java.util.List;

public class Invocations {
    private Class<?> className;

    private List<Method> methods;

    public Invocations(Class<?> className, List<Method> methods) {
        this.className = className;
        this.methods = methods;
    }

    public Class<?> getClassName() {
        return className;
    }

    public List<Method> getMethods() {
        return methods;
    }
}
