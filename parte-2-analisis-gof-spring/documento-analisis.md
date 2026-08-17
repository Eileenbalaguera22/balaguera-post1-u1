# Análisis de Patrones GoF en Spring Framework

## 1. Portada

**Nombre:** Eileen Balaguera Rodríguez  
**Código:** 02240131036  
**Curso:** Ingeniería de Sistemas  
**Unidad:** Unidad 1: Fundamentos de Patrones de Diseño  
**Fecha:** 17 de agosto de 2026  

---

## 2. Introducción

Los patrones de diseño GoF (Gang of Four) representan soluciones generales y reutilizables para problemas recurrentes en el desarrollo de software orientado a objetos. Su importancia no radica únicamente en proporcionar estructuras de código previamente conocidas, sino en ofrecer un vocabulario común para analizar problemas de diseño, reducir el acoplamiento y facilitar la evolución de los sistemas. Los patrones se clasifican en tres grandes categorías: creacionales, estructurales y de comportamiento, dependiendo de si su propósito principal está relacionado con la creación de objetos, la composición de estructuras o la interacción entre objetos.

Spring Framework constituye un caso de estudio apropiado para analizar la aplicación práctica de estos patrones debido a que es un framework ampliamente utilizado para construir aplicaciones Java empresariales y proporciona mecanismos para la creación, configuración, composición e interacción de componentes. El presente análisis examina el código fuente de Spring Framework con el propósito de identificar tres patrones GoF pertenecientes a categorías diferentes: Factory Method como patrón creacional, Proxy como patrón estructural y Observer como patrón de comportamiento. Para cada patrón se analiza su ubicación dentro del framework, el problema que resuelve, la evidencia encontrada en el código fuente y su relación con los principios SOLID.

---

# 3. Análisis del Patrón 1: Factory Method

## 3.1 Patrón y categoría

El **Factory Method** pertenece a la categoría de patrones **creacionales**. Su propósito general consiste en encapsular la creación de objetos y permitir que el código cliente utilice una abstracción para obtener instancias sin depender directamente de las clases concretas que serán creadas.

En lugar de que cada componente de una aplicación tenga que utilizar directamente el operador `new` para construir sus dependencias, una fábrica puede centralizar y controlar el proceso de creación. Esto permite separar la lógica de utilización de un objeto de los detalles relacionados con su construcción, configuración y ciclo de vida.

En Spring Framework, esta idea se encuentra materializada en la infraestructura de creación y obtención de beans proporcionada por `BeanFactory`. Aunque `BeanFactory` constituye principalmente una abstracción de fábrica y contenedor IoC, su contrato representa claramente el rol creacional de una fábrica dentro del framework, ya que permite solicitar instancias sin que el código cliente tenga que conocer directamente el mecanismo utilizado para construirlas.

## 3.2 Ubicación en Spring Framework

El componente analizado es:

**Clase/interfaz:** `org.springframework.beans.factory.BeanFactory`

**Módulo:** `spring-beans`

`BeanFactory` constituye una de las abstracciones fundamentales del contenedor de Spring. Su responsabilidad consiste en proporcionar acceso a los objetos administrados por el contenedor mediante diferentes operaciones `getBean()`.

El código fuente de Spring establece que un `BeanFactory` puede proporcionar instancias compartidas o independientes dependiendo de la configuración del bean. De esta manera, el código cliente utiliza una misma abstracción para solicitar objetos sin necesidad de conocer si estos serán administrados como singleton, prototype u otro alcance.

## 3.3 Problema que resuelve

En una aplicación empresarial, la creación de objetos puede involucrar mucho más que la ejecución de un constructor. Los objetos pueden requerir dependencias, configuración, procesamiento posterior, control de ciclo de vida y diferentes estrategias de alcance.

Si cada clase de la aplicación utilizara directamente el operador `new`, tendría que conocer los detalles de construcción de sus dependencias. Esto produciría un acoplamiento fuerte entre las clases de negocio y las implementaciones concretas.

Spring resuelve este problema mediante el contenedor IoC. El desarrollador puede solicitar un bean mediante su nombre o tipo y delegar al framework la responsabilidad de determinar cómo obtener o crear la instancia.

Por ejemplo, en lugar de realizar directamente:

```java
MiServicio servicio = new MiServicio();
```

el código puede solicitar el componente al contenedor:

