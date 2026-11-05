# 🎓 Patrón de Diseño Mediador (Mediator Pattern) en Java

Este proyecto contiene un ejemplo práctico y educativo del **Patrón Mediador**, aplicado a un sistema real de **Comercio Electrónico (E-Commerce)** compuesto por los servicios de:
* **Pedidos (Orders)**
* **Inventario (Inventory)**
* **Pagos (Payments)**
* **Envíos (Shipping)**
* **Notificaciones (Notifications)**
* **Puntos de Fidelización (Loyalty/Gamification)**

El proyecto incluye dos versiones completas y ejecutables para comparar:
1. **`src/antes/`**: Sistema con **acoplamiento espagueti (N-a-N)** sin mediador.
2. **`src/despues/`**: Sistema desacoplado con **arquitectura en estrella (1-a-N)** aplicando el Patrón Mediador.

---

## 🏛️ Estructura del Proyecto

```text
Patron Mediator/
├── bin/                             <- Archivos compilados (.class)
├── src/
│   ├── antes/                       <- Código SIN el patrón
│   │   ├── Pedido.java
│   │   ├── ServicioNotificaciones.java
│   │   ├── ServicioEnvios.java
│   │   ├── ServicioInventario.java
│   │   ├── ServicioPagos.java
│   │   ├── ServicioPedidos.java
│   │   └── MainAntes.java           <- Punto de entrada (Antes)
│   │
│   └── despues/                     <- Código CON el patrón Mediador
│       ├── modelo/
│       │   ├── Pedido.java
│       │   └── TipoEvento.java      <- Enum de eventos desacoplados
│       ├── mediador/
│       │   ├── MediadorCompra.java  <- Interfaz Mediador (Contrato)
│       │   └── CompraMediador.java  <- Mediador Concreto (Orquestador)
│       ├── componentes/
│       │   ├── ComponenteServicio.java <- Clase base colega
│       │   ├── ServicioPedidos.java
│       │   ├── ServicioInventario.java
│       │   ├── ServicioPagos.java
│       │   ├── ServicioEnvios.java
│       │   ├── ServicioNotificaciones.java
│       │   └── ServicioPuntosFidelidad.java <- Extensión sin modificar otros
│       └── MainDespues.java         <- Punto de entrada (Después)
└── README.md
```

---

## 🔍 Comparación Conceptual: ¿Por qué usar el Mediador?

### ❌ ANTES (Sin Mediador - Red Caótica de Acoplamiento)
```
  [ServicioPedidos] -------> [ServicioInventario] <------+
        |                           ^                    | (rollback)
        v                           |                    |
  [ServicioPagos] ------------------+                    |
        |                                                |
        +------------------> [ServicioEnvios]            |
        |                           |                    |
        v                           v                    |
  [ServicioNotificaciones] <--------+                    |
```
* **Problema:** `ServicioPagos` necesita conocer y llamar directamente a `ServicioInventario` (para rollback en caso de fallo), a `ServicioEnvios` (para despachar) y a `ServicioNotificaciones`.
* Si se desea agregar un nuevo servicio como `ServicioPuntosFidelidad`, habría que modificar `ServicioPagos`, `ServicioPedidos` y sus constructores.
* **Violación de Principios:** Viola el Principio de Responsabilidad Única (SRP) y el Principio Abierto/Cerrado (OCP).

---

###  DESPUÉS (Con Patrón Mediador - Topología en Estrella)
```
          [ServicioPedidos]         [ServicioInventario]
                   \                     /
                    \                   /
                     v                 v
                 +-------------------------+
                 |     CompraMediador      |
                 +-------------------------+
                    ^         |         ^
                   /          |          \
                  /           v           \
          [ServicioPagos] [ServicioEnvios] [ServicioNotificaciones]
                                  |
                        [ServicioPuntosFidelidad]
```
* **Solución:** Cada componente solo conoce a la interfaz `MediadorCompra`.
* Cuando ocurre algo (ej: pago exitoso), el componente emite: `mediador.notificar(this, TipoEvento.PAGO_EXITOSO, datos)`.
* El componente no sabe ni le importa quién reacciona a ese evento.
* Toda la lógica de orquestación, compensación (rollbacks) y flujos de negocio reside en un único lugar: `CompraMediador`.

---

## 🚀 Cómo Compilar y Ejecutar

Abre una terminal en la raíz del proyecto (`c:\Users\herna\Desktop\Patron Mediator`) y ejecuta:

### 1. Compilación
Compila todo el código fuente depositando los `.class` en la carpeta `bin/`:

```powershell
javac -encoding UTF-8 -d bin src/antes/*.java src/despues/modelo/*.java src/despues/mediador/*.java src/despues/componentes/*.java src/despues/*.java
```

### 2. Ejecutar la versión SIN Mediador (Antes)
```powershell
java -cp bin antes.MainAntes
```

### 3. Ejecutar la versión CON Mediador (Después)
```powershell
java -cp bin despues.MainDespues
```

---

## 📊 Resumen Comparativo para Exposiciones / Evaluaciones

| Aspecto | Antes (Sin Mediador) | Después (Con Mediador) |
| :--- | :--- | :--- |
| **Relación entre clases** | Muchos a Muchos ($N \times N$) | Uno a Muchos ($1 \times N$) |
| **Acoplamiento** | Alto y enredado | Débil (Loose coupling) |
| **Reutilización** | Difícil (los servicios arrastran dependencias) | Alta (servicios 100% independientes) |
| **Pruebas unitarias** | Complejas (requieren instanciar o mockear 3-4 servicios) | Sencillas (solo se prueba el componente aislado) |
| **Extensibilidad (OCP)** | Baja (agregar fidelización modifica Pagos) | Alta (solo se registra en el mediador) |

