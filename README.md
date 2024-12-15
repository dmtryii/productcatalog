# Product Catalog API

This project is a RESTful web service for managing a product catalog with caching capabilities to improve read performance. It is built with Spring Boot and Dockerized for easy deployment.

## Running the Project with Docker

```bash
git clone https://github.com/dmtryii/productcatalog.git
cd productcatalog
docker build -t productcatalog .
docker run -p 8080:8080 productcatalog
```

## API Documentation

Once the application is running, you can view the automatically generated API documentation by navigating to the following URL:
[Swagger](https://www.docker.com/get-started)

This will open an interactive Swagger UI that allows you to explore the available API endpoints, view request/response models, and test the API directly from your browser.

## Technologies Used

- **Spring Boot**: Simplifies development of production-ready, stand-alone Java applications with minimal setup.
- **Spring Data JPA**: Automates database access, eliminating the need for complex SQL queries.
- **Spring Boot Starter Cache**: Improves read performance by reducing database load for frequently requested data.
- **H2 Database**: Lightweight, in-memory database for development and testing.
- **Flyway**: Manages and versions database migrations, ensuring consistency across environments.
- **Lombok**: Reduces boilerplate code like getters, setters, and constructors.
- **Springdoc OpenAPI**: Automatically generates API documentation in OpenAPI format.
- **Docker**: Ensures consistent deployment across platforms by containerizing the application and its dependencies.

These technologies were chosen to simplify development, improve performance, and ensure ease of deployment.
