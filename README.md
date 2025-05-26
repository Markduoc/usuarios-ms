# 🌿 EcoMarket - API de Gestión de Usuarios

API REST para la gestión de Usuarios del sistema **EcoMarket**, desarrollada con **Spring Boot** y conectada a una base de datos **MySQL**.

---

## 📋 Tabla de Contenidos

- [✨ Características](#-características)
- [🛠 Tecnologías](#-tecnologías)
- [📋 Prerrequisitos](#-prerrequisitos)
- [🚀 Instalación](#-instalación)
- [⚙️ Configuración](#️-configuración)
- [📡 API Endpoints](#-api-endpoints)
- [🔧 Ejemplos de Uso](#-ejemplos-de-uso)
- [📚 Documentación Swagger](#-documentación-swagger)
- [🌐 API Gateway](#-api-gateway)


---

## ✨ Características

- ✅ **Gestión de Usuarios:** CRUD completo con validaciones.
- 🔒 **Validaciones de Negocio:** Id's únicos.
- 🌐 **API RESTful:** Endpoints estandarizados con códigos de estado HTTP.
- 📘 **Documentación OpenAPI:** Swagger UI integrado.

---

## 🛠 Tecnologías

- **Backend:** Spring Boot 3.4.6, Spring Data JPA, Spring Web  
- **Base de Datos:** MySQL 8.0  
- **Herramientas:** Maven, Lombok, Spring Boot Actuator  
- **Documentación:** Springdoc OpenAPI (Swagger)  
- **Java:** OpenJDK 17

---

## 📋 Prerrequisitos

Asegúrate de tener instalado:

- Java 17 o superior  
- Maven 3.6 o superior  
- MySQL 8.0  
- Git  

---

## 🚀 Instalación

### 1. Clonar el repositorio

```bash
git clone https://github.com/Markduoc/usuarios-ms.git
cd usuarios-ms
```
### 2. Compilar el proyecto
```bash
mvn clean package spring-boot:repackage -DskipTests
```
### 3. ejecutar el proyecto
```bash
mvn clean package spring-boot:repackage -DskipTests
```
## ⚙️ Configuración local

Para desarrollo local, puedes sobrescribir las propiedades creando un archivo llamado application-local.properties dentro del directorio src/main/resources/:

- server.port=8082
- spring.datasource.url=jdbc:mysql://localhost:3306/ecomarket_local
- spring.datasource.username=tu_usuario
- spring.datasource.password=tu_password

Asegúrate de tener la base de datos ecomarket_local creada en tu instancia local de MySQL.

## 📖 API Endpoints

Usuarios
| Método | Endpoint                        | Descripción                  |
|--------|----------------------------------|------------------------------|
| GET    | `/api/v1/usuarios`             | Listar todos los Usuarios    |
| GET    | `/api/v1/usuarios/{id}`        | Obtener Usuario por ID       |
| POST   | `/api/v1/usuarios`             | Crear nuevo Usuario          |
| PUT    | `/api/v1/usuarios/{id}`        | Actualizar Usuario           |
| DELETE | `/api/v1/usuarios/{id}`        | Eliminar Usuario             |

## 🔧 Ejemplos de Uso

### Crear un Usuario

```bash
curl -X POST http://localhost:8082/api/v1/usuarios \
  -H "Content-Type: application/json" \
  -d '{
        "run": 219040636,
        "prNombre": "Marco",
        "seNombre": "Agustin",
        "apPaterno": "Corrales",
        "apMaterno": "Ahumada"
    }'
```
### Listar todos los Usuarios
```bash
curl http://localhost:8082/api/v1/usuarios
```
### Obtener producto específico
```bash
curl http://localhost:8082/api/v1/usuarios/1
```

## 📚 Documentación Swagger
Una vez que la aplicación esté ejecutándose, puedes acceder a la documentación interactiva en:

[🔗 Ver Swagger UI](http://localhost:8082/swagger-ui.html)

## 🌐 API Gateway
Este microservicio puede ser gestionado a través de una API Gateway desarrollada con Spring Cloud Gateway, la cual centraliza el acceso a los distintos servicios del sistema EcoMarket.

🔗 Repositorio de la API Gateway:
👉 [github.com/JorgeToledoIporre/api-gateway](https://github.com/JorgeToledoIporre/ApiGatewayEcoMarket)