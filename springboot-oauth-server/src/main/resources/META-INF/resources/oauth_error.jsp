<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>Oauth</title>
    <link type="text/css" rel="stylesheet"
          href="webjars/bootstrap/css/bootstrap.min.css"/>
    <script type="text/javascript" src="webjars/jquery/jquery.min.js"></script>
    <script type="text/javascript"
            src="webjars/bootstrap/js/bootstrap.min.js"></script>
</head>

<body>

<div class="container">

    <h1> OAuth2 Error</h1>

    <p>
        <c:out value="${message}"/>
        (
        <c:out value="${error.summary}"/>
        )
    </p>
    <p>返回客户端重试</p>
</div>

</body>
</html>