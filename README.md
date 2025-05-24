# Documentación del Proyecto Universidad

## Tabla de Contenidos
- [Descripción del Proyecto](#descripción-del-proyecto)
- [Estructura del Proyecto](#estructura-del-proyecto)
- [Tecnologías Utilizadas](#tecnologías-utilizadas)
- [Instrucciones de Configuración](#instrucciones-de-configuración)
- [Funcionalidades](#funcionalidades)
- [Endpoints de la API](#endpoints-de-la-api)
- [Configuración de Seguridad](#configuración-de-seguridad)
- [Configuración de Swagger](#configuración-de-swagger)
- [Validación](#validación)
- [Contacto](#contacto)

## Descripción del Proyecto
Este proyecto es una aplicación Spring Boot diseñada para gestionar datos relacionados con la universidad, incluyendo profesores, estudiantes, cursos e inscripciones. Proporciona una API RESTful para realizar operaciones CRUD e incluye características de seguridad como autenticación y autorización.

## Estructura del Proyecto
El proyecto está organizado en los siguientes paquetes principales:
```
src/main/java/com/universidad/
├── controller/ # Controladores REST
├── dto/ # Objetos de Transferencia de Datos
├── model/ # Entidades de la base de datos
├── repository/ # Repositorios JPA
├── service/ # Lógica de negocio
├── registro/ # Autenticación y seguridad
└── validation/ # Validación de datos
```

## Paquetes y Clases Principales:
- **com.universidad.controller:** Contiene los controladores REST para gestionar entidades como Docente, Estudiante, Materia, Inscripcion y EvaluacionDocente.

- **com.universidad.dto:** Define los Objetos de Transferencia de Datos (DTO) utilizados para el intercambio de datos entre el cliente y el servidor.

- **com.universidad.model:** Define las clases de entidad que representan las tablas de la base de datos.

- **com.universidad.repository:** Contiene los repositorios Spring Data JPA para la interacción con la base de datos.

- **com.universidad.service:** Define las interfaces y las implementaciones de la capa de servicio para la lógica de negocio.

- **com.universidad.service.impl:** Contiene las implementaciones para las interfaces de la capa de servicio.

- **com.universidad.registro:** Maneja la autenticación y autorización del usuario.

- **com.universidad.registro.config:** Contiene las clases de configuración para la inicialización de la base de datos, la seguridad y Swagger.

- **com.universidad.registro.controller:** Contiene los controladores para la autenticación y gestión de usuarios.

- **com.universidad.registro.dto:** Define los DTO para la autenticación.

- **com.universidad.registro.model:** Define las entidades Rol y Usuario.

- **com.universidad.registro.repository:** Contiene los repositorios para Rol y Usuario.

- **com.universidad.registro.security:** Implementa la autenticación basada en JWT.

- **com.universidad.registro.service:** Implementa el servicio de detalles del usuario para la autenticación.

- **com.universidad.validation:** Contiene las clases para la validación de datos de entrada y el manejo global de excepciones.

- **com.universidad.UniversidadApplication:** La clase principal que inicia la aplicación Spring Boot.

- **src/main/resources:** Contiene el archivo application.properties para configurar la aplicación.
## Tecnologías Utilizadas
- **Backend**: Java 11+, Spring Boot
- **Persistencia**: Spring Data JPA, H2 (desarrollo)
- **Seguridad**: Spring Security, JWT
- **Documentación**: Swagger
- **Gestión de dependencias**: Maven

## Instrucciones de Configuración
1. Clonar el repositorio:
```bash
git clone <url-del-repositorio>
cd <nombre-del-proyecto>
Configurar la base de datos (editar application.properties)
Compilar y ejecutar:

mvn clean install
mvn spring-boot:run
```
## Funcionalidades
- CRUD completo para entidades académicas
- Autenticación JWT
- Control de acceso por roles
- Validación de datos
- Documentación API con Swagger

## Endpoints de la API
**Autenticación**
- POST /api/auth/login - Autenticación de usuarios
**Estudiantes**
- GET /api/estudiantes - Listar todos
- POST /api/estudiantes - Crear nuevo
- GET /api/estudiantes/{id} - Obtener por ID
**Docentes**
- GET /api/docentes - Listar todos
- POST /api/docentes - Crear nuevo
- PUT /api/docentes/{id} - Actualizar

## Configuración de seguridad
La seguridad se implementa con:
- Spring Security
- JSON Web Tokens (JWT)
- BCrypt para hashing de contraseñas
- Control de acceso basado en roles

## Configuración de Swagger
Acceder a la documentación en:
http://localhost:8080/swagger-ui/index.html
Configuración principal en:
```com.universidad.registro.config.SwaggerConfig```
## Validación
Se implementan:
- Validadores customizados
- Manejo global de excepciones
- Manejo de roles
