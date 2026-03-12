# 🧩 Escape Room Virtual Backend

Backend de simulación para un sistema de **Escape Room digitalizado**.
Permite registrar equipos de jugadores, salas temáticas y el historial de sesiones de juego, incluyendo tiempos de escape y pistas utilizadas.

Este proyecto sirve como práctica de **Hibernate / JPA con Java 21**, modelando relaciones complejas y persistencia en **PostgreSQL**.

---

# Contexto del Proyecto

El escape room **“El Enigma”** cuenta con varias salas temáticas:

* 👻 Terror
* 🕵️ Misterio
* 🚀 Sci-Fi

Para digitalizar sus sesiones, necesitan un sistema que registre:

* Equipos de jugadores
* Salas disponibles
* Historial de partidas
* Tiempos de escape
* Número de pistas utilizadas

---

#  Objetivos de Aprendizaje

Este proyecto está diseñado para practicar:

* Relaciones **Many-to-Many con entidad intermedia**
* Relaciones **bidireccionales**
* Métodos utilitarios (`addJugador()`, `removeJugador()`)
* Estrategias de **Fetching (Lazy / Eager)**
* Uso de **Duration y LocalTime** para calcular tiempos de juego
* Manejo de **transacciones con Hibernate Session**

---

# 🛠 Stack Tecnológico

| Tecnología           | Versión             |
| -------------------- | ------------------- |
| Java                 | 21+                 |
| ORM                  | Hibernate 6.x / JPA |
| Base de datos        | PostgreSQL 16+      |
| Build tool           | Maven               |
| Control de versiones | Git + GitHub CLI    |

---

#  Arquitectura de Datos

El sistema modela la relación entre **Equipos** y **Salas** mediante una entidad intermedia llamada **SesionJuego**, que almacena el histórico de partidas.

```
Equipo 1 ----- * SesionJuego * ----- 1 SalaEscape
```

Cada sesión registra:

* tiempo empleado
* si lograron escapar
* pistas utilizadas
* fecha de la partida

---

#  Modelo de Entidades (Conceptual)

### Equipo

Representa un grupo de jugadores.

Campos principales:

* `id`
* `nombreEquipo`
* `numeroJugadores`
* `sesiones`

---

### SalaEscape

Representa una sala disponible.

Campos principales:

* `id`
* `nombre`
* `tematica`
* `dificultad`
* `tiempoMaximo`

---

### SesionJuego

Entidad intermedia que registra cada partida.

Campos principales:

* `id`
* `equipo`
* `sala`
* `logroEscapar`
* `pistasConsumidas`
* `tiempoEmpleado`

---

#  Guía de Implementación

## 1️⃣ Preparar el repositorio

Inicializar proyecto Maven:

```bash
mvn archetype:generate
```

Crear repositorio en GitHub:

```bash
gh repo create scaperoom-backend \
--public \
--source=. \
--remote=origin \
--push
```

---

## 2️⃣ Modelar las entidades JPA

Entidades principales:

```
Equipo
SalaEscape
SesionJuego
```

Relaciones:

* `Equipo → SesionJuego` → OneToMany
* `SalaEscape → SesionJuego` → OneToMany
* `SesionJuego → Equipo` → ManyToOne
* `SesionJuego → SalaEscape` → ManyToOne

---

## 3️⃣ Configurar Hibernate

Archivo `hibernate.cfg.xml`:

* conexión PostgreSQL
* dialecto
* entidades registradas

---

## 4️⃣ Insertar datos iniciales

Crear salas:

```
Laboratorio Zombie
Dificultad: 8
Tiempo máximo: 60 minutos
```

Crear equipo:

```
Los Linces
```

---

## 5️⃣ Simular una partida

Escenario:

```
Equipo: Los Linces
Sala: Laboratorio Zombie
Logró escapar: false
Pistas consumidas: 3
Tiempo empleado: 60 minutos
```

Registrar una `SesionJuego` en la base de datos.

---

#  Base de Datos

PostgreSQL debe tener creada la base:

```sql
CREATE DATABASE scaperoom_db;
```

Configuración típica:

```
jdbc:postgresql://localhost:5432/scaperoom_db
username: postgres
password: postgres
```

---

# ▶️ Ejecutar el Proyecto

Compilar:

```bash
mvn clean install
```

Ejecutar:

```bash
mvn exec:java
```

---

#  Estructura del Proyecto

```
Escaperoom
│
├── src
│   └── main
│       ├── java
│       │   └── com.Escaperoom
│       │       ├── modelo
│       │       ├── repositorio
│       │       ├── servicio
│       │       └── Main.java
│       │
│       └── resources
│           └── 
│
├── pom.xml
└── README.md
```

# 👨‍💻 Autor
Guillermo Castro Abarca <br>
 **Hibernate + PostgreSQL + Java 21**.

---
