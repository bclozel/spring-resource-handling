Spring Resource Handling
========================

This application demonstrates new resource handling features in Spring Framework 4.1.
This projects requires a local install of node+npm (see [nvm](https://github.com/creationix/nvm)).

The easiest way to get started - from the project root - development version:

    SPRING_PROFILES_ACTIVE=development PROJECT_ROOT=`pwd` ./gradlew :server:bootRun
     
Or the production version (more optimizations):

    ./gradlew :server:bootRun
    
Then go to: http://localhost:8080/

Interesting parts of the application:
* [configuring resource handlers with resource resolvers and resource transformers](https://github.com/bclozel/spring-resource-handling/blob/master/server/src/main/java/org/springframework/samples/resources/WebConfig.java#L85-L124)
* [a sample template file using handlebars.java](https://github.com/bclozel/spring-resource-handling/blob/master/server/src/main/resources/handlebars/index.hbs)
and a [custom handlebars helper](https://github.com/bclozel/spring-resource-handling/blob/master/server/src/main/java/org/springframework/samples/resources/handlebars/ResourceUrlHelper.java) to resolve URLs to static resources

Work in progress:
* [ ] CDN
* [ ] HTML5 AppCache MANIFEST
* [ ] image optimization
* [ ] CSS sprites
* [ ] LESS / SASS