# Hướng dẫn setup môi trường phát triển Kotlin

## Menu

- [I. Setup JOOQ trong dự án Kotlin](#i-setup-jooq-trong-dự-án-kotlin)
- [II. Setup Flyway trong dự án Kotlin](#ii-setup-flyway-trong-dự-án-kotlin)

## I. Setup JOOQ trong dự án Kotlin

### Các bước setup JOOQ:

1. **Thêm plugin và dependency vào `build.gradle.kts`:**
   - Đảm bảo có các dòng sau trong phần `plugins` và `dependencies`:
     ```kotlin
     plugins {
         id("nu.studer.jooq") version "10.1.1"
     }
     dependencies {
         implementation("org.springframework.boot:spring-boot-starter-jooq")
         jooqGenerator("org.postgresql:postgresql") // hoặc driver DB bạn dùng
     }
     ```

2. **Cấu hình JOOQ trong `build.gradle.kts`:**
   - Thêm block cấu hình sau:
     ```kotlin
     jooq {
         version.set("3.19.18")
         configurations {
             create("main") {
                 generateSchemaSourceOnCompilation.set(true)
                 jooqConfiguration.apply {
                     jdbc.apply {
                         driver = "org.postgresql.Driver"
                         url = "jdbc:postgresql://localhost:54321/kotlin"
                         user = "postgres"
                         password = "postgres"
                     }
                     generator.apply {
                         name = "org.jooq.codegen.KotlinGenerator"
                         database.apply {
                             inputSchema = "public"
                         }
                         generate.apply {
                             isDaos = true
                             isPojos = true
                         }
                         target.apply {
                             packageName = "com.example.kotlin"
                             directory = "build/generated-src/jooq/main"
                         }
                     }
                 }
             }
         }
     }
     ```

3. **Generate code JOOQ:**
   - Chạy lệnh sau để generate code từ schema:
     ```bash
     ./gradlew generateJooq
     ```
   - Source code sẽ được sinh ra ở thư mục `build/generated-src/jooq/main`.

4. **Sử dụng JOOQ trong code Kotlin:**
   - Import các class đã sinh ra và sử dụng như bình thường.

#### Tham khảo thêm:
- [JOOQ Gradle Plugin](https://github.com/etiennestuder/gradle-jooq-plugin)
- [JOOQ Documentation](https://www.jooq.org/doc/3.19/manual/)

## II. Setup Flyway trong dự án Kotlin

### 1. Thêm plugin và dependency Flyway vào `build.gradle.kts`
- Thêm dòng sau vào phần `plugins`:
  ```kotlin
  id("org.flywaydb.flyway") version "9.22.3"
  ```
- Thêm dòng sau vào phần `dependencies`:
  ```kotlin
  implementation("org.flywaydb:flyway-core:10.13.0")
  ```

### 2. Cấu hình Flyway trong `build.gradle.kts`
- Thêm block cấu hình sau vào cuối file:
  ```kotlin
  flyway {
      url = "jdbc:postgresql://localhost:54321/kotlin"
      user = "postgres"
      password = "postgres"
      cleanDisabled = false
  }
  ```

### 3. Tạo file migration SQL
- Tạo thư mục `src/main/resources/db/migration` nếu chưa có.
- Đặt các file SQL migration vào thư mục này, ví dụ: `V1__init.sql`, `V2__add_table.sql`.
- Đặt tên file theo chuẩn: `V<version>__<description>.sql`.

### 4. Chạy migration
- Khi khởi động ứng dụng Spring Boot, Flyway sẽ tự động thực thi các file migration.
- Hoặc có thể chạy thủ công bằng lệnh:
  ```bash
  ./gradlew flywayMigrate
  ```
- Kiểm tra trạng thái migration:
  ```bash
  ./gradlew flywayInfo
  ```

#### Tham khảo thêm
- [Flyway Documentation](https://flywaydb.org/documentation/)
