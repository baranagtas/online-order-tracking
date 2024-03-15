# Online Order Tracking System

## Project Description

This project aims to develop an Online Order Tracking System using the Spring Boot Framework in the Java programming language. The system will allow customers to order various products and track their orders. The application will utilize REST API architecture and will be developed as a Maven project. The source code will be hosted on GitHub.

## Project Scope

The scope of the project includes:

- Implementing user authentication for customers to sign up and log in.
- Configuring Basic Auth tokens based on user login credentials to secure endpoints.
- Providing endpoints for filtering and searching products based on attributes such as category and price.
- Processing new orders, including order confirmation, stock control, and updating product stock quantities in the database.
- Allowing customers to track the statuses of their orders and view order confirmation information.
- Enabling customers to cancel their orders, with corresponding adjustments to stock quantities.

## Requirements

1. **User Authentication**: Customers should be able to sign up and log in.
2. **Token-based Security**: Endpoints should only be accessible using Basic Auth tokens generated from user login credentials.
3. **Product Filtering and Searching**: Allow filtering and searching of products based on attributes such as category and price.
4. **Order Processing**: New orders should undergo processes such as order confirmation and stock control, updating stock quantities in the database.
5. **Order Tracking**: Customers should be able to track the statuses of their orders and view order confirmation information.
6. **Order Cancellation**: Customers should be able to cancel their orders, with corresponding adjustments to stock quantities.

## Evaluation Criteria

The project will be evaluated based on the following criteria:

1. Maven Project Structure
2. Dockerized Tools Installation
3. Postman Collection or Swagger API Documentation
4. Unit Tests with JUnit
5. Adherence to Object-Oriented Programming Principles
6. Code Quality and Readability (naming conventions, comments, etc.)

## Usage

To run the project, follow these steps:

1. Clone the repository from GitHub.
2. Install Docker and Docker Compose.
3. Navigate to the project directory.
4. Run `docker-compose up` to start the application and required services.
5. Access the application through the provided API endpoints.
6. Use Postman or Swagger for API documentation and testing.


## License

This project is licensed under the [MIT License](LICENSE).