```java
MiServicio servicio = beanFactory.getBean(MiServicio.class);
```

De esta forma, el cliente conoce la abstracción mediante la cual obtiene el objeto, pero no necesita conocer todos los detalles relacionados con su construcción y configuración.

## 3.4 Evidencia de código

La interfaz `BeanFactory` define métodos para obtener instancias administradas por el contenedor:

```java
// org.springframework.beans.factory.BeanFactory

public interface BeanFactory {

    Object getBean(String name) throws BeansException;

    <T> T getBean(String name, Class<T> requiredType)
            throws BeansException;

    <T> T getBean(Class<T> requiredType)
            throws BeansException;
}
```

Los métodos `getBean()` constituyen evidencia del mecanismo de fábrica utilizado por Spring. El cliente solicita una instancia mediante una abstracción y delega al contenedor el proceso de obtención de la implementación concreta.

La documentación de `BeanFactory` también señala que el método `getBean()` puede proporcionar una instancia compartida o independiente dependiendo de la configuración del bean. Esto demuestra que el cliente no necesita conocer directamente la estrategia utilizada para crear o recuperar la instancia.

## 3.5 Principios SOLID relacionados

El patrón se relaciona principalmente con el **Dependency Inversion Principle (DIP)**. El código de alto nivel puede depender de abstracciones proporcionadas por el contenedor en lugar de depender directamente de las clases concretas que implementan los servicios. De esta manera, los detalles de creación quedan separados de la lógica que utiliza los objetos.

También existe una relación con el **Open/Closed Principle (OCP)**, porque el contenedor puede administrar nuevos tipos de beans sin que sea necesario modificar la lógica general de obtención de objetos. La extensión se produce mediante nuevas definiciones y configuraciones.

Finalmente, existe una relación con el **Single Responsibility Principle (SRP)**. Las clases de negocio pueden concentrarse en sus responsabilidades funcionales mientras que el contenedor asume las responsabilidades relacionadas con creación, configuración y gestión del ciclo de vida de los objetos.

## 3.6 Análisis contrafactual

Si Spring no utilizara este mecanismo de fábrica y gestión centralizada de objetos, las aplicaciones tendrían que crear manualmente gran parte de sus dependencias. Esto incrementaría el acoplamiento entre las clases, dificultaría las pruebas y obligaría a distribuir la lógica de construcción y configuración por diferentes partes de la aplicación.

Por lo tanto, el mecanismo de fábrica utilizado por Spring contribuye a mantener separadas las responsabilidades de creación y utilización de objetos.

---

# 4. Análisis del Patrón 2: Proxy

## 4.1 Patrón y categoría

El **Proxy** pertenece a la categoría de patrones **estructurales**. Su propósito consiste en proporcionar un objeto intermediario que controla o intercepta el acceso a otro objeto, denominado objeto real o sujeto.

El patrón permite agregar comportamientos adicionales alrededor de una operación sin modificar directamente la implementación original. En frameworks empresariales, este mecanismo resulta especialmente útil para implementar funcionalidades transversales como seguridad, transacciones, auditoría, logging y otras operaciones asociadas a la infraestructura.

## 4.2 Ubicación en Spring Framework

El componente analizado es:

**Clase:** `org.springframework.aop.framework.JdkDynamicAopProxy`

**Módulo:** `spring-aop`

`JdkDynamicAopProxy` es una implementación de `AopProxy` que utiliza los proxies dinámicos de Java para interceptar las llamadas realizadas sobre las interfaces expuestas por el objeto.

La propia documentación de la clase indica que se trata de una implementación de proxy basada en `java.lang.reflect.Proxy`.

## 4.3 Problema que resuelve

Las aplicaciones empresariales necesitan frecuentemente ejecutar funcionalidades adicionales alrededor de las operaciones de negocio. Algunos ejemplos son la validación de permisos, la gestión de transacciones, el registro de operaciones y la ejecución de aspectos.

Una alternativa directa sería incorporar estas responsabilidades dentro de cada método de negocio. Sin embargo, esto produciría duplicación de código y mezclaría responsabilidades técnicas con responsabilidades funcionales.

Spring utiliza proxies para interceptar las llamadas a los objetos y aplicar comportamientos adicionales sin modificar directamente las clases originales.

