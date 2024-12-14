## Требования
* Java 17 (corretto-17 Amazon Corretto 17.0.13)
* Gradle: Для сборки и запуска приложения.
## Компиляция проекта
* Для компиляции с Intellij Idea следует ввести через в терминал команду 
```gradle shadowJar```
* JAR-архив появится в ```\build\libs```
### Запуск приложения
Необходимо перейти в ```\build\libs``` и запустить командную строку именно с папки.
Далее написать следующую команду
* ``` java -jar Navigator-1.0-SNAPSHOT.jar ```
## Логирование
Приложение использует Log4j для записи логов:

* Логи выводятся в консоль и файл logs/application.log.
* Это упрощает отладку и мониторинг активности приложения.
## Документация
Проект полностью документирован с помощью JavaDoc:

* Содержит описания всех классов, методов и исключений.
* HTML-документация доступна в директории docs.
