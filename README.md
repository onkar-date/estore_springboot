Here’s an updated version of the documentation for setting up the project with Gradle:

# **Project Setup Documentation**

This guide explains how to set up the project, including database initialization, application startup, and seeding data into the database.

---

## **Prerequisites**

Ensure the following are installed on your system:
- **Docker** (version 20.10 or above)
- **Docker Compose** (version 1.29 or above)
- **Java** (JDK 17 or compatible)
- **Gradle** (version 7.0 or above)

---

## **Project Structure**

- `docker-compose.db.yml`: Sets up the MySQL database.
- `docker-compose.data-seeder.yml`: Seeds the database with initial product data using a Python script.
- `src/main/resources/db/migration`: Contains Flyway migration scripts.
- `src/main/resources/data_seed`: Contains `loadData.py` and product JSON files.
- `application.properties`: Configures the app to connect to the database.

---

## **Step-by-Step Setup**

### 1. **Set Up the MySQL Database**
Run the following command to start the MySQL database container:

```bash
docker-compose -f docker-compose.db.yml up -d --build
```

- This command will build and run the MySQL container.
- The database will be available on `localhost:3307` with the database name `estore_db`.

**Note**: This container only runs the database. The migration scripts for creating tables will be executed when the application starts.

---

### 2. **Build and Start the Application**

- Build the application using Gradle:

```bash
./gradlew build
```

OR

```cmd
gradlew build
```

- Once the application is built, you can run it with the following command:

```bash
./gradlew bootRun
```

OR

```cmd
gradlew bootRun
```

- The application will connect to the `estore_db` MySQL database on `localhost:3307` (as defined in `application.properties`) and automatically execute the Flyway migration scripts to create the necessary tables.

**Note**: The migration scripts are configured to run at application startup and create the required database schema.

---

### 3. **Seed Data into the Database**

Once the database is set up and the application is running, run the following command to seed the products data into the database:

```bash
docker-compose -f docker-compose.data-seeder.yml up --build
```

- This command will build and run a Python container that:
    1. Installs dependencies from `requirements.txt`.
    2. Runs the `loadData.py` script, which reads product data from `products.json` and inserts it into the `products` and `images` tables in the database.

- The `data-seeder` container depends on the `db` container, so it will only run after the database container is available.

**Note**: Ensure the `data_seed` directory has the required files (`loadData.py`, `products.json`, etc.) before running the seeder.

---

## **Command Summary**

- **Start MySQL database** (from `docker-compose.db.yml`):

```bash
docker-compose -f docker-compose.db.yml up -d --build
```

- **Build and run the application**:

```bash
./gradlew build
./gradlew bootRun
```

OR

```cmd
gradlew build
gradlew bootRun
```

- **Seed data into the database** (from `docker-compose.data-seeder.yml`):

```bash
docker-compose -f docker-compose.data-seeder.yml up --build
```

---

## **Troubleshooting**

- **Cannot connect to the database**:
    - Ensure the `db` container is running and accessible on port `3307` (as defined in `docker-compose.db.yml`).
    - Check that the `application.properties` is correctly configured to use `host.docker.internal:3307` for the MySQL connection.

- **Flyway migration fails**:
    - If the tables are not being created, check the application logs to ensure Flyway is running the migration scripts successfully.
    - You can also manually verify the migrations in the `src/main/resources/db/migration` directory.

- **Data seeding fails**:
    - Ensure that the `loadData.py` script is correctly reading the JSON file and inserting data into the database.
    - If the images are not found, make sure the files are available in the expected directory.

---

By following these steps, you will have a fully set up environment with MySQL, your application, and seeded data.