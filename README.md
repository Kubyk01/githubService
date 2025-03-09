GitHub Repository Info Service

This project provides a RESTful service that retrieves information about a user's GitHub repositories. For each repository, it returns the repository name, owner login, and a list of branches with the respective commit SHA.
Requirements: Java 21 or newer is required to build and run this application.

Getting Started
1. Build the Project

Ensure you have Java 21 installed and set up. To verify if Java 21 is installed, you can run:

``java -version``

2. Running the Application

You have two ways to run the application: Development Mode and Production Mode.
Development Mode

For development purposes, you can run the application in development mode. This will allow you to make changes to your code and see them reflected immediately without needing to restart the application. To start the app in development mode, run:

``./mvnw quarkus:dev``

This will start the application in development mode, and the server will be available at http://localhost:8080. You can use the localhost:8080/github/{login}/repos endpoint to retrieve repository information.
Production Mode

For production, you can build and run the application using the following commands:

``./mvnw clean package``
``./mvnw``

This will run the application in normal mode (without hot reloading).
 
3. Accessing the API

Once the application is running, you can access the endpoint to retrieve GitHub repositories.
Endpoint

``GET http://localhost:8080/github/{login}/repos``

Replace {login} with the GitHub username you want to query.
This endpoint will return a Uni array containing the repository name, owner login, and branches with their respective last commit SHA.