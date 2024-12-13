# Branchstock - Sistema de Gestión de Inventario

Branchstock es un sistema para gestionar inventario, construido con Spring Boot, WebFlux (para una arquitectura reactiva) y Mysql. Permite administrar el inventario de forma eficiente y escalable.

## Funcionalidades Principales
* Agregar, actualizar, eliminar y consultar productos de inventario.
* Agregar, actualizar, eliminar y consultar sucursales.
* Agregar, actualizar, eliminar y consultar franquicias.
* Consultar los productos con mas stock de cada sucursal de un franquicia.

## Tecnologías:
* Spring Boot
* Spring WebFlux
* Spring Data MySQL
* MySQL
* Java
* Maven
* Docker

## Ejecución Local
### Requisitos:
* Tener Java instalado.
* Tener Mysql instalado y en ejecución en tu máquina. Asegúrate de que la configuración de conexión en application.properties apunte a tu instancia local de Mysql.

### Clonar el repositorio:
git clone <url_del_repositorio>

### Compilar y ejecutar:
mvn spring-boot:run

### Ejecución con Docker Compose
Requisitos:

* Tener Docker y Docker Compose instalados.
Desde la raíz del proyecto, ejecutar:

`docker-compose up -d`

Esto construirá la imagen de la aplicación y la ejecutará junto con una instancia de MySQL en un contenedor separado. La configuración de la base de datos en el archivo docker-compose.yml se utilizará para la conexión.

## Documentación API (Swagger)
La documentación de la API generada con Swagger está disponible en: http://localhost:8080/docs).

