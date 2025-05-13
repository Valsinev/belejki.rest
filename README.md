# belejki.rest

**belejki.rest** is a Spring Boot RESTful API that allows users to manage reminders, recipes, wishlists, shopping lists, and friendships. Each user can create and manage their personal data while optionally sharing wishlists with friends.


## ✨ Features

- **User Accounts** – Register and manage users.
- **Reminders** – Set reminders with expiration dates.
- **Recipes** – Store and retrieve cooking recipes.
- **Wishes** – Create wishlists with price estimates and item links.
- **Shopping List** – Manage personal shopping items.
- **Friendships** – Add friends and access their wishlists.


## 📦 Installation

### Requirements

- Java 17+
- Maven
- MySQL 8+
- IntelliJ IDEA (recommended for development)

### Clone the Repository

bash
git clone https://github.com/Valsinev/belejki.rest.git
cd belejki.rest


### ⚙️ Environment Variables
Before running the application, set the following environment variables:

Variable	                Description

- SERVER_PORT	            - Optional – custom port (defaults to 8080)
- DATABASE_URL	            - JDBC URL for MySQL (e.g., jdbc:mysql://localhost:3306/belejki)
- DATABASE_USER	         - MySQL username
- DATABASE_PASSWORD	      - MySQL password
- SPRING_SECURITY_USER	   - Default username for Spring Security
- SPRING_SECURITY_PASSWORD	- Default password for Spring Security

💡 In IntelliJ, you can define these in Run > Edit Configurations > Environment Variables.


### 🚀 Running the Application
✅ In IntelliJ (Recommended)

   1. Open the project in IntelliJ IDEA.

   2. Set environment variables (see above).

   3. Find the main class: Application.java.

   4. Right-click it and choose Run.

The API will start and be available at: http://localhost:8080/

🧪 From Terminal (Alternative)

./mvnw spring-boot:run

Or package and run the JAR:

./mvnw package
java -jar target/belejki.rest-0.0.1-SNAPSHOT.jar

Make sure environment variables are exported before running:

export DATABASE_URL=jdbc:mysql://localhost:3306/belejki
export DATABASE_USER=root
export DATABASE_PASSWORD=yourpassword
...

## 🛠️ Technologies Used

 - Java 17

 - Spring Boot

 - Spring Data JPA

 - Spring Security

 - MySQL

 - Maven

 - Postman

## 📫 API Documentation

This project uses a Postman collection to document and test the REST API.

### ▶️ How to Use

1. **Download the collection file**:
   [📄 BelejkiApi.postman_collection.json](./docs/BelejkiApi.postman_collection.json)

2. **Import into Postman**:
    - Open Postman.
    - Click **"Import"** > **"File"**, and choose the JSON file above.

3. **Optional: Set up Environment**  
   To simplify testing, set up Postman environment variables (e.g. `base_url`, etc.).

### 🌐 Optional: Publish Online

If you want to share your API with others or teams, you can [publish the collection on Postman Cloud](https://learning.postman.com/docs/publishing-your-api/documenting-your-api/#publishing-documentation).

---

