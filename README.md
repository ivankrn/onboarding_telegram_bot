# Onboarding Telegram bot

Телеграм-бот для онбординга сотрудников

## Технологический стек

- Frontend: Angular
- Backend: Spring Boot, Hibernate
- Telegram API

## Основные функции

- Чтение статей
- Прохождение тестов на усвоение материалов
- Сбор обратной связи и статистики по статьям и тестам
- Панель управления для добавления и редактирования статей и тестов

## Установка и настройка

1. Клонируйте репозиторий:

```bash
git clone https://github.com/ivankrn/onboarding_telegram_bot.git
```

2. Установите зависимости для frontend:
```bash
cd onboarding-telegram-bot-frontend
npm install
```

3. Установите зависимости для backend:
```bash
cd onboarding-telegram-bot-backend
mvn install
```

4. Создайте файл config.properties в каталоге *onboarding-telegram-bot-backend/src/main/resources* и добавьте следующие настройки:
```
# Настройки Telegram бота
bot.name=имя_вашего_бота
bot.token=токен_вашего_бота

# Настройки БД
spring.jpa.database = PostgreSQL
spring.jpa.show-sql = false
spring.jpa.hibernate.ddl-auto = update
spring.datasource.driverClassName = org.postgresql.Driver
spring.datasource.url = jdbc:postgresql://localhost:5432/название_вашей_базы_данных
spring.datasource.username = логин_вашей_базы_данных
spring.datasource.password = пароль_вашей_базы_данных
```

5. Запустите frontend:
```bash
cd onboarding-telegram-bot-frontend
ng serve
```

6. Запустите backend:
```bash
cd onboarding-telegram-bot-backend
mvn spring-boot:run
```
