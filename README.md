API Rest para upload e download de arquivos com Spring Boot + armazenamento em banco de dados
MySQL

## Tecnologias

- [Spring Boot](https://spring.io/projects/spring-boot)
- [MySQL](https://www.mysql.com/)
- [Spring Data JPA](https://spring.io/projects/spring-data-jpa)

## Como Executar

- Clonar repositório git
```
git clone git@github.com:igorbarret0/file-manager.git
```

- Construir o projeto:
```
./mvnw clean package
```


A API poderá ser acessada em [localhost:8001](http://localhost:8001).

## API Endpoints

Para fazer as requisições HTTP abaixo, foi utilizada a ferramenta [Postman](https://www.postman.com/)

-  FILE
```
POST /files/upload - Faer upload de um arquivo
```

```
GET /files - Obter a lista de todos os arquivos
```

```
POST /files/find/{id} - Fazer downlaod de um arquivo
```