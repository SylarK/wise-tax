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

