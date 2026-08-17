# Evidencia — Factory Method en BeanFactory

## Clase

`org.springframework.beans.factory.BeanFactory`

## Ubicación

Spring Framework → spring-beans → BeanFactory.java

## Fragmento de código

```java
Object getBean(String name) throws BeansException;

<T> T getBean(String name, Class<T> requiredType)
        throws BeansException;

<T> T getBean(Class<T> requiredType)
        throws BeansException;