Este mecanismo constituye una parte importante de Spring AOP y permite implementar funcionalidades como la gestión declarativa de transacciones mediante anotaciones como `@Transactional`.

## 4.4 Evidencia de código

En `JdkDynamicAopProxy` se encuentra el siguiente método para crear el proxy dinámico:

```java
@Override
public Object getProxy(@Nullable ClassLoader classLoader) {
    if (logger.isTraceEnabled()) {
        logger.trace("Creating JDK dynamic proxy: "
                + this.advised.getTargetSource());
    }

    return Proxy.newProxyInstance(
            determineClassLoader(classLoader),
            this.cache.proxiedInterfaces,
            this);
}
```

El código demuestra que Spring utiliza `Proxy.newProxyInstance()` para crear un objeto intermediario que implementa las interfaces correspondientes y utiliza el propio `JdkDynamicAopProxy` como `InvocationHandler`.

La interceptación de las operaciones se realiza posteriormente mediante el método `invoke()`, donde Spring obtiene el objeto objetivo y determina la cadena de interceptores y consejos que debe ejecutarse:

```java
target = targetSource.getTarget();

List<Object> chain =
        this.advised.getInterceptorsAndDynamicInterceptionAdvice(
                method, targetClass);

retVal = invocation.proceed();
```

Este mecanismo permite que una llamada realizada por el cliente sea procesada por la infraestructura AOP antes de llegar al objeto objetivo.

## 4.5 Principios SOLID relacionados

El patrón Proxy se relaciona principalmente con el **Open/Closed Principle (OCP)**. Las clases de negocio pueden permanecer cerradas para modificación mientras nuevas funcionalidades transversales son incorporadas mediante mecanismos externos como proxies y aspectos.

También se relaciona con el **Single Responsibility Principle (SRP)**. La clase de negocio puede concentrarse en resolver la funcionalidad principal mientras que responsabilidades como transacciones, seguridad o logging pueden ser gestionadas por componentes de infraestructura.

Existe además una relación con el **Dependency Inversion Principle (DIP)**, debido a que el cliente puede interactuar con una abstracción o interfaz y el proxy puede interponerse entre el cliente y la implementación concreta.

## 4.6 Análisis contrafactual

Si Spring no utilizara proxies para implementar buena parte de sus funcionalidades AOP, las clases de negocio tendrían que incorporar directamente código relacionado con transacciones, seguridad, logging y otras responsabilidades técnicas.

Esto aumentaría el acoplamiento y disminuiría la cohesión de las clases. Además, cada nueva funcionalidad transversal podría requerir modificaciones en múltiples componentes de la aplicación.

Por esta razón, el uso de Proxy permite a Spring extender el comportamiento de los componentes de manera no invasiva.

El Proxy representa una herramienta fundamental para el bajo acoplamiento dentro del ecosistema Spring. Mientras el mecanismo de fábrica permite delegar la creación de objetos al contenedor, el proxy permite agregar comportamiento alrededor de esos objetos sin modificar directamente las clases de negocio.

---

# 5. Análisis del Patrón 3: Observer

## 5.1 Patrón y categoría

El **Observer** pertenece a la categoría de patrones **de comportamiento**. Su propósito consiste en establecer una relación uno-a-muchos entre objetos, de manera que cuando un objeto publica un cambio o evento, los objetos interesados pueden recibir una notificación.

Este patrón también se relaciona con el modelo de publicación-suscripción, debido a que el componente que genera el evento no necesita conocer directamente a todos los componentes interesados en recibirlo.

## 5.2 Ubicación en Spring Framework

Los componentes analizados son:

**Interfaz:** `org.springframework.context.ApplicationListener`

**Clase relacionada:** `org.springframework.context.ApplicationEvent`

**Módulo:** `spring-context`

`ApplicationListener` representa la abstracción utilizada por Spring para definir componentes interesados en determinados eventos de aplicación.

La documentación de la propia interfaz establece explícitamente que está basada en la interfaz estándar `java.util.EventListener` para implementar el patrón Observer.

## 5.3 Problema que resuelve

En aplicaciones empresariales pueden existir múltiples componentes que necesitan reaccionar ante una misma acción. Por ejemplo, después de registrar un pedido, diferentes componentes podrían necesitar enviar una notificación, registrar una auditoría o actualizar información adicional.

Una implementación directa podría hacer que la clase que registra el pedido conociera y llamara explícitamente a todos esos componentes. Esta solución genera un acoplamiento fuerte entre el emisor y los receptores.

