Based on the code and configuration files provided, here is a comprehensive README.md file for the Career Networking Platform.

Career Networking Platform
A robust, microservices-based social networking application designed to facilitate professional connections, similar to LinkedIn. This project utilizes a modern event-driven architecture with Spring Boot, Kafka, Neo4j, and Kubernetes.

Architecture Overview
The application is split into domain-specific microservices, ensuring scalability and separation of concerns:

API Gateway: The entry point for all client requests. It handles routing and implements centralized JWT Authentication.

Discovery Server: A Netflix Eureka server for service registry and discovery.

User Service: Manages user registration (Sign-up) and authentication (Login). It handles JWT token generation.

Connections Service: Powered by Neo4j (Graph Database). It manages the social graph, handling connection requests and retrieving 1st, 2nd, and 3rd-degree connections.

Posts Service: Manages content creation. Allows users to create posts (with images) and like posts.

Notification Service: An event-driven service that listens to Kafka topics to generate notifications when posts are created or liked.

Uploader Service: Handles file uploads to external cloud providers (Cloudinary / Google Cloud Storage).
Tech Stack
Language: Java 17 / 23

Framework: Spring Boot 3.x / 4.x

Cloud Architecture: Spring Cloud (Gateway, Eureka, OpenFeign)

Databases:

PostgreSQL: For User, Posts, and Notification services.

Neo4j: For the Connections Service (Graph relationships).

Message Broker: Apache Kafka (Event-driven communication).

Containerization & Orchestration: Docker, Kubernetes (K8s), Jib (Maven plugin for image building).

Storage: Cloudinary, Google Cloud Storage.

Build Tool: Maven.

📂 Project Structure
├── APIGateway            # Spring Cloud Gateway & Security
├── DiscoverServer        # Eureka Service Registry
├── userService           # User Management & Auth
├── ConnectionsService    # Graph DB Logic for network
├── postsService          # Post creation & Interactions
├── NotificationService   # Async Notification Consumer
├── UploaderService       # File Upload Utility
└── k8s                   # Kubernetes Deployment Manifests
⚙️ Key Features
Secure Authentication: JWT-based stateless authentication passed via API Gateway.

Social Graph: Efficient traversal of user connections using Neo4j to find "Friends of Friends".

Event-Driven: Asynchronous communication between services (e.g., User creation triggers graph node creation; Likes trigger notifications) using Kafka.

Media Support: Image uploading integration for posts.

Resilience: Service discovery and load balancing via Eureka and Feign Clients.

🔧 Setup & Installation
Prerequisites
Java JDK 17+

Maven

Docker & Kubernetes (e.g., Minikube or Docker Desktop)

PostgreSQL

Neo4j

Apache Kafka

1. Configuration
Ensure you have the following environment variables set, or update the application.yml / k8s files accordingly:

Databases: Credentials for PostgreSQL (user-db, posts-db, notification-db) and Neo4j (connections-db).

Kafka: Bootstrap server address.

Cloudinary/GCloud: API keys for the Uploader Service.

JWT: jwt.secretKey in the API Gateway and User Service.

2. Build Services
You can build each service using the Maven wrapper:

Bash

cd userService
./mvnw clean install
# Repeat for other services
3. Running with Kubernetes
The project includes a k8s folder with manifest files.

Build Docker Images: The projects use the Jib maven plugin to build images directly.

Bash

./mvnw jib:dockerBuild
Deploy to K8s:

Bash

kubectl apply -f k8s/
Access the App: The Ingress is configured to route traffic to the API Gateway.

Entry point: http://localhost:80 (or your Ingress IP).

🔌 API Endpoints
Authentication (/auth)
POST /auth/signup: Create a new account.

POST /auth/login: Login and receive JWT.

Connections (/core)
GET /core/{userId}/first-degree: Get direct connections.

GET /core/{userId}/second-degree: Get friends of friends.

POST /core/sender/{userId}: Send connection request.

POST /core/accept/{userId}: Accept connection request.

Posts (/core)
POST /core: Create a post (Multipart file + JSON).

GET /core/{postId}: Get a specific post.

GET /core/users/{userId}/allPosts: Get all posts by a user.

POST /likes/{postId}: Like a post.

Uploads (/file)
POST /file: Upload a file/image.

Event Workflow Example
User Signup:

userService saves User to Postgres.

Produces UserCreatedEvent to Kafka user_created_topic.

ConnectionsService consumes event and creates a Person node in Neo4j.

Creating a Post:

postsService uploads image via UploaderService (Feign Client).

Saves Post to Postgres.

Fetches connections via ConnectionsService (Feign Client).

Produces PostCreated event to Kafka.

NotificationService consumes event and generates notifications for friends.
