# balaguera-post1-u1
Post-contenido — Refactorización SOLID y análisis de patrones GoF en Spring

## Análisis de Violaciones SOLID

| Principio | Método/Sección afectada | Descripción de la violación |
|---|---|---|
| SRP | calculateTotal + applyDiscount + saveOrder + sendEmail + printReport | La clase OrderProcessor concentra múltiples responsabilidades: cálculo de totales e impuestos, aplicación de descuentos, persistencia de órdenes, envío de notificaciones y generación de reportes. Esto provoca que cambios en diferentes funcionalidades requieran modificar la misma clase, dificultando su mantenimiento. |
| OCP | applyDiscount | El método utiliza estructuras if/else basadas en customerType para determinar el descuento. Si se agrega un nuevo tipo de cliente o una nueva política de descuento, es necesario modificar el código existente, por lo que la clase no está abierta a extensión y cerrada a modificación. |
| DIP | Toda la clase | OrderProcessor depende directamente de implementaciones concretas para realizar sus responsabilidades, como ArrayList para almacenar órdenes y System.out para persistencia, notificación y presentación. No utiliza abstracciones que permitan desacoplar estas responsabilidades de sus implementaciones. |


# Post-contenido — Unidad 1: Fundamentos de Patrones de Diseño y Buenas Prácticas

## Descripción

Repositorio del post-contenido de la Unidad 1 de Patrones de Diseño de Software — Sexto Semestre. Contiene dos partes: refactorización SOLID de un God Object (`parte-1-refactorizacion-solid/`) y análisis de patrones GoF en Spring Framework (`parte-2-analisis-gof-spring/`).

## Parte 1 — Refactorización SOLID

Proyecto Maven que refactoriza `OrderProcessor` aplicando los principios SOLID, específicamente:

- **SRP (Single Responsibility Principle):** separación de responsabilidades.
- **OCP (Open/Closed Principle):** aplicación de estrategias de descuento extensibles.
- **DIP (Dependency Inversion Principle):** inyección de dependencias mediante abstracciones.

La implementación y las evidencias de esta parte se encuentran en `parte-1-refactorizacion-solid/`.

## Parte 2 — Análisis de Patrones GoF en Spring

Se investigó el código fuente de Spring Framework para identificar tres patrones GoF pertenecientes a categorías diferentes:

| # | Patrón | Categoría | Clase en Spring |
|---|--------|-----------|-----------------|
| 1 | Factory Method | Creacional | `org.springframework.beans.factory.BeanFactory` |
| 2 | Proxy | Estructural | `org.springframework.aop.framework.JdkDynamicAopProxy` |
| 3 | Observer | Comportamiento | `org.springframework.context.ApplicationListener` |

El análisis completo, junto con las evidencias del código fuente, se encuentra en `parte-2-analisis-gof-spring/documento-analisis.md`.

## Herramientas utilizadas

- Java 17
- Apache Maven
- VS Code
- Git
- GitHub
- Spring Framework
- Código fuente de Spring Framework para investigación

## Conclusiones

El desarrollo de ambas partes permitió comprender que los patrones de diseño y los principios SOLID son herramientas complementarias para construir software mantenible y flexible. La primera parte permitió aplicar SRP, OCP y DIP mediante la refactorización de un God Object, reduciendo el acoplamiento y separando responsabilidades. La segunda parte permitió reconocer cómo patrones GoF como Factory Method, Proxy y Observer son utilizados en Spring Framework para gestionar la creación de objetos, incorporar comportamientos adicionales y establecer comunicación desacoplada entre componentes. En conjunto, el ejercicio demuestra que los patrones deben aplicarse de manera justificada, considerando el problema que se busca resolver y evitando la sobreingeniería.