Spring resuelve este problema mediante eventos de aplicación. El componente que genera el evento publica la información y los listeners registrados pueden reaccionar ante ella sin que el publicador necesite conocer sus implementaciones concretas.

Esto facilita la extensión del sistema porque pueden incorporarse nuevos listeners sin modificar la clase que genera el evento.

## 5.4 Evidencia de código

La interfaz `ApplicationListener` define el contrato fundamental para los observadores:

```java
@FunctionalInterface
public interface ApplicationListener<E extends ApplicationEvent>
        extends EventListener {

    void onApplicationEvent(E event);

    default boolean supportsAsyncExecution() {
        return true;
    }
}
```

El método `onApplicationEvent()` representa la operación que será ejecutada cuando el listener reciba un evento compatible.

La propia documentación de Spring identifica esta interfaz como parte de la implementación del patrón Observer:

```java
/**
 * Interface to be implemented by application event listeners.
 *
 * Based on the standard EventListener interface for the
 * Observer design pattern.
 */
```

El uso de `@FunctionalInterface` también permite implementar listeners mediante expresiones lambda desde Java 8, proporcionando una forma concisa de definir receptores de eventos.

## 5.5 Principios SOLID relacionados

El patrón Observer se relaciona principalmente con el **Dependency Inversion Principle (DIP)**. El publicador no depende directamente de las implementaciones concretas de los receptores, sino de una infraestructura de eventos y de abstracciones como `ApplicationEvent` y `ApplicationListener`.

También refuerza el **Open/Closed Principle (OCP)** porque pueden agregarse nuevos receptores de eventos sin modificar el componente que publica el evento.

Asimismo, se relaciona con el **Single Responsibility Principle (SRP)**. Una clase puede mantener su responsabilidad principal y delegar funcionalidades secundarias a observadores especializados. Por ejemplo, una clase puede encargarse exclusivamente de registrar una operación mientras otros listeners se encargan de notificar, auditar o actualizar información.

## 5.6 Análisis contrafactual

Si Spring no utilizara el patrón Observer para gestionar eventos, los componentes que producen eventos tendrían que conocer directamente a cada componente que necesita reaccionar ante ellos.

Esto produciría dependencias directas entre módulos y dificultaría la incorporación de nuevos comportamientos. Además, una modificación en los receptores podría obligar a modificar el componente que genera el evento.

El sistema de eventos de Spring evita este problema al proporcionar una infraestructura desacoplada para publicar y recibir eventos.

---

# 6. Conclusiones

Este análisis permite concluir que los patrones de diseño no son simplemente fragmentos de código reutilizables, sino soluciones arquitectónicas que representan experiencias y buenas prácticas acumuladas en el desarrollo de software. El análisis del código fuente de Spring Framework permite observar que la flexibilidad y mantenibilidad del framework se encuentran relacionadas con el uso sistemático de abstracciones y mecanismos de diseño orientados a controlar la complejidad del sistema.

Los tres patrones analizados muestran diferentes dimensiones de este enfoque. El **Factory Method** permite delegar la creación y gestión de objetos al contenedor; el **Proxy** permite incorporar comportamientos adicionales sin modificar directamente las clases de negocio; y el **Observer** facilita la comunicación desacoplada entre componentes mediante eventos. En los tres casos se observa una relación con principios SOLID como **DIP, OCP y SRP**.

Finalmente, el estudio demuestra que el valor de los patrones GoF no consiste en aplicarlos de manera automática, sino en reconocer los problemas que justifican su utilización. Para el diseño de software propio, la principal lección consiste en seleccionar patrones de acuerdo con las necesidades reales del sistema, buscando soluciones claras, mantenibles, extensibles y con un nivel de complejidad proporcional al problema que se pretende resolver.

---

# 7. Referencias

Gamma, E., Helm, R., Johnson, R., & Vlissides, J. (1994). *Design patterns: Elements of reusable object-oriented software*. Addison-Wesley.

Spring. (2026). *Spring Framework source code*. GitHub. https://github.com/spring-projects/spring-framework

Spring. (2026). *Spring Boot reference documentation*. https://docs.spring.io/spring-boot/reference/

Refactoring.Guru. (s. f.). *Design patterns*. https://refactoring.guru/design-patterns
