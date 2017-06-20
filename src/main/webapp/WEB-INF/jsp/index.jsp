<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html>
<html>
<head>
    <script type="text/javascript">
    	var months = ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'];
	    function formatDate(timestamp) {
	        if(!timestamp) return '';
	        var date = new Date(timestamp);
	        var y = date.getFullYear();
	        var m = date.getMonth();
	        var d = date.getDate();
	        return ((d<10?('0'+d):d)+'-'+months[m]+'-'+y);
	    }
    
        $(document).ready(function() {
        	var fnBlank = function() { return null; };
        	
        	$('#dataTable').dataTable({
        		"bFilter": false,
                "aaSorting": [],
        		"sAjaxSource": "search",
        		"sServerMethod": "GET",
        		"aoColumns": [
					{ "mDataProp": "patientName" },
					{ "mDataProp": "patientEmail" },
					{
						"mDataProp": "patientDob",
						"mRender": function(data, type, row) {
							return formatDate(row.patientDob);
						}
					},
					{ "mDataProp": "note" },
					{ "mDataProp": "status" },
					{
						"mDataProp": fnBlank, "bSortable": false,
						"mRender": function(data, type, row) {
							return '<a class="btn btn-primary btn-sm" href="view/' + row.id + '">VIEW</a>';
						}
					}
				]
            });
        });
    </script>
</head>
<body>
    <div class="row">
        <div class="panel panel-info">
            <div class="panel-heading">List E-Form</div>
            <div class="panel-body">
                <div class="col-xs-12">
                    <div class="alert alert-danger">
                        User: <sec:authentication property="principal"/>
                    </div>
                    
                    <table id="dataTable" class="table table-bordered table-hover">
                    	<thead>
                    		<tr>
                    			<th>Patient Name</th>
                    			<th>Patient Email</th>
                    			<th>Patient DOB</th>
                    			<th>Note</th>
                    			<th>Status</th>
                    			<th></th>
                    		</tr>
                    	</thead>
                    </table>
                </div>
            </div>
        </div>
    </div>
</body>
</html>