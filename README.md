# books

[![maven](https://img.shields.io/badge/maven-v3.6.X-red.svg)](https://maven.apache.org/)

>Projecto en Maven Java 8 con spring boot, actuator, spring data, spring web y una base de datos en H2
>
>Se utiliza lombok para optimizar codigo
>
>Arquitectura MVC
>
>Archivo de ejemplo de para subir datos por upload exampleUploadFile.json
>
>POST http://localhost:8080/books/upload
>
>POST http://localhost:8080/books/delete
>
>POST http://localhost:8080/books/update
>
>POST http://localhost:8080/books/save
>
>GET http://localhost:8080/books/byid
>
>GET http://localhost:8080/books/byauthor
>
>GET http://localhost:8080/books/bytitle
>
>GET http://localhost:8080/books/list
>> http://localhost:8080/books/
>>> http://localhost:8080/books

#Prerequisitos

* [Git](http://git-scm.com/)
* [Maven](https://maven.apache.org/)

#Test

* [Junit](https://junit.org/junit4/)

> mvn test
>Reporte target/site/jacoco/index.html

#Build

> mvn verify
