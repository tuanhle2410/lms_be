Attention: by Default, the Liquibase is disabled in application.yml

ENABLE LIQUIBASE: Import the @EnableLiquibaseConfig Annotation into Spring Boot Application


Generate from DB -> changelog -> model
1. GENERATE LIQUIBASE CHANGE LOG: mvn liquibase:generateChangeLog
2. GENERATE LIQUIBASE CHANGE LOG WITH DATA: mvn liquibase:generateChangeLog -Dliquibase.diffTypes=data

Generate from Model -> changelog -> to DB
1. mvn liquibase:diff

Note: It does not always work as expected
