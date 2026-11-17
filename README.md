# JWT Validation and Claims Extraction

This Spring Boot project implements the lab task shown in the provided image.

## Features

1. Parses an incoming JWT using a secret key.
2. Verifies the JWT signature.
3. Checks token expiration (handled by the JWT parser).
4. Extracts `username` and `roles` claims.
5. Exposes `POST /api/jwt/validate`.
6. Includes Swagger UI and automated tests for valid and invalid tokens.

## Requirements

- JDK 17+
- Maven 3.6+ or STS / Spring Tool Suite with Maven support

## Import into STS

1. Extract this ZIP file.
2. Open STS.
3. Select **File -> Import -> Maven -> Existing Maven Projects**.
4. Browse to the extracted `JWT_Validation_Claims_Extraction` folder.
5. Select `pom.xml` and click **Finish**.
6. Wait for Maven dependencies to download.

## Run the tests

Right-click the project -> **Run As -> Maven test**.

Or run in a terminal:

```bash
mvn test
```

The tests create a signed, short-lived JWT and check both valid and invalid token cases.

## Run the application

Run `JwtValidationApplication.java` as **Spring Boot App**, or use:

```bash
mvn spring-boot:run
```

Open Swagger UI:

```text
http://localhost:8080/swagger-ui/index.html
```

## Test the endpoint in Swagger

Select `POST /api/jwt/validate` -> **Try it out**.

Request body format:

```json
{
  "token": "PASTE_A_SIGNED_JWT_HERE"
}
```

A valid token returns HTTP 200 and extracted claims. An invalid, altered, or expired token returns HTTP 401 with `Invalid Token`.

### Important: generating a valid token for manual Swagger testing

The project includes a demo HMAC secret in `JwtConfig.java`. The automated test generates a correctly signed token using that key. For manual Swagger testing, create a JWT with:

- Algorithm: HS256
- Payload:
  ```json
  {
    "username": "madhuri",
    "roles": ["USER", "ADMIN"],
    "iat": 1794940000,
    "exp": 1894940000
  }
  ```
- Secret (Base64-decoded text): `my-super-secure-demo-key-for-jwt-validation-256-bits`

Use the exact secret text above in a local JWT tool configured for HS256. Do not use this demo secret in a real application.

## Expected successful response

```json
{
  "valid": true,
  "message": "Token Valid",
  "username": "madhuri",
  "roles": ["USER", "ADMIN"]
}
```

## Expected invalid-token response

HTTP status: `401 Unauthorized`

```json
{
  "valid": false,
  "message": "Invalid Token",
  "username": null,
  "roles": []
}
```

## Files

- `JwtConfig.java` - provides the HMAC signing key.
- `JwtValidator.java` - verifies signature and expiration, then extracts claims.
- `JwtValidationController.java` - implements the POST validation endpoint.
- DTO classes - request and response objects.
- `JwtValidationControllerTest.java` - tests valid and invalid JWTs.

## Security note

The included secret is only for this classroom demonstration. In real projects, keep secrets outside source code (for example, environment variables or a secret manager), use HTTPS, and never log raw JWTs.
"# 2400032196-SOAEndSemSkill" 
