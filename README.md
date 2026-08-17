# balaguera-post1-u1
Post-contenido — Refactorización SOLID y análisis de patrones GoF en Spring

## Análisis de Violaciones SOLID

| Principio | Método/Sección afectada | Descripción de la violación |
|---|---|---|
| SRP | calculateTotal + applyDiscount + saveOrder + sendEmail + printReport | La clase OrderProcessor concentra múltiples responsabilidades: cálculo de totales e impuestos, aplicación de descuentos, persistencia de órdenes, envío de notificaciones y generación de reportes. Esto provoca que cambios en diferentes funcionalidades requieran modificar la misma clase, dificultando su mantenimiento. |
| OCP | applyDiscount | El método utiliza estructuras if/else basadas en customerType para determinar el descuento. Si se agrega un nuevo tipo de cliente o una nueva política de descuento, es necesario modificar el código existente, por lo que la clase no está abierta a extensión y cerrada a modificación. |
| DIP | Toda la clase | OrderProcessor depende directamente de implementaciones concretas para realizar sus responsabilidades, como ArrayList para almacenar órdenes y System.out para persistencia, notificación y presentación. No utiliza abstracciones que permitan desacoplar estas responsabilidades de sus implementaciones. |