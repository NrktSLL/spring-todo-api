# TODO API
> Basic Todo App API Example

<p align="center">
<img src="https://github.com/NrktSLL/spring-todo-api/blob/master/image/spring-boot-todo-api.png" alt="Spring Boot Todo Example" width="40%" height="40%"/> 
</p>


## Installation
Prerequisites:
*  Docker
*  Docker Compose

```
docker-compose up -d
```

## Build/Package
```
mvn clean package
```

## Testing
```
mvn test
```

## Used Dependencies
* Spring Boot Web
* Spring Boot Actuator
* Spring Boot Validation
* Spring Boot Data Couchbase
* Spring Boot Security
* Auth0 JWT
* Springdoc OpenApi (openapi-ui + security)
* Mapstruct
* Lombok

## Abilities
* JWT
* Couchbase Cache

## Swagger
> **Access : http://localhost:8080/api/documentation/**

<img src="https://github.com/NrktSLL/spring-todo-api/blob/master/image/swagger.png" alt="Spring Boot TODO Example Swagger" width="100%" height="100%"/> 

## Couchbase UI
> **Access : http://localhost:8091/**

<img src="https://github.com/NrktSLL/spring-todo-api/blob/master/image/couchbase-ui.png" alt="Spring Boot TODO Example COUCHBASE" width="100%" height="100%"/> 
