Процедура запуска тестов

1. Клонировать репозиторий
2. Убедиться, что на компьютере установлены приложения IntelliJ IDEA, Docker Desktop
3. Убедиться, что порты 3306, 5432, 8080, 9999 свободны
4. Открыть репозиторий в IDE
5. В терминале запустить команду "docker-compose up"
6. Открыть новое окно терминала и запустить SUT:

*  Для **MySQL** командой *"java -jar artifacts/aqa-shop.jar -port:8080"*


*  Для **PostgreSQL** командой "*java -jar artifacts/aqa-shop.jar -port:8080 -P:db.url=jdbc:postgresql://localhost:5432/app"*

7. Для запуска тестов ввести в новом терминале команду:


 *  Для **MySQL**: *.\gradlew test*


 *  Для **PostgreSQL**: *.\gradlew test -P:db.url=jdbc:postgresql://localhost:5432/app*
   
8. Для формирования отчета Allure запустить в текущем терминале команду: ".\gradlew allureServe
9. Для завершения тестирования в открытых терминалах ввести команду: *Ctrl+C*