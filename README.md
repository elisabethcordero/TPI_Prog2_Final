# Food Store - Sistema de Gestión de Pedidos de Comida (Consola)

## Universidad Tecnológica Nacional (UTN)

**Carrera:** Tecnicatura Universitaria en Programación  
**Materia:** Programación II  
**Modalidad:** A Distancia  
**Año lectivo:** 2026  
**Profesor:** Renzo Sosa  

---

## Integrantes - Comisión 5

- **Cordero Campero Elisabeth**
- **Franco Analía Rocío**
- **García Nadia Anahí**

---

## Enlaces Obligatorios de la Entrega

**Requisitos de Cátedra**

- **Video de Demostración (Flujo Completo):** [Ingresar a la demostración en video] https://youtu.be/Z83WDUwQ2n0

---

## Descripción del Proyecto

Este proyecto corresponde al **Trabajo Práctico Integrador (TPI)** de la materia **Programación II** de la **Tecnicatura Universitaria en Programación – UTN**.

El sistema fue desarrollado en **Java** y consiste en una aplicación de consola para la gestión de pedidos de comida.

Permite administrar categorías, productos, usuarios, pedidos y detalles de pedido, aplicando conceptos fundamentales de la **Programación Orientada a Objetos (POO)**.

La información se almacena en memoria durante la ejecución del programa mediante colecciones, principalmente `ArrayList`. Por este motivo, los datos se pierden al cerrar la aplicación.

Esta implementación corresponde a una etapa inicial del sistema, dejando preparada la estructura para una futura conexión a base de datos mediante **JDBC**.

El trabajo busca integrar los principales conceptos teóricos de la materia: clases, objetos, encapsulamiento, herencia, polimorfismo, interfaces, enumeraciones, colecciones, relaciones entre clases, CRUD, validaciones y manejo de excepciones.

---

## Funcionalidades Principales

- Gestión de categorías.
- Gestión de productos.
- Gestión de usuarios.
- Gestión de pedidos.
- Gestión de detalles de pedido.
- Alta, listado, modificación y eliminación de registros.
- Baja lógica de entidades.
- Validación de datos ingresados.
- Validación de stock y precios.
- Control de email único para usuarios.
- Cálculo de subtotales y total de pedidos.
- Manejo de excepciones personalizadas.

---

## Conceptos Aplicados

En el desarrollo del sistema se aplicaron los siguientes conceptos de Programación II:

- Programación Orientada a Objetos (POO).
- Clases y objetos.
- Encapsulamiento.
- Herencia.
- Clase abstracta.
- Polimorfismo.
- Interfaces.
- Enumeraciones.
- Colecciones.
- Relaciones uno a muchos.
- CRUD.
- Validaciones.
- Manejo de excepciones.
- Excepciones personalizadas.
- Separación en capas.
- Baja lógica.

---

## Tecnologías Utilizadas

- Java 21
- Programación Orientada a Objetos (POO)
- Colecciones (`ArrayList`)
- Git y GitHub
- NetBeans IDE
- JDBC (estructura preparada para futura implementación)

---

## Estructura del Proyecto

```text
src/
└── integrado/
    └── prog2/
        ├── config/
        │   └── Listas.java
        ├── entidades/
        │   ├── Base.java
        │   ├── Calculable.java
        │   ├── Categoria.java
        │   ├── Producto.java
        │   ├── Usuario.java
        │   ├── Pedido.java
        │   └── DetallePedido.java
        ├── enumeraciones/
        │   ├── Rol.java
        │   ├── Estado.java
        │   └── FormaPago.java
        ├── excepciones/
        │   ├── EntidadNoEncontradaException.java
        │   ├── PrecioInvalidoException.java
        │   ├── StockInvalidoException.java
        │   └── MailDuplicadoException.java
        ├── interfazUsuario/
        │   ├── MenuBase.java
        │   ├── MenuCategoria.java
        │   ├── MenuProducto.java
        │   ├── MenuUsuario.java
        │   └── MenuPedido.java
        ├── servicios/
        │   ├── CategoriaService.java
        │   ├── ProductoService.java
        │   ├── UsuarioService.java
        │   └── PedidoService.java
        └── Main.java
```

---

## Arquitectura del Sistema

El sistema fue organizado aplicando una separación de responsabilidades mediante paquetes.

### Interfaz de Usuario

La capa de interfaz de usuario contiene los menús de consola. Su función es mostrar opciones, solicitar datos al usuario y presentar resultados.

### Servicios

La capa de servicios contiene la lógica de negocio. Allí se realizan las principales validaciones, como controlar precios inválidos, stock insuficiente, emails duplicados o entidades inexistentes.

### Configuración y Almacenamiento

La capa de configuración contiene la clase `Listas`, utilizada como almacenamiento en memoria. Esta clase centraliza las colecciones del sistema y permite guardar categorías, productos, usuarios y pedidos durante la ejecución del programa.

Esta organización permite mantener el código más ordenado, reutilizable y preparado para futuras modificaciones.

---

## Almacenamiento de Datos

En esta etapa, el sistema no utiliza una base de datos real.

Los datos se almacenan temporalmente en memoria mediante colecciones como `ArrayList`.

Esto permite simular el funcionamiento básico de una base de datos, aunque la información se pierde al finalizar la ejecución del programa.

La estructura del proyecto queda preparada para que, en una etapa futura, pueda incorporarse una conexión a base de datos mediante JDBC.

---

## Validaciones Implementadas

El sistema incluye validaciones para evitar inconsistencias:

- No permitir productos con precio negativo.
- No permitir stock inválido.
- No permitir usuarios con email duplicado.
- No permitir categorías duplicadas.
- No permitir seleccionar entidades inexistentes.
- No permitir cantidades inválidas en los detalles de pedido.
- No permitir pedidos sin datos válidos.

Estas validaciones se realizan principalmente en la capa de servicios, separando la lógica de negocio de los menús de consola.

---

## Instrucciones de Ejecución

### Requisitos

- Tener instalado Java 21 o superior.
- Tener un IDE compatible con Java (recomendado: NetBeans).

### Clonar el repositorio

```bash
git clone https://github.com/usuario/food-store.git
```

### Ejecutar desde NetBeans

1. Abrir el proyecto en NetBeans.
2. Verificar que el proyecto esté configurado con JDK 21.
3. Ejecutar la clase principal:

```text
Main.java
```

4. Utilizar el menú interactivo de consola para acceder a las funcionalidades del sistema.

---

## Ejecución desde Terminal

### Compilar

```bash
javac Main.java
```

### Ejecutar

```bash
java Main
```

---

## Estado del Proyecto

Proyecto académico desarrollado para la materia Programación II.

Actualmente el sistema funciona completamente en memoria utilizando colecciones, cumpliendo los requisitos solicitados para la etapa de consola.

La arquitectura del proyecto queda preparada para futuras mejoras e integración con base de datos mediante JDBC.
