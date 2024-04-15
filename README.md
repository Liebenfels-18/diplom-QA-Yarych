# Дипломный проект профессии «Тестировщик»

Дипломный проект представляет собой автоматизацию тестирования комплексного сервиса.

## Документы
* [План автоматизации](https://github.com/Liebenfels-18/diplom-QA-Yarych/blob/master/Plan.md)
* [Отчет по итогам тестирования](https://github.com/Liebenfels-18/diplom-QA-Yarych/blob/master/Report.md)
* [Отчет по итогам автоматизированного тестирования]()

На локальном компьютере заранее должны быть установлены IntelliJ IDEA и Docker.

## Процедура запуска авто-тестов:

**1.** Склонировать на локальный репозиторий [Дипломный проект](https://github.com/Liebenfels-18/diplom-QA-Yarych).

**2.** Запустить Docker Desktop

**3.** Открыть проект в IntelliJ IDEA

**4.** В терминале запустить контейнеры с помощью команды:

    docker-compose up -d

**5.** Запустить целевое приложение с помощью команды в терминале:

     для mySQL: 
    java "-Dspring.datasource.url=jdbc:mysql://localhost:3306/app" -jar artifacts/aqa-shop.jar 

     для postgresgl:
     java "-Dspring.datasource.url=jdbc:postgresql://localhost:5432/app" -jar artifacts/aqa-shop.jar

**5.** Открыть вторую вкладку терминала

**6.** Во втором терминале запустить тесты командой:

    для mySQL:
    ./gradlew clean test -DurlDB="jdbc:mysql://localhost:3306/app"

    для postgresgl: 
    ./gradlew clean test -DurlDB="jdbc:postgresql://localhost:5432/app"

**7.** Создать отчёт Allure и открыть в браузере с помощью команды в терминале:

    ./gradlew allureServe

**8.** Для завершения работы allureServe выполнить команду:

    Ctrl+C

**9.** Для остановки работы контейнеров выполнить команду:

    docker-compose down
