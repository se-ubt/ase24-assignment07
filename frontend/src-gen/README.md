# TaskBoard API client

This directory contains the TaskBoard OpenAPI specification ([`./taskboard-api-docs.json`](./taskboard-api-docs.json)) and the generated TypeScript DTOs for the API.

## Update API client code

If the TaskBoard API changes, update the DTOs as follows:

```shell
curl http://localhost:8080/api/api-docs > taskboard-api-docs.json
cd ..
mvn swagger-codegen:generate # regenerates the DTOs based on the new specification
```

Then commit the changes.
