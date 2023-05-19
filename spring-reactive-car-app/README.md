## Getting Started

> See [Running MongoDB as a Docker Container](https://www.mongodb.com/compatibility/docker)

1. ***Pull the MongoDB Docker Image***

```sh
docker pull mongodb/mongodb-community-server
```

2. ***Run the Image as a Container***

```sh
export MONGODB_VERSION=6.0.4-ubi8
docker run --name mongo -d -p 27017:27017 mongodb/mongodb-community-server:$MONGODB_VERSION
```
<!--

If your application is running inside a container itself, you can run MongoDB as part of the same Docker network as your application using --network. With this method, you will connect to MongoDB on mongodb://mongodb:27017 from the other containerized applications in the network.

```sh
docker run --name mongodb -d --network mongodb mongodb/mongodb-community-server:$MONGODB_VERSION
```

You can test this with a second container running mongosh on the same network.

```sh
docker run --name mongosh --network mongodb mongodb/mongodb-community-server:$MONGODB_VERSION mongosh mongodb://mongodb --eval "show dbs"
```

To initialize your MongoDB with a root user, you can use the environment variables MONGO_INITDB_ROOT_USERNAME and MONGO_INITDB_ROOT_PASSWORD. These environment variables will create a user with root permissions with the specified user name and password.

```sh
docker run --name mongodb -d -p 27017:27017 -e MONGO_INITDB_ROOT_USERNAME=user -e MONGO_INITDB_ROOT_PASSWORD=pass mongodb/mongodb-community-server:$MONGODB_VERSION
```

Managing MongoDB from a Container

To manage your MongoDB server or to access, import, and export your data, you can use a second MongoDB container from which you will run the necessary CLI tools.

To open up a Mongo Shell session to your MongoDB Atlas server, use mongosh and specify the cluster URL.

```sh
docker run -it mongodb/mongodb-community-server:$MONGODB_VERSION mongosh "mongodb://username:password@clusterURL/database"
```

If you want to use the mongoexport tool to export an existing collection to a .json file, you can use the command from a separate MongoDB container. You will need to mount a volume to be able to access the resulting JSON file.

```sh
docker run -it -v $(pwd):/tmp mongodb/mongodb-community-server:$MONGODB_VERSION mongoexport --collection=COLLECTION --out=/tmp/COLLECTION.json "mongodb://username:password@clusterURL/database"
```

If you need to import data into a collection, you use the mongoimport tool, also available from the mongodb/mongodb-community-server:$MONGODB_VERSION image. Again, you will need to mount a volume to access a file stored on your local machine from inside the container.

```sh
docker run -it -v $(pwd):/tmp mongodb/mongodb-community-server:$MONGODB_VERSION mongoimport --drop --collection=COLLECTION "mongodb+srv://user:password@clusterURL/database" /tmp/COLLECTION.json
```


-->


3. ***Check that the Container is Running***

```sh
docker container ls
```

4. ***Connect to the MongoDB Deployment with `mongosh`***

```sh
docker exec -it mongo mongosh
```

5. ***Validate Your Deployment***

```sh
db.runCommand(
   {
      hello: 1
   }
)
```

6. ***Stop MongoDb and remove containr***

```sh
docker stop mongodb && docker rm mongodb
```

<!--

Test slices

Test slices are a feature in Spring Boot that allow the client to load the types in a Spring ApplicationContext that are adjacent to the thing under test.

In this case, we’re interested in testing the data access logic in the service. We are not interested in testing the web functionality. We haven’t even written the web functionality yet, for a start! A test slice lets us tell Spring Boot to load nothing by default and then we can bring pieces back in iteratively.

When Spring Boot starts up it runs a slew of auto-configuration classes. Classes that produce objects that Spring in turn manages for us. The objects are provided by default assuming certain conditions are met. These conditions can include all sorts of things, like the presence of certain types on the classpath, properties in Spring’s Environment, and more. When a Spring Boot application starts up, it is the sum of all the auto-configurations and user configuration given to it. It will be, for our application, database connectivity, object-record mapping (ORM), a webserver, and so much more.

We only need the machinery related to MongoDB and our ProfileService, in isolation. We’ll use the @DataMongoTest annotation to tell Spring Boot to autoconfigure all the things that could be implied in our MongoDB logic, while ignoring things like the web server, runtime and web components.

This results in focused, faster test code that has the benefit of being easier to reproduce. The @DataMongoTest annotation is what’s called a test slice in the Spring Boot world. It supports testing a slice of our application’s functionality in isolation. There are numerous other test slices and you can easily create your own, too.

Test slices can also contribute new auto-configuration supporting tests, specifically. The @DataMongoTest does this. It can even run an embedded MongoDB instance using the Flapdoodle library!

See ProfileServiceTest

-->
