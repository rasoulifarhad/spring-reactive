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
