# 💰 Spendly — Personal Expense Tracker

A full-stack expense tracker built with **Java Spring Boot** (backend) and **HTML + CSS + JavaScript** (frontend).

---

## 📁 Project Structure

```
expense-tracker/
├── backend/                    ← Spring Boot application
│   ├── pom.xml
│   └── src/main/java/com/expensetracker/
│       ├── ExpenseTrackerApplication.java
│       ├── model/
│       │   ├── Expense.java
│       │   ├── Category.java
│       │   └── Budget.java
│       ├── dto/
│       │   ├── ExpenseDTO.java
│       │   └── SummaryDTO.java
│       ├── repository/
│       │   ├── ExpenseRepository.java
│       │   ├── CategoryRepository.java
│       │   └── BudgetRepository.java
│       ├── service/
│       │   ├── ExpenseService.java
│       │   └── CategoryService.java
│       ├── controller/
│       │   ├── ExpenseController.java
│       │   └── CategoryController.java
│       └── config/
│           ├── GlobalExceptionHandler.java
│           └── DataSeeder.java
│
└── frontend/                   ← Static HTML/CSS/JS frontend
    ├── index.html              ← Dashboard
    ├── css/style.css
    ├── js/
    │   ├── api.js
    │   └── utils.js
    └── pages/
        ├── expenses.html       ← Expense list with CRUD
        ├── reports.html        ← Charts & analytics
        └── categories.html     ← Category management
```

---

## 🚀 How to Run

### Prerequisites
- **Java 17+**
- **Maven 3.6+**
- A modern browser

### Step 1 — Run the Backend

```bash
cd backend
mvn spring-boot:run
```

The API will start at: **http://localhost:8080**

> Sample data is auto-seeded on first run (categories + 20 expenses).

### Step 2 — Open the Frontend

Simply open `frontend/index.html` in your browser.

> **Or** serve it with a local server (recommended to avoid CORS issues with file://):
> ```bash
> cd frontend
> npx serve .         # if Node.js is installed
> # OR
> python3 -m http.server 3000
> ```
> Then open: http://localhost:3000

---

## 🌐 REST API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/expenses` | Get all expenses (supports filters) |
| GET | `/api/expenses?categoryId=1` | Filter by category |
| GET | `/api/expenses?startDate=2025-01-01&endDate=2025-01-31` | Filter by date range |
| GET | `/api/expenses?keyword=swiggy` | Search by keyword |
| GET | `/api/expenses/{id}` | Get single expense |
| POST | `/api/expenses` | Create expense |
| PUT | `/api/expenses/{id}` | Update expense |
| DELETE | `/api/expenses/{id}` | Delete expense |
| GET | `/api/expenses/summary` | Dashboard analytics |
| GET | `/api/categories` | Get all categories |
| POST | `/api/categories` | Create category |
| PUT | `/api/categories/{id}` | Update category |
| DELETE | `/api/categories/{id}` | Delete category |

### Example: Create an Expense
```bash
curl -X POST http://localhost:8080/api/expenses \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Lunch at office",
    "amount": 150.00,
    "date": "2025-06-15",
    "categoryId": 1,
    "paymentMethod": "UPI",
    "note": "Biryani"
  }'
```

---

## 🛠️ Switch to MySQL (Production)

1. In `application.properties`, comment the H2 section and uncomment MySQL:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/expensedb
spring.datasource.username=root
spring.datasource.password=yourpassword
spring.jpa.database-platform=org.hibernate.dialect.MySQL8Dialect
spring.jpa.hibernate.ddl-auto=update
```

2. Add MySQL dependency in `pom.xml` (already included, just uncomment).

3. Create the database:
```sql
CREATE DATABASE expensedb;
```

---

## ✨ Features

- **Dashboard** — monthly stats, trend chart, category breakdown, recent expenses
- **Expenses** — full CRUD, search, filter by category/date range, CSV export
- **Reports** — bar chart, doughnut chart, horizontal bar, budget vs actual
- **Categories** — create/edit/delete with custom colors & icons
- **Budget tracking** — set monthly limits per category with visual progress bars
- **20 sample expenses** auto-loaded on first run
- **H2 console** at http://localhost:8080/h2-console (dev only)

---

## 🗄️ H2 Database Console

While running in dev mode, access the H2 web console:

- URL: http://localhost:8080/h2-console
- JDBC URL: `jdbc:h2:mem:expensedb`
- Username: `sa`
- Password: *(leave blank)*

---

## 📦 Tech Stack

| Layer | Technology |
|-------|-----------|
| Backend framework | Spring Boot 3.2 |
| ORM | Spring Data JPA / Hibernate |
| Database (dev) | H2 In-Memory |
| Database (prod) | MySQL 8 |
| Validation | Jakarta Validation |
| Frontend | Vanilla HTML + CSS + JS |
| Charts | Chart.js 4 |
| Fonts | Google Fonts (DM Sans + DM Serif Display) |
