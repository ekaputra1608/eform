<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html>
<html>
<head>
</head>
<body>
    <div class="row">
        <div class="panel panel-info">
            <div class="panel-heading">Details E-Form</div>
            <div class="panel-body">
				<form class="form-horizontal" action="#" method="post">
					<input type="hidden" name="id" value="${eform.id}"/>
					<div class="col-xs-6">
						<div class="form-group">
							<label class="col-sm-3 control-label">Patient Name</label>
							<label class="col-sm-1 control-label">:</label>
							<div class="col-sm-8">
								<p class="form-control-static">${eform.patientName}</p>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label">Patient Email</label>
							<label class="col-sm-1 control-label">:</label>
							<div class="col-sm-8">
								<p class="form-control-static">${eform.patientEmail}</p>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label">Patient DOB</label>
							<label class="col-sm-1 control-label">:</label>
							<div class="col-sm-8">
								<p class="form-control-static">
									<fmt:formatDate value="${eform.patientDob}" pattern="dd-MMM-yyyy"/>
								</p>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label">Note</label>
							<label class="col-sm-1 control-label">:</label>
							<div class="col-sm-8">
								<p class="form-control-static">${eform.note}</p>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label">Items</label>
							<label class="col-sm-1 control-label">:</label>
							<div class="col-sm-8">
								<p class="form-control-static">${eform.items}</p>
							</div>
						</div>
						<div class="form-group">
							<div class="col-sm-offset-4 col-sm-8">
								<c:if test="${eform.status != 'SUBMITTED'}">
									<a class="btn btn-primary" href="../edit/${eform.id}">
										<span class="glyphicon glyphicon-pencil"></span> EDIT</a>
									
									<a class="btn btn-success" href="../submit/${eform.id}" onclick="return confirm('Are you sure to submit ?')">
										<span class="glyphicon glyphicon-ok"></span> SUBMIT</a>
									
									<a class="btn btn-danger" href="../delete/${eform.id}" onclick="return confirm('Are you sure to delete ?')">
										<span class="glyphicon glyphicon-remove"></span> DELETE</a>
								</c:if>
							</div>
						</div>
					</div>
					<div class="col-xs-6">
						<div class="form-group">
							<label class="col-sm-3 control-label">Doctor Name</label>
							<label class="col-sm-1 control-label">:</label>
							<div class="col-sm-8">
								<p class="form-control-static">${eform.doctorName}</p>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label">Doctor Email</label>
							<label class="col-sm-1 control-label">:</label>
							<div class="col-sm-8">
								<p class="form-control-static">${eform.doctorEmail}</p>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label">Company Code</label>
							<label class="col-sm-1 control-label">:</label>
							<div class="col-sm-8">
								<p class="form-control-static">${eform.companyCode}</p>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label">Status</label>
							<label class="col-sm-1 control-label">:</label>
							<div class="col-sm-8">
								<p class="form-control-static">${eform.status}</p>
							</div>
						</div>
					</div>
				 </form>
            </div>
        </div>
    </div>
</body>
</html>