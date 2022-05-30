# frepl-visitor
Simple programing language created with ANTLR4 Visitor.

#### Tools

> Oracle OpenJDK 17.0.2

> Apache Maven 3.8.5

> IntelliJ IDEA Community Edition 2022.1.1
> - ANTLR v4 plugin

#### Build

Regenerate ANTLR visitor from grammar with:

    mvn clean antlr4:antlr4@build-parser

Build compiler with:

    mvn clean package

Run compiler with:

    java -jar target/glang-1.0.jar code/demo.frepl
