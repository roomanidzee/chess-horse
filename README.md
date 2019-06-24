# ChessHorse
Сервис для поиска минимального количества перемещений шахматному коню

# Как запустить сервис:

```
mvn spring-boot:run
```

# Как запустить тесты сервиса:

```
mvn test
```

# API

## Запрос на Spring Controller

### URL
```
localhost:8000/hourse/rest/count?width=10&height=14&start=B1&end=A3
```

### Тело ответа
```
{
  "start_position": {
    "row": 1,
    "column": 1
  },
  "end_position": {
    "row": 3,
    "column": 0
  },
  "moves_count": 1
}
```

## Запрос на Java Servlet

### URL
```
localhost:8000/hourse/servlet/count?width=10&height=14&start=B1&end=A3
```

### Тело ответа
```
{
  "start_position": {
    "row": 1,
    "column": 1
  },
  "end_position": {
    "row": 3,
    "column": 0
  },
  "moves_count": 1
}
```


