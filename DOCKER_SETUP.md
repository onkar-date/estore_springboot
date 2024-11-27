
# E-Store Project

## Features
- User authentication with roles (Admin, Seller, Customer)
- Product and order management
- Database migrations using Flyway
- Integration with MySQL running in a Docker container

---

## Prerequisites
- Docker Desktop
- Gradle Wrapper (included in the project)
- Node.js and npm
- Java 21 or higher

---

## Setup Instructions

### Backend Setup
1. Clone the repository and navigate to the root directory:
   ```bash
   git clone <repository-url>
   cd <project-directory>
   ```

2. Start the MySQL database using Docker Compose:
   ```bash
   docker-compose up -d
   ```

3. Build and run the backend application:
   ```bash
   ./gradlew bootRun
   ```

## Database Access
- **Host:** `localhost`
- **Port:** `3307` (as mapped in `docker-compose.yml`)
- **Username:** `root`
- **Password:** `root`
- **Database Name:** `estore_db`

You can access the database via a tool like DBeaver or MySQL Workbench.

---

## Running Tests
To run tests for the backend:
```bash
./gradlew test
```

---

## Troubleshooting
- Ensure Docker is running before starting the database.
- Verify that Java and Node.js are installed correctly.

---

## Contribution Guidelines
Contributions are welcome! Please create a pull request with clear documentation.
