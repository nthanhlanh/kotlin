# Project Setup

## Generate Jooq Code

To generate Jooq code, run the following command in your project directory:

```
./gradlew generateJooq
```

This will generate the necessary Jooq classes based on your configuration.

## Database Migration with Flyway

Flyway is integrated for managing database migrations.

### How to use:
1. Place migration scripts in `src/main/resources/db/migration`.
   - Example: `V1__init.sql`, `V2__add_table.sql`
2. Flyway will automatically run these scripts on application startup.
3. Migration scripts should follow the naming convention: `V<version>__<description>.sql`.

### Useful commands
- To run migrations manually:
```
./gradlew flywayMigrate
```
- To check migration status:
```
./gradlew flywayInfo
```
- To clean the database (use with caution):
```
./gradlew flywayClean
```

Refer to [Flyway documentation](https://flywaydb.org/documentation/) for more details.
