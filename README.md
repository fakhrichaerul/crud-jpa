# Aplikasi Microservices User dan Department

Cara menjalankan :

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

3. Template request url

    ```
    http://localhost:8080/department
    ```

   Template request json

    ```json
    {
    "departmentName":"IT"
    }
    ```
   