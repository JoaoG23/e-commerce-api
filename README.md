

# Application E-Commerce API 🏪👜

API small e-commerce 🤰🏽in construction....
electronic commerce, is a system of buying and selling products or services over the internet. It is a business model that has become very popular due to its convenience and efficiency.
## Technologies Used 🪄

  [![Insomnia](https://img.shields.io/badge/Insomnia-5849BE?style=for-the-badge&logo=insomnia&logoColor=white)](https://insomnia.rest/)
  [![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)](https://www.java.com/)
  [![Java Spring Web](https://img.shields.io/badge/Java%20Spring%20Web-6DB33F?style=for-the-badge&logo=spring&logoColor=white)](https://spring.io/projects/spring-boot)
  [![Lombok](https://img.shields.io/badge/Lombok-BC4521?style=for-the-badge&logo=lombok&logoColor=white)](https://projectlombok.org/)
  [![Spring DevTools](https://img.shields.io/badge/Spring%20DevTools-6DB33F?style=for-the-badge&logo=spring&logoColor=white)](https://docs.spring.io/spring-boot/docs/current/reference/html/using.html#using.devtools)
  [![REST Architecture](https://img.shields.io/badge/REST%20Architecture-blue?style=for-the-badge&logo=rest&logoColor=white)](https://www.redhat.com/en/topics/api/what-is-a-rest-api)
  [![PostgresSQL](https://img.shields.io/badge/PostgresSQL-336791?style=for-the-badge&logo=postgresql&logoColor=white)](https://www.postgresql.org/)
  [![JWT Tokens](https://img.shields.io/badge/JWT%20Tokens-6DB33F?style=for-the-badge&logo=spring&logoColor=white)](https://jwt.io/)
  [![Spring Data JPA](https://img.shields.io/badge/Spring%20Data%20JPA-6DB33F?style=for-the-badge&logo=spring&logoColor=white)](https://spring.io/projects/spring-data-jpa)
  [![Spring Security](https://img.shields.io/badge/Spring%20Security-6DB33F?style=for-the-badge&logo=spring&logoColor=white)](https://spring.io/projects/spring-security)

## Functional Requirements

**Employee**: User responsible for creating and managing the store, stock, cash flow, adding products, etc., as well as getting, separating, and sending orders with products.

**Customer**: User responsible for buying products from the store. They need to register to look at items in the store.

**Products**: Items shown to customers for purchase.

**Stock**: Where products are managed and counted. When products are bought, the quantity is decreased from the stock.

**Order**: Contains all information about cart items after purchase.

### To-Do Tasks

- **Products**
    - [x]  Employee can create a product
        - [x]  When creating, the system automatically creates images
        - [x]  When creating, the system automatically creates product data in stock
    - [x]  Employee can delete a product
        - [x]  When deleting, the system automatically removes product images
        - [x]  When deleting, the system automatically removes product data from stock
    - [x]  All users can list a product
    - [x]  All users can list all products by page
    - [x]  Employee can update a product
- **Product Images**
    - [x]  Employee can create a picture for a product
    - [x]  Employee can delete a picture
    - [x]  Employee can list a picture
- **Stock Product (Future Integration)**
    - [x]  Employee can create product data in stock
    - [x]  Employee can increase product quantity in stock
    - [x]  Employee can decrease product quantity in stock
        - [x]  When decreasing, the system should allow only positive values in the stock item
    - [x]  Employee can get all product quantities by page in stock
    - [x]  Employee can get one product quantity in stock
- **Employee (User with Role EMPLOYEE)**
    - [ ]  Employee can delete themselves
    - [x]  Employee can create other employees
    - [ ]  System can add the role EMPLOYEE
    - [ ]  Employee can get their own data
    - [ ]  Employee can update their own data
- **Roles (User with Role)**
    - [x]  System can list two roles **CUSTOMER and EMPLOYEE**
- **Customer (User with Role CUSTOMER)**
    - [ ]  Customer can delete themselves
    - [ ]  System can add the role **CUSTOMER**
    - [ ]  Customer can get their own data
    - [ ]  Customer can update their own data
    - [ ]  Customer can generate an order after payment
- **Order**
    - [x]  Employee or costumer can create an order for costumer
    - [x]  Order can just have one product for item list;
    - [x]  Can't have two of the same product in the same order
    - [ ]  The order has 2 states. **OPEN**: means the customer should be able to update or add more items to the order created. **BEGIN PICKED**: means the customer shouldn’t be able to update or add more items and can only view their order.
    - [ ]  Order can show total value order.
    - [ ]  When order state to be **BEGIN PICKED**. All product into order are decrease of **STOCK**.
    - [x]  Order must have only costumer and don’t employee and get your items
    - [x]  Customer can delete their order and delete your items
    - [x]  Customer can get their order data and get your items
- **Order Items**
    - [x]  Customer can have order items
    - [x]  When state order **OPEN**
        - [x]  Costumer can add Order item
        - [x]  Costumer can remove Order item
        - [x]  Costumer can update Order item
        - [x]  Costumer can get one Order item
- **Authentication**
    - [ ]  All users can log in
    - [x]  Customers can register

## How to Install 🔑

To configure the E-Commerce-api project, follow the instructions below:

#### Backend (Spring Boot);

`DEVELOPER Environment`

1. Enter the backend folder:
   ```bash
   cd backend
   ```

2. Configure the `application.properties` file for the database settings (located in `src/main/resources`):

    ```properties
    # HIBERNATE
    spring.datasource.url=jdbc:postgresql://localhost:5432/e-commerce
    spring.datasource.username=admin
    spring.datasource.password=admin
    
    # SERVER
    server.error.include-stacktrace=never
    server.port=8081
    server.servlet.contextPath=/api
    
    # TOKENS
    api.security.token.secret=${JWT_Secret:joao}
    ```

3. Build the project with Maven:
   ```bash
   ./mvnw clean install
   ```

4. Start the Spring Boot server:
   ```bash
   ./mvnw spring-boot:run
   ```

`PRODUCTION DOCKER Environment`

1. Create a folder named `db` in this directory.
2. Inside this folder, create two files:
   `db.env` with the following information:

        ```
        POSTGRES_USER=username
        POSTGRES_PASSWORD=password
        ```

   `init.sql` with the following commands:
      -- Replace `username` with the selected application user.

        ```
        CREATE USER e-commerce;
        CREATE DATABASE e-commerce;
        GRANT ALL PRIVILEGES ON DATABASE e-commerce TO username;
        ```

3. In the `application-prod.properties` file, update the information to match your database settings:

    ```
    # JPA
    spring.datasource.url=jdbc:postgresql://e-commerce-container:5432/e-commerce
    spring.datasource.username=username
    spring.datasource.password=password
    
    # SERVER
    server.error.include-stacktrace=never
    server.port=8080
    server.servlet.contextPath=/api
    
    # TOKENS
    api.security.token.secret=${JWT_Secret:joao}
    
    #spring.jpa.hibernate.ddl-auto=update
    #spring.jpa.properties.hibernate.jdbc.lab.non_contextual_creation=true
    #flyway.ignoreMigrationPatterns="repeatable:missing"
    ```

4. Now, just run the command `docker-compose up -d` in the root directory.

1. Ensure that the PostgreSQL database is configured and running.
2. In the terminal, navigate to the project's root directory.
3. Run the following command to start the Spring Boot server:

   ```bash
   ./mvnw spring-boot:run
   ```

4. The Spring Boot server will start and be listening for requests on the defined port.

    ```bash
    http://localhost:8081/api/v1/login
    ```

5. Use the routes and endpoints provided by the server to manage the deployment processes.

<!-- Documentation link: https://doc-E-Commerce-api.netlify.app/#req_57f32835a4da4a64946ef9bff6a1330e -->

1. **Access the Documents Folder**:
    - Navigate to the `/docs` folder on your computer.

2. **Locate the Collection File**:
    - Find the JSON file named `insomnia.json`.

3. **Open Insomnia**:
    - Start Insomnia on your computer.

4. **Import the Collection**:
    - Click on the menu icon in the top left corner (three horizontal lines).
    - Select "Import/Export".
    - Click "Import Data".
    - Choose "From File".
    - Navigate to the `/docs` folder, select the collection file, and click "Open".

5. **Verify Import**:
    - After the import, verify that the collection was added correctly in Insomnia.

Done! The collection should now be available in Insomnia for you to use.

## How to Use 👨🏽‍🏫
**You can:**

* .. In construction



**Benefits:**

* .. In construction


**Example Usage:**

* 

## Author


![avatar](https://images.weserv.nl/?url=https://avatars.githubusercontent.com/u/80895578?v=4?v=4&h=100&w=100&fit=cover&mask=circle&maxage=7d)

<sub><b>João Guilherme</b></sub></h4> <a href="https://github.com/JoaoG23/">🚀</a>

Made with 🤭 by João Guilherme 👋🏽 Get in touch below!

[![Linkedin Badge](https://img.shields.io/badge/-Joao-blue?style=flat-square&logo=Linkedin&logoColor=white&link=https://www.linkedin.com/in/jaoo/)](https://www.linkedin.com/in/joaog123/)
[![Badge](https://img.shields.io/badge/-joaoguilherme94@live.com-c80?style=flat-square&logo=Microsoft&logoColor=white&link=mailto:joaoguilherme94@live.com)](mailto:joaoguilherme94@live.com)

## License

[![Licence](https://img.shields.io/github/license/Ileriayo/markdown-badges?style=for-the-badge)](./LICENSE)