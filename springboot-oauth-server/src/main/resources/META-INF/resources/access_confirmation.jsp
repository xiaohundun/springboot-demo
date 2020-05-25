<%@ page
        import="org.springframework.security.core.AuthenticationException" %>
<%@ page
        import="org.springframework.security.oauth2.common.exceptions.UnapprovedClientAuthenticationException" %>
<%@ page
        import="org.springframework.security.web.WebAttributes" %>
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
    <link type="text/css" rel="stylesheet"
          href="<%=request.getContextPath()%>/webjars/bootstrap/css/bootstrap.min.css"/>
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/webjars/jquery/jquery.min.js"></script>
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/webjars/bootstrap/js/bootstrap.min.js"></script>
</head>

<body>

<div class="container">
    <h1>OAuth</h1>

    <%
        if (session.getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION) != null && !(session.getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION) instanceof UnapprovedClientAuthenticationException)) {
    %>
    <div class="error">
        <h2>Woops!</h2>

        <p>
            Access could not be granted.
            (<%=((AuthenticationException) session.getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION)).getMessage()%>)
        </p>
    </div>
    <%
        }
    %>
    <c:remove scope="session" var="SPRING_SECURITY_LAST_EXCEPTION"/>

    <authz:authorize access="hasRole('ROLE_USER')">
        <h2>Please Confirm</h2>

        <p>
            You hereby authorize "
            <c:out value="${client.clientId}"/>
            " to access your protected resources.
        </p>

        <form id="confirmationForm" name="confirmationForm"
              action="<%=request.getContextPath()%>/oauth/authorize" method="post">
            <input name="user_oauth_approval" value="true" type="hidden"/>
            <ul class="list-unstyled">
                <c:forEach items="${scopes}" var="scope">
                    <c:set var="approved">
                        <c:if test="${scope.value}"> checked</c:if>
                    </c:set>
                    <c:set var="denied">
                        <c:if test="${!scope.value}"> checked</c:if>
                    </c:set>
                    <li>
                        <div class="form-group">
                                ${scope.key}: <input type="radio" name="${scope.key}"
                                                     value="true" ${approved}>Approve</input> <input type="radio"
                                                                                                     name="${scope.key}"
                                                                                                     value="false" ${denied}>Deny</input>
                        </div>
                    </li>
                </c:forEach>
            </ul>
            <input type="hidden" name="${_csrf.parameterName}"
                   value="${_csrf.token}"/>
            <button class="btn btn-primary" type="submit">Submit</button>
        </form>

    </authz:authorize>

</div>

</body>
</html>