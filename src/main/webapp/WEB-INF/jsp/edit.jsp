<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html>
<html>
<head>
    <script type="text/javascript">
        $(document).ready(function() {
        	$('.datepicker').datepicker({
                format: 'dd-M-yyyy',
                autoclose: true
            });
        	
        	var items = '${eform.items}';
        	$.each(items.split(","), function(i,e){
        	    $("#rontgenItems option[value='" + e + "']").prop("selected", true);
        	    $("#generalItems option[value='" + e + "']").prop("selected", true);
        	});
        	
        	$("form").validate();
        });
    </script>
</head>
<body>
    <div class="row">
        <div class="panel panel-info">
            <div class="panel-heading">Details E-Form</div>
            <div class="panel-body">
				<form class="form-horizontal" action="${pageContext.request.contextPath}/save" method="post">
					<c:if test="${not empty eform.id}">
						<input type="hidden" name="id" value="${eform.id}"/>
					</c:if>
					<div class="col-xs-6">
						<div class="form-group">
							<label class="col-sm-3 control-label">Patient Name</label>
							<label class="col-sm-1 control-label">:</label>
							<div class="col-sm-8">
								<input type="text" class="form-control required" name="patientName" value="${eform.patientName}"/>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label">Patient Email</label>
							<label class="col-sm-1 control-label">:</label>
							<div class="col-sm-8">
								<input type="text" class="form-control required email" name="patientEmail" value="${eform.patientEmail}"/>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label">Patient DOB</label>
							<label class="col-sm-1 control-label">:</label>
							<div class="col-sm-8">
								<input type="text" class="form-control datepicker required date" name="patientDob" 
									value="<fmt:formatDate value="${eform.patientDob}" pattern="dd-MMM-yyyy"/>"/>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label">Note</label>
							<label class="col-sm-1 control-label">:</label>
							<div class="col-sm-8">
								<textarea class="form-control required" name="note" rows="3">${eform.note}</textarea>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label">Rontgen Items</label>
							<label class="col-sm-1 control-label">:</label>
							<div class="col-sm-8">
								<select class="form-control required" name="rontgenItems" id="rontgenItems" multiple>
									<c:forEach var="item" items="${rontgenItems}">
										<option value="${item[0]}">${item[0]} ${item[1]}</option>
									</c:forEach>
								</select>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label">General Items</label>
							<label class="col-sm-1 control-label">:</label>
							<div class="col-sm-8">
								<select class="form-control required" name="generalItems" id="generalItems" multiple>
									<c:forEach var="item" items="${generalItems}">
										<option value="${item[0]}">${item[0]} ${item[1]}</option>
									</c:forEach>
								</select>
							</div>
						</div>
						<div class="form-group">
							<div class="col-sm-offset-4 col-sm-8">
								<button type="submit" class="btn btn-primary">
									<span class="glyphicon glyphicon-floppy-disk"></span> SAVE</button>
								
								<a class="btn btn-default" href="${empty eform.id ? pageContext.request.contextPath : '../view/'.concat(eform.id)}">
									<span class="glyphicon glyphicon-arrow-left"></span> CANCEL</a>
							</div>
						</div>
					</div>
					<div class="col-xs-6">
						<div class="form-group">
							<label class="col-sm-3 control-label">Doctor Name</label>
							<label class="col-sm-1 control-label">:</label>
							<div class="col-sm-8">
								<input type="text" class="form-control required" name="doctorName" value="${eform.doctorName}"/>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label">Doctor Email</label>
							<label class="col-sm-1 control-label">:</label>
							<div class="col-sm-8">
								<input type="text" class="form-control required email" name="doctorEmail" value="${eform.doctorEmail}"/>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label">Company Code</label>
							<label class="col-sm-1 control-label">:</label>
							<div class="col-sm-8">
								<select class="form-control required" name="companyCode">
									<option value="">-- Choose --</option>
									<c:forEach var="company" items="${listCompany}">
										<option value="${company[0]}" ${company[0] == eform.companyCode ? 'selected' : ''}>${company[1]}</option>
									</c:forEach>
								</select>
							</div>
						</div>
					</div>
				 </form>
            </div>
        </div>
    </div>
</body>
</html>