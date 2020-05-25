<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="authz"
           uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>OAuth</title>
    <link type="text/css" rel="stylesheet" href="webjars/bootstrap/css/bootstrap.min.css"/>
    <script type="text/javascript" src="webjars/jquery/jquery.min.js"></script>
    <script type="text/javascript" src="webjars/bootstrap/js/bootstrap.min.js"></script>

</head>
<body>

<div class="container">

    <h1>OAuth</h1>

    <h2>Home</h2>

    <p>This is the simplest OAuth demo .</p>

    <authz:authorize access="hasRole('ROLE_USER')">
        <div class="form-horizontal">
            <form action="<c:url value="/logout"/>" role="form" method="post">
                <input type="hidden" name="${_csrf.parameterName}"
                       value="${_csrf.token}"/>
                <button class="btn btn-primary" type="submit">Logout</button>
            </form>
        </div>

    </authz:authorize>

</div>

</body>
</html>