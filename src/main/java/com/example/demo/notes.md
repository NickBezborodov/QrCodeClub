Что я сегодня сделал:
- написал 3 теста для ClubServiceImpl
- научился мокать репозитории через @Mock и when().thenReturn()
- научился проверять исключения через assertThrows
- понял, что маппер мокается, потому что тестируем сервис, а не маппер


...



# QR Code Club — что я сделал

## Проект
REST API для закрытого клуба с одноразовым входом по QR-коду.

## Технологии
Java 26, Spring Boot 3.5.14, Spring Data JPA (Hibernate), PostgreSQL, Docker, Lombok, Maven, JUnit 5, Mockito

---

## База данных

### Таблицы
- `participants` — участники (id, first_name, last_name, middle_name)
- `qr_codes` — QR-коды (id, participant_id, qr_uuid)
- Связь: One-to-One через `participant_id → participants.id`
- `ON DELETE CASCADE` — удаление участника удаляет его QR

### Где лежит
- Docker контейнер `my-postgres`, порт 5432
- База `mydb`
- Подключение настроено в `application.yml`

---

## Структура проекта

controller → ClubController (REST API)
service → ClubService (интерфейс)
service/impl→ ClubServiceImpl (логика)
repository → ParticipantRepo, QrCodeRepo (доступ к БД)
entity → Participant, QrCode (таблицы)
dto → ParticipantDto (передача данных клиенту)
mapper → ParticipantMapper (entity ↔ DTO)
exception → QrNotFoundException, GlobalExceptionHandle



---

## REST API

| Метод | URL | Что делает |
|-------|-----|------------|
| GET | /api/v1/club | Список всех участников |
| POST | /api/v1/club/add/participant | Добавить участника + QR |
| PUT | /api/v1/club/{id} | Изменить участника |
| DELETE | /api/v1/club/{id} | Удалить участника |
| GET | /api/v1/club/check/{uuid} | Вход по QR |

---

## Бизнес-логика

### Вход по QR (processQR)
1. Получает UUID из URL
2. Ищет QrCode в базе через `findByQrUuid`
3. Если не найден → `QrNotFoundException` → 404
4. Если найден → возвращает ФИО участника
5. Меняет UUID на новый (`UUID.randomUUID()`)
6. Сохраняет QrCode

### Валидация
- `@NotBlank` + `@Size(min=2)` на firstName и lastName
- `@Size(min=2)` на middleName (опционально)
- Ошибки возвращаются как JSON с полями и сообщениями
- Статус 400 Bad Request

---

## Что я узнал про Spring Boot

### Аннотации
- `@Entity`, `@Table` — класс отражает таблицу
- `@Id`, `@GeneratedValue` — первичный ключ
- `@OneToOne`, `@JoinColumn`, `mappedBy` — связь таблиц
- `@Service`, `@RestController`, `@Repository` — слои приложения
- `@Transactional` — только на методы, которые меняют данные
- `@RestControllerAdvice`, `@ExceptionHandler` — обработка ошибок
- `@Valid` — включает валидацию DTO

### Lombok
- `@Getter`, `@Setter` — вместо `@Data` на entity
- `@Builder` — создание объектов
- `@RequiredArgsConstructor` — конструктор для final полей
- Почему `@Data` опасно: `@ToString` и `@EqualsAndHashCode` вызывают рекурсию и ломают прокси Hibernate

### JPA / Hibernate
- `findById`, `findAll`, `save`, `delete` — базовые методы
- `findByQrUuid` — свой метод запроса (Spring Data сам пишет SQL)
- `CascadeType.ALL`, `orphanRemoval` — каскадное удаление
- `ddl-auto: validate` — проверяет соответствие таблиц и entity

### Docker
- `docker run --name my-postgres ...` — запуск PostgreSQL
- `docker exec -it my-postgres psql -U postgres` — вход в контейнер
- Порты: `5432:5432` — проброс порта

---

## Что я узнал про тесты

### Подход
- Тестируем один метод сервиса
- Всё, что вне метода (репозитории, мапперы) — мокаем
- Не мокаем то, что тестируем

### Mockito
- `@Mock` — создать мок-объект
- `@InjectMocks` — внедрить моки в тестируемый класс
- `@ExtendWith(MockitoExtension.class)` — включить Mockito
- `when(...).thenReturn(...)` — настроить поведение мока
- `assertThrows` — проверить, что метод выбросил исключение
- `assertEquals`, `assertNotEquals` — сравнить результат

### Три теста для processQR
1. Возвращает правильное ФИО
2. Выбрасывает исключение, если QR не найден
3. UUID меняется после входа

---

## Что дальше
- Swagger (документация API)
- Docker Compose (приложение + база одной командой)
- Новый проект (ToDo List) для закрепления