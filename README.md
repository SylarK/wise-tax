# Wise-tax

- The wise-tax application is an answer to Altice Labs challenge.

### Context of the challenge
- Create a web solution to order management and reports taking into account the provided tariffs.

### Flow
1. Check eligibility based on the criteria for each tariff.
2. Calculate total cost based on price per unit and apply discounts
   permanent.
3. Determine which bucket (A, B or C) the amount will be debited to.
4. Record the transaction in the CDR with the perMent details.

### Implementation
- This system is designed to efficiently manage client data, tariffs, and billing records. I've applied Object-Oriented Programming (OOP) principles to create a robust, maintainable, and extensible software solution.
- To understand better what was done we need to define some stuffs:
  - Tariffs: Tariffs are categorized as "Alpha" and "Beta," and they define how billing is calculated for different services. We've defined tariff-related methods in the Tariff class, making it easy to apply the correct tariff to a client's usage.
  - Client Data Records: One of the hearts of this application is the ClientDataRecord class, which represents records of client data, including details about when and how the service was used.
  - The ServiceProcessor interface and the implementations are tightly related to the concept of tariffs. Each implementation is responsible for handling requests associated with a particular tariff type. By enforcing tariff-specific restrictions, the service processors ensure that clients are billed correctly based on their selected tariff.

### How to run

**Step 1: Clone the Repository**
```bash
git clone https://your-github-repo-url.git
cd wisetax-billing-system
```

**Step 2: Build the Spring Boot Application**
```bash
./mvnw clean package
```

**Step 3: Run the MongoDB Container using Docker Compose**
```bash
docker-compose up -d
```

**Step 4: Run the Spring Boot Application**
You can run the Spring Boot application using the following command:

```bash
java -jar target/wisetax-billing-system.jar
```

or you can also opt to use the IDE to run the application (for example, Intellij).


### Application Usage:
- **REQUEST**
  - Base URL: http://localhost/api/v1/request
  - HTTP Method: POST
  - Content-Type: application/json

```json
{
  "service": "A",
  "roaming": true,
  "phoneNumber": "+123456789",
  "rsu": 10
}
```

#### Restrictions
Request Body
The request body should contain a JSON object with the following parameters:

- **Service (string, required)**:
    - Description: Specifies the type of service (should be 'A' or 'B').
    - Validation:
      - Must not be empty.
      - Must be either 'A' or 'B'.
- **Roaming (boolean, optional)**:
  - Description: Indicates whether the user is roaming.
  - Validation: None
- **PhoneNumber (string, optional)**:
  - Description: User's phone number.
  - Validation:
    - Must be a valid phone number in the format of a positive integer.
    - The phone number must have a '+' sign followed by digits. 
- **Rsu (integer, required)**:
  - Description: The Request Service Unit (RSU) value.
  - Validation:
    - Must be a positive integer.

---

- **REPORT**
    - Base URL: http://localhost/api/v1/report
    - HTTP Method: GET

```http request
GET http://localhost/api/v1/report?phoneNumber=+123456789&order=DESC
```

#### Restrictions
Request Body
The API accepts the following query parameters:

- **PhoneNumber (string, required):**:
    - Description: The phone number for which client data records are to be retrieved.
    - Validation: None
- **order (string, optional, default: "DESC")**:
    - Description: Specifies the order of the returned records (ascending or descending) based on createdAt timestamp.
    - Values: "ASC" for ascending order, "DESC" for descending order.
    - Validation: If provided must be either "ASC" or "DESC." If not provided, the default is "DESC."
