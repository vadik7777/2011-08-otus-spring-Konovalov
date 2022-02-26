# 1. Приложение для интеграции с сервером РНИС (rnis-integration)
# 2. Приложение для имитации РНИС сервера (rnis-simulator) и тестов

## Технологический стек для интеграции с сервером РНИС
* OpenJDK 11
* Spring Boot 2.6.3
* Spring Actuator
* Spring Data JPA
* Spring MVC
* Spring Cloud OpenFeign
* Spring Cloud Netflix Hystrix
* Spring Cloud Netflix Dashboard
* Spring Integration
* Spring OpenAPI
* Spring Testing
* ModelMapper
* Lombok
* Postgres или H2

## Приложение для имитации РНИС сервера и тестов
* OpenJDK 11
* Spring Boot 2.6.3
* Spring Testing
* Spring MVC
* ModelMapper
* Lombok

## Порядок запуска

1. Запустить симулятор(сервер по умолчанию стартует на порту 8081),
данные для работы находятся в ресурсах - tree.json
2. Запустить сервер для интеграции (сервер по умолчанию стартует на порту 8080)

## Запуск и сборка
### Сервис из командной строки с помощью maven
После указания необходимых параметров в файле `./src/main/resources/application.properties`.
```shell script
$ ./mvn clean spring-boot:run
```

### Сервис через jar-файл
После указания в файле `./src/main/resources/application.properties` или с передачей переменных окружения.
Сборка:
```shell script
$ ./mvn clean package
```

Запуск:
```shell script
$ java -jar ./target/rnis-simulator-0.0.1-SNAPSHOT.jar
$ java -jar ./target/rnis-0.0.1-SNAPSHOT.jar
```

Профили можно задавать принудительно через аргументы запроса:
```shell script
$ java -jar ./target/rnis-simulator-0.0.1-SNAPSHOT.jar --spring.profiles.active=env
$ java -jar ./target/rnis-0.0.1-SNAPSHOT.jar --spring.profiles.active=env
```