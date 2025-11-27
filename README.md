-----

````markdown
# ☕ TechStore Backend - API RESTful

Este repositorio contiene el **Backend** del sistema de comercio electrónico **TechStore**. Es una API RESTful robusta y segura desarrollada con **Spring Boot 3** y **Java 17**, encargada de gestionar la lógica de negocio, la seguridad y la persistencia de datos.

![Java](https://img.shields.io/badge/Java-17-orange)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.2.0-brightgreen)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-blue)
![JWT](https://img.shields.io/badge/Security-JWT_Auth-red)
![Deploy](https://img.shields.io/badge/Deploy-Render-purple)

---

## 🛠️ Tecnologías Utilizadas

* **Lenguaje:** Java 17 (OpenJDK).
* **Framework:** Spring Boot 3.2.0.
* **Seguridad:** Spring Security 6 + JWT (JSON Web Tokens - HS256).
* **Base de Datos:** PostgreSQL (Alojada en AlwaysData).
* **Persistencia:** Spring Data JPA / Hibernate.
* **Documentación:** SpringDoc OpenAPI (Swagger UI).
* **Build Tool:** Maven.
* **Contenedorización:** Docker.

---

## 🚀 Funcionalidades Principales

El backend expone endpoints para las siguientes operaciones:

### 🔐 Autenticación & Seguridad
* **Registro de Usuarios:** Creación de cuentas con encriptación de contraseñas (**BCrypt**).
* **Login:** Generación de Tokens **JWT** para sesiones *stateless*.
* **Control de Acceso:** Rutas protegidas por Roles (`ADMIN`, `CLIENTE`).
* **CORS:** Configurado para permitir peticiones desde el Frontend (Vercel/Localhost).

### 📦 Gestión de Productos
* **Catálogo Público:** Listar y ver detalles de productos.
* **Administración (CRUD):** Crear, editar (incluyendo ofertas %) y eliminar productos.
* **Control de Stock:** Validación automática de inventario.

### 🛒 Órdenes y Transacciones
* **Procesamiento de Compras:** Creación de órdenes transaccionales.
* **Gestión de Stock:** Descuento automático de unidades al confirmar la orden.
* **Historial:** Consulta de compras por usuario.
* **Estado:** Actualización de estados (Pendiente -> Completado).

### 📊 Dashboard Administrativo
* **Métricas en Tiempo Real:** Ventas totales, usuarios registrados, productos y órdenes.
* **Gestión de Usuarios:** Listado y administración de clientes.

---

## ⚙️ Instalación y Ejecución Local

Sigue estos pasos para levantar el servidor en tu máquina:

### 1. Prerrequisitos
* Java JDK 17 instalado.
* Maven (opcional, el proyecto incluye `mvnw`).
* Un cliente SQL (DBeaver) o PostgreSQL instalado localmente (si no usas la nube).

### 2. Clonar el Repositorio
```bash
git clone [https://github.com/apotheosisss/tienda-fullstack-backend.git](https://github.com/apotheosisss/tienda-fullstack-backend.git)
cd tienda-fullstack-backend
````

### 3\. Configuración de Variables

El proyecto viene configurado para conectar a **AlwaysData** por defecto en `src/main/resources/application.properties`.

Si deseas usar variables de entorno (Recomendado para producción), configura lo siguiente en tu IDE o sistema:

| Variable | Descripción | Ejemplo |
| :--- | :--- | :--- |
| `DB_URL` | URL JDBC de PostgreSQL | `jdbc:postgresql://host:5432/db_name` |
| `DB_USERNAME` | Usuario de la BD | `postgres` |
| `DB_PASSWORD` | Contraseña de la BD | `admin123` |
| `JWT_SECRET` | Clave para firmar Tokens | `ClaveSecretaSuperLarga...` |

### 4\. Ejecutar la Aplicación

Usa el wrapper de Maven incluido para iniciar el servidor:

```bash
# En Windows
.\mvnw spring-boot:run

# En Mac/Linux
./mvnw spring-boot:run
```

El servidor iniciará en: `http://localhost:8080`

-----

## 📡 Documentación de la API (Swagger)

Una vez iniciada la aplicación, puedes explorar y probar todos los endpoints visualmente en:

👉 **URL Local:** `http://localhost:8080/swagger-ui.html`
👉 **URL Producción:** `https://tienda-fullstack-backend.onrender.com/swagger-ui.html`

-----

## 📂 Estructura del Proyecto

```text
src/main/java/com/example/crud
├── config/           # Configuraciones globales (CORS si aplica)
├── controller/       # Controladores REST (Endpoints)
├── dto/              # Data Transfer Objects (Request/Response)
├── model/            # Entidades JPA (Tablas de BD)
├── repository/       # Interfaces de acceso a datos (JPA)
├── security/         # Configuración de JWT y Spring Security
└── CrudApplication.java  # Clase Principal
```

-----

## ☁️ Despliegue en Render

Este proyecto incluye un `Dockerfile` optimizado para el despliegue en la nube.

1.  Crear nuevo **Web Service** en Render.
2.  Conectar este repositorio.
3.  Runtime: **Docker**.
4.  Configurar Variables de Entorno (`JWT_SECRET`, etc.).
5.  ¡Desplegar\!

-----

## 🛡️ Endpoints Clave

| Método | Endpoint | Rol Requerido | Descripción |
| :--- | :--- | :--- | :--- |
| `POST` | `/api/auth/login` | Público | Iniciar sesión y obtener Token. |
| `GET` | `/api/productos` | Público | Obtener catálogo. |
| `POST` | `/api/ordenes` | Auth (Cliente) | Crear nueva compra. |
| `GET` | `/api/admin/dashboard/stats` | Admin | Obtener estadísticas. |

-----
