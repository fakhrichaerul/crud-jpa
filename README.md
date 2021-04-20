# Aplikasi Crud Spring Data JPA With Custom Query HQL and Native SQL

Features :

    • CRUD employee
    • CRUD department
    • Basic custom query native SQL and HQL on employee
    • Advanced custom query native SQL and HQL on department (JOIN, LIKE)

Quickstart :

1. Jalankan `docker-compose.yml` di root project ini terlebih dahulu untuk membuat container database yang sesuai dengan username, password
   dan database di `application.properties`
   
    ```
   docker-compose up -d     // Untuk menjalankan container
   docker-compose down      // Untuk mematikan container
    ```

2. Jalankan aplikasi
    ```
    mvn spring-boot:run
    ```

3. Dokumentasi Rest API dapat diakses di url :

   ```
   http://localhost:8080/swagger-ui.html
   ```
   