# Evidencia — Observer en ApplicationListener

## Patrón

**Observer**

## Categoría

**Comportamiento**

## Clase/interfaz de Spring

`org.springframework.context.ApplicationListener<E extends ApplicationEvent>`

## Ubicación

Spring Framework → `spring-context` → 
`org.springframework.context.ApplicationListener`

## Evidencia del código fuente

```java
@FunctionalInterface
public interface ApplicationListener<E extends ApplicationEvent> extends EventListener {

    void onApplicationEvent(E event);

    default boolean supportsAsyncExecution() {
        return true;
    }

    static <T> ApplicationListener<PayloadApplicationEvent<T>> forPayload(
            Consumer<T> consumer) {
        return event -> consumer.accept(event.getPayload());
    }
}