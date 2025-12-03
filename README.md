Spring API Keys

A simple Spring Boot service for managing API clients and their API keys.
It handles key generation, hashing, rotation, revocation, and verification—without needing a database or external dependencies.

What It Does

Create API clients

Generate API keys (plaintext returned once)

Store keys hashed using SHA-256

Revoke keys individually

Verify a presented API key

All data kept in-memory for simplicity

This is a small, self-contained service—easy to read through, easy to extend.

Running the App
./mvnw spring-boot:run


(or run ApiKeysApplication from IntelliJ)

```

Base URL:

http://localhost:8080/api

Example Usage
Create a client
curl -X POST http://localhost:8080/api/clients \
  -H "Content-Type: application/json" \
  -d '{"name": "internal-service"}'

Generate a key
curl -X POST http://localhost:8080/api/clients/<clientId>/keys


Returns a plaintext key you’ll never see again.

Verify a key
curl -X POST http://localhost:8080/api/keys/verify \
  -H "Content-Type: application/json" \
  -d '{"apiKey":"<your-key>"}'

Revoke a key
curl -X POST http://localhost:8080/api/clients/<clientId>/keys/<keyId>/revoke
