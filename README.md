# graphql-playground
A simple demo of a GraphQL-based vehicle service backed by an in-memory DB2 database. Some of the initial code was inspired by [this post](https://dzone.com/articles/a-beginners-guide-to-graphql-with-spring-boot)

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
mutation {
  updateVehicle(id: 3, type: "cybertruck", modelCode: "T1", brandName: "Tesla", launchDate: "2019-11-21") 
  {
    id
    type
  }
}
```
Response
```json
{
    "data": {
        "updateVehicle": {
            "id": "3",
            "type": "cybertruck"
        }
    }
}
```

#### Delete a Vehicle
Most examples I've found include _selection sets_ when making requests. In my case, I don't necessarily need a response,
especially since my JPA repository returns `void` on delete. While GraphQL does not support omitting a response entirely 
in a mutation, returning a scalar type is supported. My solution was to return a `Boolean` type. With scalar return 
types, you can omit the _selection set_ entirely which is demonstrated in the example below.

Request
```graphql
mutation {
  deleteVehicle(id: 1)
}
```
Response
```json
{
    "data": {
        "deleteVehicle": true
    }
}
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
* Create sample integration test
  * can we use libs that we're already pulling in to build client request?
  * see what graphql-spring-boot-starter-test offers

