# QR Code Club

REST API для закрытого клуба с одноразовым входом по QR-коду.

## Как работает

1. Участник получает уникальный QR-код (UUID)
2. Прикладывает QR на входе — система проверяет UUID
3. Если UUID найден — возвращает ФИО участника и **меняет UUID на новый**
4. Старый UUID больше не действует (одноразовый вход)

## Технологии

- Java 26
- Spring Boot 3.5.14
- Spring Data JPA
- PostgreSQL (в Docker)
- Lombok
- Maven

ве таблицы, связанные One-to-One:

**participants**

| Колонка | Тип | Описание |
|---------|-----|----------|
| id | BIGSERIAL | Первичный ключ |
| first_name | VARCHAR(50) | Имя |
| last_name | VARCHAR(50) | Фамилия |
| middle_name | VARCHAR(50) | Отчество (опционально) |

**qr_codes**

| Колонка | Тип | Описание |
|---------|-----|----------|
| id | BIGSERIAL | Первичный ключ |
| participant_id | BIGINT | Внешний ключ → participants |
| qr_uuid | UUID | Уникальный код (меняется при входе) |

## REST API

| Метод | URL | Описание |
|-------|-----|----------|
| GET | /api/v1/club | Список всех участников |
| POST | /api/v1/club/add/participant | Добавить участника |
| PUT | /api/v1/club/{id} | Изменить участника |
| DELETE | /api/v1/club/{id} | Удалить участника |
| GET | /api/v1/club/check/{uuid} | Вход по QR |
