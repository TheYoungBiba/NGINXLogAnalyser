# Анализатор NGINX логов
Прграмма анлизирует NGINX логи из локальных файлов и URL, поддерживается анализ сразу нескольких файлов.
Результатом работы программы является отчет в формате markdown или adoc в папке resources, содержащий:
- общую информацию;
- 3 самых запрашиваемых ресурса;
- 3 самых возращаемых кода ответа;
- самые популярные типы подключения;
- самые популярные протоколы подключения;

### На вход программе через аргументы командной строки задаётся:
- путь к одному или нескольким NGINX лог-файлам в виде локального шаблона или URL
- опциональные временные параметры from и to в формате ISO8601
- необязательный аргумент формата вывода результата: markdown или adoc

Пример конфигурации запуска: --path src/main/resources/nginx_logs_example.txt
https://raw.githubusercontent.com/elastic/examples/master/Common%20Data%20Formats/nginx_logs/nginx_logs
--format markdown

Учебный проект с курса: https://fintech.tinkoff.ru/academy/java.
Ссылка на репозиторий с ДЗ курса: https://github.com/TheYoungBiba/Tinkoff-Java-Course