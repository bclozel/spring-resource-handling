Spring Resource Handling
========================

This application demonstrates new resource handling features in Spring Framework 4.1.

The easiest way to get started - from the project root:

    SPRING_PROFILES_ACTIVE=development PROJECT_ROOT=`pwd` ./gradlew :server:bootRun 

Then go to: http://localhost:8080/

Interesting parts of the application:
* [configuring resource handlers with resource resolvers and resource transformers](https://github.com/bclozel/spring-resource-handling/blob/master/server/src/main/java/org/springframework/samples/resources/WebConfig.java#L85-L124)
* [a sample template file using handlebars.java](https://github.com/bclozel/spring-resource-handling/blob/master/server/src/main/resources/handlebars/index.hbs)
and a [custom handlebars helper](https://github.com/bclozel/spring-resource-handling/blob/master/server/src/main/java/org/springframework/samples/resources/handlebars/ResourceUrlHelper.java) to resolve URLs to static resources

Work in progress:
* [ ] Fix the Groovy Markup Template example
* [ ] CDN
* [ ] HTML5 AppCache MANIFEST
* [ ] image optimization
* [ ] CSS sprites
* [ ] LESS / SASS