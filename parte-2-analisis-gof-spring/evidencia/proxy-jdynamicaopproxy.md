# Evidencia — Patrón Proxy en Spring Framework

## Patrón

**Proxy**

## Categoría GoF

**Estructural**

## Clase analizada

`org.springframework.aop.framework.JdkDynamicAopProxy`

## Ubicación en Spring Framework

Módulo: `spring-aop`

Clase:

`org.springframework.aop.framework.JdkDynamicAopProxy`

## Evidencia del código fuente

La clase implementa `AopProxy`, `InvocationHandler` y `Serializable`:

```java
final class JdkDynamicAopProxy implements AopProxy, InvocationHandler, Serializable {