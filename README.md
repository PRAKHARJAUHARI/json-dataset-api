📘 JSON Dataset API – Full Documentation

A Spring Boot REST API to store, sort, and group dynamic JSON records using PostgreSQL's JSONB feature.

✅ Features
- Insert flexible JSON records into named datasets
- Sort records by any key (supports numeric and string sort)
- Group records by any JSON key
- Global exception handling for edge cases
- Unit + integration tests
- Clean architecture with custom repository and service layer

🚀 How to Run the Project Locally

✅ Requirements
- Java 21+
- PostgreSQL
- Maven 3.8+
- IDE (e.g. IntelliJ, VSCode)

🛠 Setup Steps
1. Clone the repository
   git clone https://github.com/your-username/json-dataset-api.git
   cd json-dataset-api

2. Create the PostgreSQL database
   CREATE DATABASE json_dataset;

3. Update DB credentials in application.properties:
   spring.datasource.url=jdbc:postgresql://localhost:5432/json_dataset
   spring.datasource.username=your_db_user
   spring.datasource.password=your_db_password
   spring.jpa.hibernate.ddl-auto=update

4. Run the application
   mvn clean install
   mvn spring-boot:run

🔗 API Reference

✅ Insert Record
POST /api/dataset/{datasetName}/records

Request Body:
{
  "name": "Alice",
  "age": 30,
  "department": "Engineering"
}

Response:
{
  "message": "Record added successfully",
  "dataset": "employees",
  "recordId": 1
}

🔢 Sort Records
GET /api/dataset/{datasetName}/records/sorted

Query Parameters:
- sortKey: key to sort (required)
- order: asc or desc (optional)
- limit: number of records (optional)
- offset: start index (optional)
- isNumeric: true/false (optional)

Example:
GET /api/dataset/employees/records/sorted?sortKey=age&order=desc&limit=5&isNumeric=true

🧩 Group Records
GET /api/dataset/{datasetName}/records/grouped?groupKey=department

Response:
[
  {
    "group_value": "Engineering",
    "records": [
      {"name": "Alice", "age": 30, "department": "Engineering"},
      {"name": "Bob", "age": 25, "department": "Engineering"}
    ]
  }
]

🛡️ Global Exception Handling

Handled by GlobalExceptionHandler.java.

| Scenario                        | Response Code | Message Example              |
|--------------------------------|---------------|------------------------------|
| Blank sortKey/groupKey         | 400           | "Key must not be blank"     |
| Invalid limit/offset           | 400           | "Limit must be greater than 0" |
| Dataset not found              | 204           | No Content                   |
| SQL error / invalid key        | 500           | Internal Server Error        |

🧪 Testing the Application

To run all tests:
mvn test

Tests include:
- DatasetServiceTest (unit tests with Mockito)
- DatasetControllerIntegrationTest (integration tests using actual DB)

📁 Project Structure

src
├── controller/
│   └── DatasetController.java
├── service/
│   └── DatasetService.java
├── repository/
│   ├── DatasetRecordRepository.java
│   └── impl/
│       └── DatasetRecordRepositoryImpl.java
├── model/
│   └── DatasetRecord.java
├── exception/
│   └── GlobalExceptionHandler.java
├── JsonDatasetApiApplication.java

⚙️ Tech Stack

- Java 21
- Spring Boot 3.2.5
- PostgreSQL (with JSONB)
- Spring Data JPA
- JUnit 5 + Mockito
- Maven

📫 Contact

Created by Prakhar Jauharijauhari.prakhar2001@gmail.com
GitHub:https://github.com/PRAKHARJAUHARI

📝 License

This project is licensed under the MIT License.
