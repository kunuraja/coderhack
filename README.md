CodeerHack is a Spring Boot app for managing the leaderboard based on users' scores. Users are rewarded with badges based on their scores.

Requirements:

For building and running the application you need:

JDK 21
Gradle

Running the application locally:

You can run the spring-boot application locally by using the gradle command:

./gradlew bootrun

Endpoints for accessing the application:

GET /users - For getting the list of registered users

GET /users/{userId} - For fetching the user with userId passed in path variable

POST /users - For registering a user

PUT /users/{userId} - For updating a user
DELETE /users/{userId} - For removing one user
