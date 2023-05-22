package com.amrut.prabhu.ha.autoconfiguration;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.stereotype.Controller;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

//@Component
public class HAAutoconfiguration implements InitializingBean {


    public HAAutoconfiguration(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }


    private ApplicationContext applicationContext;

    @Override
    public void afterPropertiesSet() throws Exception {
        Predicate<? super Method> byAnnotation = method -> Arrays.stream(method.getDeclaredAnnotations())
                .anyMatch(annotation -> annotation.annotationType().equals(HAHandler.class));

        String[] beanNamesForAnnotation = this.applicationContext.getBeanNamesForAnnotation(Controller.class);
        Map<String, Object> beansWithAnnotation = applicationContext.getBeansWithAnnotation(Controller.class);
        Map<String, List<Invocations>> collect = beansWithAnnotation.values().stream()
//                .map(beanName -> this.applicationContext.getType(beanName))
//                .filter(bean -> AnnotatedElementUtils.hasAnnotation(bean, Controller.class))
                .flatMap(bean1 -> Arrays.stream(bean1.getClass().getMethods()))
                .filter(byAnnotation)
                .peek(method -> System.out.println(method.getName()))
                .collect(Collectors.groupingBy(method -> method.getAnnotation(HAHandler.class).value(), Collectors.collectingAndThen(Collectors.groupingBy(m -> m.getDeclaringClass(), Collectors.<Method>toList()), d -> d.entrySet().stream().map(entry -> new Invocations(entry.getKey(), entry.getValue())).collect(Collectors.toList()))));//                .collect(Collectors.groupingBy(method -> method.getAnnotation(HAHandler.class).value(), Collectors.groupingBy(m -> m.getDeclaringClass().getName(), Collectors.toList())));
//        ArrayList<String> beans = new ArrayList<>();
//        for (String beanName : beanNamesForType) {
//            Class<?> type = this.applicationContext.getType(beanName);
//
////                     collect(method -> method.(HAHandler.class));
//            if (AnnotatedElementUtils.hasAnnotation(type, Controller.class)) {
//
//
////                final Class<?> userType = ClassUtils.getUserClass(type);
////                Map<Method, String> methods = MethodIntrospector.selectMethods(userType,
////                        (MethodIntrospector.MetadataLookup<String>) method -> getMappingForMethod(method, userType));
////                methods.forEach((key, value) -> System.out.println(key.getName()));
//                Predicate<? super Method> byAnnotation = method -> Arrays.stream(method.getDeclaredAnnotations())
//                        .anyMatch(annotation -> annotation.annotationType().equals(HAHandler.class));
//
//                Map<Method, Map<String, List<Method>>> collect = Arrays.stream(type.getMethods()).
//                        filter(byAnnotation).
//                        peek(method -> System.out.println(method.getName()))
//                        .collect(Collectors.groupingBy(method -> method, Collectors.groupingBy(m -> m.getAnnotation(HAHandler.class).value(), Collectors.toList())))
//                                .
//                beans.add(beanName);
//
//            }
//        }
//        beans.forEach(System.out::println);
        List<Invocations> result = collect.get("Result");
        result.forEach(inv -> {

            Object bean = this.applicationContext.getBean(inv.getClassName());
            inv.getMethods().forEach(method -> {
                        try {
                            Object invoke = method.invoke(bean);
                        } catch (IllegalAccessException e) {
                            throw new RuntimeException(e);
                        } catch (InvocationTargetException e) {
                            throw new RuntimeException(e);
                        }
                    }
            );
        });
    }

    private String getMappingForMethod(Method method, Class<?> userType) {
        HAHandler mergedAnnotation = AnnotatedElementUtils.findMergedAnnotation((AnnotatedElement) method, HAHandler.class);
        return mergedAnnotation.value();
    }


}
