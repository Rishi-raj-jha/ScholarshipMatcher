# ScholarshipMatcher

A local prototype for finding demonstration scholarship records, checking preliminary eligibility, and tracking personal application progress.

> **Demo data:** Scholarship records and criteria are illustrative, unverified examples. The app does not fetch live listings from NSP, MahaDBT, or other providers. A match is not an official eligibility decision. Apply through the provider's official site.

## Requirements

- Java 21
- Maven 3.9+
- MySQL 8.0 or later (tested locally with MySQL 8.4.13)
- Python 3

## Run on macOS

Install Java/Maven if needed and MySQL 8.4:

```bash
brew install openjdk@21 maven mysql@8.4
brew services start mysql@8.4
```

Use the Homebrew client explicitly if another MySQL install (such as Anaconda) comes earlier on `PATH`:

```bash
"$(brew --prefix mysql@8.4)/bin/mysql" -u root
```

At the `mysql>` prompt, create the database and a dedicated local app user. Replace the sample password with a private password; do not commit it:

```sql
CREATE DATABASE scholarshipmatcher CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE USER 'scholarship_app'@'localhost' IDENTIFIED BY 'replace-with-a-strong-password';
GRANT ALL PRIVILEGES ON scholarshipmatcher.* TO 'scholarship_app'@'localhost';
EXIT;
```

In a terminal, from the project root, configure the backend and start it:

```bash
export DB_URL='jdbc:mysql://localhost:3306/scholarshipmatcher?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC'
export DB_USER='scholarship_app'
export DB_PASSWORD='your-private-password'
export CORS_ORIGIN='http://localhost:5500'
cd backend
mvn test package
mvn spring-boot:run
```

Flyway creates the tables and four marked demo records on first startup. Keep this terminal open. In a second terminal from the project root, start the frontend:

```bash
python3 -m http.server 5500 --directory frontend
```

Open <http://localhost:5500>. Guest browsing and matching remain usable without an account. Guest profiles, saved items, check history, and application tracking use this browser's local storage. Account profiles, bookmarks, matching summaries, and application tracker data use MySQL.

## API and matching behavior

The frontend loads scholarship records from `GET /api/scholarships`; if the backend is offline it falls back to the clearly marked local demo data. Account matching uses `POST /api/match`, shows those returned results, and saves the matching totals to `POST /api/checks`. The backend accepts the frontend's short profile keys (`level`, `year`, `income`) and the saved-profile keys (`educationLevel`, `academicYear`, `familyIncome`).

Account writes use a session cookie and CSRF token. The account profile stores one student per account; the existing multiple-student workspace remains available locally in guest mode.

## Deploy the complete app on Railway

The root `Dockerfile` bundles the HTML, CSS, and JavaScript into the Spring Boot app. The browser and API then use one HTTPS address, so login cookies work on the same site. The MySQL database remains a separate, private Railway service.

1. Push this project folder to a GitHub repository and connect that repository in Railway, or deploy this local folder with Railway's CLI.
2. In the Railway project, add a MySQL service. Keep it private.
3. Add a web service for this repository. Railway will use the root `Dockerfile` automatically. Generate a public domain for this service.
4. In the web service's Variables, add the following. If the database service has a different name, replace `MySQL` with its exact Railway service name.

   ```text
   DB_URL=jdbc:mysql://${{MySQL.MYSQLHOST}}:${{MySQL.MYSQLPORT}}/${{MySQL.MYSQLDATABASE}}?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
   DB_USER=${{MySQL.MYSQLUSER}}
   DB_PASSWORD=${{MySQL.MYSQLPASSWORD}}
   CORS_ORIGIN=https://${{RAILWAY_PUBLIC_DOMAIN}}
   SESSION_COOKIE_SECURE=true
   SESSION_COOKIE_SAME_SITE=lax
   ```

5. Deploy the web service. Flyway creates the database tables and the four clearly marked demo scholarship records on first startup. Open the service's public domain to use the app.

Keep database passwords in Railway Variables; never put them in `frontend/config.js`, source files, or GitHub. Railway bills by plan and resource use; check its current [pricing](https://docs.railway.com/pricing) before deploying. The scholarships remain illustrative and unverified, so this is a public prototype rather than an official scholarship listing service.

For local development, keep using the commands in [Run on macOS](#run-on-macos). The frontend config selects localhost for the local frontend server and uses the current site origin for the deployed app.

## Build and known limits

Run `mvn test package` from `backend/`. The repository currently has no automated test source files, so this command compiles and packages the backend but does not exercise database-backed account workflows. The database migrations have been run locally on MySQL 8.4.13; Flyway may warn that the version managed by Spring Boot 3.4.3 has not been tested against MySQL 8.4, although the migrations complete successfully.

Scholarship data remains demo-only. Verified records, deadline/document tracking, production security review, and end-to-end browser tests are still needed before public production use.
