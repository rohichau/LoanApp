### Loan Management System
The Loan Management System is an application that allows users to manage loans and repayments. It provides several controllers to handle loan-related operations, including creating loans, approving loans, retrieving customer loans, and adding repayments.

### Controllers
## LoanController
The LoanController is responsible for handling loan-related operations. It provides the following endpoints:

POST /api/loans: Creates a new loan based on the provided loan request.
PUT /api/admin/loans/{loanId}/approve: Approves the loan with the specified ID.

## CustomerLoanController
The CustomerLoanController is responsible for managing customer loans. It provides the following endpoint:

GET /api/customers/{customerId}/loans: Retrieves the loans associated with a specific customer.

## RepaymentController
The RepaymentController handles repayment-related operations. It provides the following endpoint:

POST /api/customers/{customerId}/loans/{loanId}/repayments: Adds a repayment for a specific loan.
Project Overview
The Loan Management System is built using Java and Spring Boot. It utilizes a relational database to store loan and repayment data. The system follows a RESTful API architecture and includes Swagger documentation for easy reference.

To run the project, follow these steps:

Ensure you have Java and Maven installed on your system.
Clone the repository and navigate to the project directory.
Set up the database connection by configuring the database URL, username, and password in the application.properties file.
Build the project using the command mvn clean install.
Run the application using the command mvn spring-boot:run.
Once the application is up and running, you can access the different endpoints mentioned in the controllers section to perform loan and repayment operations.

Swagger Documentation
The Loan Management System includes Swagger documentation for easy API reference. To access the Swagger UI, follow these steps:

Run the application.
Open a web browser and navigate to http://localhost:8080/swagger-ui.html.
The Swagger UI page will display the available endpoints along with detailed information about each endpoint's request and response structures.
Conclusion
The Loan Management System provides a convenient way to manage loans and repayments through a set of intuitive RESTful APIs. By leveraging the provided controllers and endpoints, users can easily create loans, approve loans, retrieve customer loans, and add repayments. The project is built using Java and Spring Boot, and includes Swagger documentation for easy API exploration and testing.

Feel free to explore the codebase and make any necessary modifications or additions to suit your specific requirements.