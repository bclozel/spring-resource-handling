<!doctype html>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>Spring resource handling</title>
    <meta name="description" content="">
    <meta name="viewport" content="width=device-width">

    <link rel="stylesheet" type="text/css" href="<spring:url value="/css/main.css"/>">
    <script src="<spring:url value="/lib/es6-module-loader/dist/es6-module-loader.js"/>"></script>
    <script src="<spring:url value="/lib/systemjs/dist/system.js"/>"></script>
    <script>
        System.map['jquery'] = '/lib/jquery/dist/jquery.min';
        System.import('app/app');
    </script>

</head>
<body>
    <c:url value="/css/main.css" var="jstlUrl"/>
    <spring:url value="/css/main.css" htmlEscape="true" var="springUrl" />
    <div class="container">
            <div class="jumbotron">
            <h1 id="greeting">{insert greeting here}</h1>
    </div>
    <h2>Spring URL: ${springUrl}</h2>
    <h2>JSTL URL: ${jstlUrl}</h2>
    <div id="logo"></div>
    </div>
</body>
</html>