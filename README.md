Spring Resource Handling
========================

[![Build Status](https://travis-ci.org/bclozel/spring-resource-handling.svg?branch=master)](https://travis-ci.org/bclozel/spring-resource-handling)

This application demonstrates new resource handling features in Spring Framework 4.1.
It was originally developed for the talk [Resource Handling in Spring MVC 4.1](https://2014.event.springone2gx.com/schedule/sessions/resource_handling_in_spring_mvc_4_1.html) talk at SpringOne2GX 2014.


This projects requires a local install of node+npm (see [nvm](https://github.com/creationix/nvm)).

The easiest way to get started - from the project root - development version:

    SPRING_PROFILES_ACTIVE=development ./gradlew :server:bootRun
     
Or the production version (more optimizations):

    SPRING_PROFILES_ACTIVE=production ./gradlew :server:bootRun
    
Then go to:

* http://localhost:8080/ for an example with JMustache templating
* http://localhost:8080/groovy for an example with Groovy Template Engine
* http://localhost:8080/app for an example with an [HTML5 AppCache Manifest](http://www.html5rocks.com/en/tutorials/appcache/beginner/) 
(you can check this in Chrome with chrome://appcache-internals/ )
* http://localhost:8080/less for an example with a LESS stylesheet; this page uses less files and the LESS JS transpiler
in development mode, and a transpiled version in production
* http://localhost:8080/jsp for a JSP example
* http://localhost:8080/velocity for a Velocity example

Interesting parts of the application:

* [configuring resource handlers with resource resolvers and resource transformers](https://github.com/bclozel/spring-resource-handling/blob/master/server/src/main/resources/application-production.java)
* [a sample template file using JMustache](https://github.com/bclozel/spring-resource-handling/blob/master/server/src/main/resources/mustache/index.html)
and a [custom Mustache lambdas](https://github.com/bclozel/spring-resource-handling/blob/master/server/src/main/java/org/springframework/samples/resources/support/MustacheViewResolverCustomizer.java) to resolve URLs to static resources
