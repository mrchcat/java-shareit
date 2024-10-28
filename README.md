# Share it

Сервис совместного использования вещей. 

Зависимости: Spring Boot, Hibernate, драйвер Postgres, Maven, Querydsl, Lombok, логирование logback, zalando

Развертывание приложения происходит в 3 docker-контейнерах: база данных основного сервиса, основной сервис, gateway.
Gateway принимает запросы пользователей, проверяет их и в случае прохождения проверки вызывает уже основной сервис. 
Для создания контейнеров и запуска приложения предназначен файл docker-compose.

Структура базы данных основного сервиса представлена ниже

![Database schema](server/src/main/resources/ER.png)

### Endpoints:

#### Пользователи:

  1) Добавление пользователя

    POST: /users
    Body: {“name”: “Иван”, email: "ivan@mail.ru""}`

  2) Обновление пользователя

    PATCH: /users/{userId}
    Body: {“name”: “Mike”, email: "mike@ya.ru""}

  3) Удаление пользователя

    DELETE: /users/{userId}

  4) Получить пользователя

    GET: /users/{userId}

  5) Получить всех пользователей 

    GET: /users`

#### Предметы:

  1) Создать предмет 

    POST: /items
    Body: {“name”: “книга”, description: "Война и мир", available: true, "requestId": 212311}
    header: userId`    

  2) Обновить предмет
   
    PATCH: /items/{itemId}
    Body: {“name”: “книга”, description: "Война и мир", available: false}
    header: userId`    

  3) Получить предмет
   
    GET: /items/{itemId}
    header: userId`  

  4) Получить все предметы
   
    GET: /items
    header: userId`

  5) Найти предмет
   
    GET: /items/search?text={text}
    header: userId` 

  6) Добавить комментарий
    
    GET: /items/{itemId}/comment
    Body: {"text": "первый"}      
    header: userId` 

#### Бронирования: