<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator"%>

<!DOCTYPE html>
<html>
<head>
    <link href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/css/bootstrap-theme.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/css/datatables.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/css/bootstrap-datepicker.css" rel="stylesheet">
    <style type="text/css">
		label.error{
		    color: red;
		    font-size: 10px;
		}
		input.error, select.error, textarea.error {
		    border: 1px solid red;
		}
	</style>
</head>
<body>
<nav class="navbar navbar-inverse" role="navigation">
    <div class="container">
        <div class="collapse navbar-collapse navbar-ex1-collapse">
            <ul class="nav navbar-nav">
                <li><a href="${pageContext.request.contextPath}/">List E-Form</a></li>
                <li><a href="${pageContext.request.contextPath}/create">Create E-Form</a></li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <li><a href="${pageContext.request.contextPath}/#">LOGOUT</a></li>
            </ul>
        </div>
    </div>
</nav>

<div class="container">
    <decorator:body />

    <div class="well" style="text-align:center;">
        <strong>PesanLab - Mensa Health Tech</strong>
    </div>
</div>

<script src="${pageContext.request.contextPath}/resources/js/jquery.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/datatables.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/bootstrap-datepicker.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/jquery.validate.js"></script>
<decorator:head/>
</body>
</html>