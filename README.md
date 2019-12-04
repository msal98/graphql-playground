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

#### Create a new vehicle record
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

#### Retrieve a Single Vehicle In It's Entirety
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