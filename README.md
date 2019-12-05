# graphql-playground
A simple demo of a GraphQL-based vehicle service backed by an in-memory DB2 database. The majority of the code was taken 
from [here](https://dzone.com/articles/a-beginners-guide-to-graphql-with-spring-boot)

## Usage
1. Clone this repository
2. Build the service with `./gradlew build`
3. Launch the service in your IDE, or via `java -jar build/libs/graphql-playground-0.0.1-SNAPSHOT.jar`
4. Once the service is running, browse to http://localhost:8080/graphiql where you can test out different queries. For 
more information about GraphiQL look [here](https://github.com/graphql/graphiql)
5. Alternatively, execute requests programmatically via `POST` requests against http://localhost:8080/graphql (no `i`).
Postman works well here as they have built-in GraphQL support (no need to go crazy escaping your JSON requests).

## GraphQL
### Schema
```graphql
type Vehicle {
    id: ID!,
    type: String,
    modelCode: String,
    brandName: String,
    launchDate: String
}
type Query {
    vehicles(count: Int):[Vehicle]
    vehicle(id: ID):Vehicle
}
type Mutation {
    createVehicle(type: String!, modelCode: String!, brandName: String, launchDate: String):Vehicle
}
```

### Example GraphiQL Requests

#### Create a New Vehicle
Request
```graphql
mutation {
  createVehicle(type: "car", modelCode: "XYZ0192", brandName: "XYZ", launchDate: "2016-08-16") 
  {
    id
  }
}
```
Response
```json
{
    "data": {
        "createVehicle": {
            "id": "15"
        }
    }
}
```

#### Retrieve a Subset of Fields for n Vehicles
Request
```graphql
query {
  vehicles(count: 3) 
  {
    id, 
    type, 
    modelCode
  }
}
```
Response
```json
{
    "data": {
        "vehicles": [
            {
                "id": "1",
                "type": "car",
                "modelCode": "XYZ0192"
            },
            {
                "id": "2",
                "type": "car",
                "modelCode": "XYZ0192"
            },
            {
                "id": "3",
                "type": "car",
                "modelCode": "XYZ0192"
            }
        ]
    }
}
```

#### Retrieve a Single Vehicle
Request
```graphql
query {
  vehicle(id: 5) 
  {
    id, 
    type, 
    modelCode,
    brandName,
    launchDate
  }
}
```
Response
```json
{
    "data": {
        "vehicle": {
            "id": "5",
            "type": "car",
            "modelCode": "XYZ0192",
            "brandName": "XYZ",
            "launchDate": "2016-08-16"
        }
    }
}
```

#### TODO: Update a Vehicle
Request
```graphql
```
Response
```json
```

#### TODO: Delete a Vehicle
Request
```graphql
```
Response
```json
```

#### Request an Invalid ID
Request
```graphql
query {
  vehicle(id: 9999) 
  {
    id, 
    type, 
    modelCode,
    brandName,
    launchDate
  }
}
```
Response
>This is a WIP. No exception details are coming back to the caller which is not ideal.
```json
{
    "data": {
        "vehicle": null
    },
    "errors": [
        {
            "message": "Internal Server Error(s) while executing query",
            "path": null,
            "extensions": null
        }
    ]
}
```

## What's Next?
* Improve error handling - GraphQL returns 200 status code with generic error message when any exceptions occur
* Add vehicle update support
* Add vehicle delete support
* Look into supporting LocalDate/LocalDateTime during deserialization rather than parsing explicitly in service
