## Functional Requirements

**Employee**: User responsible for creating and managing the store, stock, cash flow, adding products, etc., as well as getting, separating, and sending orders with products.

**Customer**: User responsible for buying products from the store. They need to register to look at items in the store.

**Products**: Items shown to customers for purchase.

**Stock**: Where products are managed and counted. When products are bought, the quantity is decreased from the stock.

**Order**: Contains all information about cart items after purchase.

### To-Do Tasks

- **Deployment**
    - [x]  Prepare docker environment
    - [ ]  Prepare CI/CD with docker
- **Logs**
    - [ ]  Add Logs in system
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
    - [x]  Obs: Add integrations tests
- **Product Images**
    - [x]  Employee can create a picture for a product
    - [x]  Employee can delete a picture
    - [x]  Employee can list a picture
    - [x]  Obs: Add integration tests
- **Stock Product (Future Integration)**
    - [x]  Employee can create product data in stock
    - [x]  Employee can increase product quantity in stock
    - [x]  Employee can decrease product quantity in stock
        - [x]  When decreasing, the system should allow only positive values in the stock item
    - [x]  Employee can get all product quantities by page in stock
    - [x]  Employee can get one product quantity in stock
    - [x]  Obs: Add integration tests
- **Employee (User with Role EMPLOYEE)**
    - [ ]  Employee can delete themselves
    - [x]  Employee can create other employees
    - [ ]  System can add the role EMPLOYEE
    - [ ]  Employee can get their own data
    - [ ]  Employee can update their own data
    - [ ]  Obs: Add integration tests
- **Roles (User with Role)**
    - [x]  System can list two roles **CUSTOMER and EMPLOYEE**
- **Customer (User with Role CUSTOMER)**
    - [ ]  Customer can delete themselves
    - [ ]  System can add the role **CUSTOMER**
    - [ ]  Customer can get their own data
    - [ ]  Customer can update their own data
    - [ ]  Customer can generate an order after payment
    - [ ]  Obs: Add integration tests
- **Order**
    - [x]  Costumer can create an order with state OPEN
    - [x]  Order can just have one product for item list;
    - [x]  Can't have two of the same product in the same order
    - [x]  The order has 2 states. **OPEN**: means the customer should be able to update or add more items to the order created. **BEGIN PICKED**: means the customer shouldn’t be able to update or add more items and can only view their order.
    - [ ]  Order can show total value.
    - [x]  When order state to be **BEGIN PICKED**. All product into order are decrease of **STOCK**.
    - [x]  Order must have only costumer and don’t employee and get your items
    - [x]  Customer can delete their order and delete your items
    - [x]  Customer can get their order data and get your items
    - [x]  Obs: Add integrations tests
- **Order Items**
    - [x]  Customer can have order items
    - [x]  When state order **OPEN**
        - [x]  Costumer can add Order item
            - [x]  System validate if quantity of item before insert
        - [x]  Costumer can remove Order item
        - [x]  Costumer can update Order item
            - [x]  System check if quantity of item before update
        - [x]  Costumer can get one Order item
    - [ ]  Obs: Add integrations tests
- **Authentication**
    - [x]  All users can log in
    - [x]  Customers can register
    - [ ]  Obs: Add integration tests