# SimpleService
## Description
This is a simple service for no purpose

We have a REST endpoing base on springboot and a mongodb as data layer. 

## Building
```
./gradlew build
```

## Some Considerations
- Service is base on springboot for lots of out of the box features, another good selection would be using Go, which I would loved to learn.
- Data layer is base on mongodb, since it is a simple service, we don't really need a relational database.
- We are using gradle as build tool, since it is the most popular one in the market. 
- The reason why I am using regular k8s rather than helm is because I don't have too much exposure to it. It's in my learning list. 



## Docker
Run the following command to build up the docker image and get it up and running on local
```
docker-compose up -d --build 
```

## K8s 
Since we don't really have docker registry setup, assuming that this is running on local. 

Run this to build out the docker image
```
docker build --no-cache -t simpleservice:latest .
```

Run `docker image` to check with the newly create image, update the `k8s/dev/deployment.yaml` with the image name. 


Then run this to bring up k8s
```
kubectl apply -f k8s/dev/deployment.yaml
```

Since we need a mongodb to run as well. 

```
kubectl apply -f k8s/dev/mongo.yaml
```

## MongoDB
It is configured as no authentication and default everything.

## TODO List
- [ ] A better configuration management. 
- [ ] Optimize building we don't clean up artifacts.
- [ ] Use Helm, and actually deploy this to an k8s engine
- [ ] Swagger UI Setup FIx
- [ ] Dockerize
- [ ] Add more tests
- [ ] Metrics logging (Prometheus)
- [ ] nginx
- [ ] CI/CD
- [ ] Authentication on data layer
- [ ] Artifactory registration

