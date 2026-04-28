# Informe Técnico - Sistema BiblioTech (TP1)

Este documento detalla las decisiones de arquitectura, herramientas y diseño aplicadas para cumplir con los requerimientos del Trabajo Práctico 1.

### 1. Arquitectura y Modelado
- **Inmutabilidad y Concisión:** Se utilizaron `Java Records` para las entidades principales (`Libro`, `EBook`, `Socio`, `Prestamo`), garantizando la integridad de los datos y reduciendo significativamente el código repetitivo.
- **Jerarquía de Clases:** Se implementó polimorfismo mediante la interfaz `Recurso`. Esto permite que el sistema gestione ejemplares físicos y digitales (`EBook`) de manera uniforme, facilitando la extensibilidad para futuros tipos de recursos.

### 2. Gestión de Dependencias y Construcción (Gradle)
Se seleccionó **Gradle** como motor de construcción por las siguientes razones:
- **Gestión Automatizada:** Facilita la incorporación de librerías externas sin necesidad de manejar archivos `.jar` manualmente.
- **Portabilidad:** Gracias al uso del Gradle Wrapper, el proyecto puede ser compilado y ejecutado en cualquier entorno sin necesidad de una instalación previa de la herramienta.
- **Estandarización:** Define una estructura de proyecto profesional y automatiza el ciclo de vida del software (compilación, pruebas y empaquetado).

### 3. Capa de Persistencia y Lógica de Negocio
- **Abstracción de Datos:** El uso de una interfaz genérica `Repository<T, K>` estandariza el acceso a los datos y facilita el testing.
- **Programación Funcional:** Se emplearon `Java Streams` y expresiones Lambda para la **Búsqueda Avanzada**, permitiendo filtrados declarativos y eficientes por múltiples criterios.
- **Validaciones de Seguridad:** - Se implementaron **Expresiones Regulares (Regex)** para asegurar el formato correcto de los correos electrónicos.
  - Se garantiza la integridad de la base de datos mediante la validación de **DNI único** en el registro de socios.

### 4. Robustez y Calidad de Código
- **Manejo de Nulos:** El uso de `Optional<T>` en las operaciones de búsqueda y mejora la expresividad del código.
- **Excepciones Personalizadas:** Se implementó `BibliotecaException` para manejar errores de dominio específicos, separando la lógica de negocio de los errores técnicos del lenguaje.

### 5. Flujo de Trabajo
- El desarrollo se realizó siguiendo una metodología de **Ramas de Características (Git Flow)**, asegurando que la rama `main` represente siempre una versión estable y funcional del sistema.