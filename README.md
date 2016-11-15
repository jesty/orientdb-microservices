# orientdb-microservices

>>> THIS PROJECT IS AN HUGE WORK IN PROGRESS AND IT IS NO PRODUCTION READY. IF YOU WANT USE IN PRODUCTION, PLEASE CONTACT ME. FEEL FREE TO USE THE SINGOLAR COMPONENTS ASE YOU NEED <<<

This Maven archetype aims to create in a short time a project where you can develop production-ready RESTful web services in Java. The generated project solves the little annoying issues to configure always the same things like a database connection, a webserver, a server api and so on. 

I choose to use OrientDB as database because is an interesting multi-model database written in Java with a lot of interesting features like graph database, document database, geo-spatial support, live query and a native java api. The database starts as embedded in the application so, you must deploy only one component.

In this project I already configured:
* OrientDB: as embedded database, but you can also connect to a remote instance. More information on https://github.com/jesty/orientdb-javaee
* Reasteasy: as JAX-RS implementation
* Undertow: as webserver
* Swagger: as API documentation generator


##Configuration
Before all you must download https://github.com/jesty/orientdb-javaee and install in your local Maven Repository:

```
git clone git@github.com:jesty/orientdb-javaee.git
cd orientdb-javaee
mvn install
cd ..
```
After that you must download this project and install in your local repository

```
git clone git@github.com:jesty/orientdb-microservices.git
cd orientdb-microservices
mvn install
cd ..
```

Now you are ready to use the archetype and generate your project:

```
mvn archetype:generate -DarchetypeGroupId=com.nutcore -DarchetypeArtifactId=orientdb-microservices-archetype -DarchetypeVersion=1.0-SNAPSHOT -DgroupId=com.company -DartifactId=project -Dversion=1.0-SNAPSHOT -Dpackage=com.company.project -DinteractiveMode=false -DarchetypeCatalog=local
```

Wait for a little and when the archetype generation ends, starts your generated project:

```
cd project
mvn package exec:java
```

When the webserver starts visit <http://localhost:8080/api/hello/davide> to create a simpe entry on database and <http://localhost:8080/api/hello> to view created entities. The application documentation is on <http://localhost:8080/api/swagger.json>

Now you are ready to snoop around the code and build your real project!

# Future developments

I like to be complaiant to Principles of microservices (<http://samnewman.io/talks/principles-of-microservices/>) to do that I'm working on this tasks. The tasks with check are work in progress or in test.

- [ ] Deployment how-to
- [x] Metrics (<http://metrics.dropwizard.io>) integration 
- [x] Correlation ID
- [x] Exception tracking and management
- [ ] Conqueur the world
- [ ] Openshift integration
- [ ] Zero configuration clustering
- [ ] Migrate all utility classes from the archetype to an indipendent project
