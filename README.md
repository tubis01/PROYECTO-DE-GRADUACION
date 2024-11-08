# PGwebONG

PGwebONG es una aplicación web desarrollada para gestionar personas y organizaciones en una base de datos. Utiliza tecnologías como Java, Spring Boot, Maven y PostgreSQL.

## Características

- **API REST**: Implementación de servicios RESTful para la gestión de personas y organizaciones.
- **Autenticación y Autorización**: Configuración de seguridad para proteger los endpoints.
- **CORS**: Configuración de CORS para permitir orígenes específicos y métodos HTTP.
- **Encriptación**: Servicios de encriptación y desencriptación de datos.
- **Persistencia**: Uso de JPA para la persistencia de datos en PostgreSQL.
- **Despliegue**: Configuración para despliegue utilizando Maven.

## Requisitos

- **Java** 11 o superior
- **Maven** 3.6.3 o superior
- **PostgreSQL** 12 o superior

## Instalación

1. **Clonar el repositorio**:

   ```bash
   git clone https://github.com/tu-usuario/PGwebONG.git
   cd PGwebONG

2. **Crear la base de datos en PostgreSQL**:
   ```sql
   CREATE DATABASE NameBD
   ```
3. **Configurar la base de datos en src/main/resources/application.properties**:
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/NameBD
   spring.datasource.username=tu-usuario
   spring.datasource.password=tu-contraseña
   ```
4. **Configurar el correo electrónico en src/main/resources/application.properties**:
      ```properties
      spring.mail.host=smtp.gmail.com
      spring.mail.port=587
      spring.mail.username=tu-email@gmail.com
      spring.mail.password=tu-contraseña
      spring.mail.properties.mail.smtp.auth=true
      spring.mail.properties.mail.smtp.starttls.enable=true
      spring.mail.properties.mail.smtp.ssl.trust=smtp.gmail.com
   ```
5. **Complicar y ejecutar la aplicacion**:
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```














      
