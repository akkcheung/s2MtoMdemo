<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Expires" CONTENT="-1">

<meta name="viewport" content="width=device-width, initial-scale=1.0">

  <!-- Bootstrap core CSS -->
    <link href="../css/bootstrap.min.css" rel="stylesheet">
    <!-- jQWidgets CSS -->
    <link href="../js/jqwidgets/styles/jqx.base.css" rel="stylesheet">
    <link href="../js/jqwidgets/styles/jqx.bootstrap.css" rel="stylesheet">
  
</head>
<body>

 <h2></h2>

Reference(s) : 
<p></p>
<a href="http://viralpatel.net/blogs/hibernate-many-to-many-annotation-mapping-tutorial/">many-to-many-annotation</a>
<p></p>
<a href="http://stackoverflow.com/questions/11746499/solve-failed-to-lazily-initialize-a-collection-of-role-exception">lazily initialize a collection</a>
<p></p>
<a href="http://viralpatel.net/blogs/tutorial-struts2-hibernate-example-eclipse/">struts2-hibernate-example</a>
<p></p>
<a href="http://www.cnblogs.com/fingerboy/p/5260776.html">��Xstruts2+hibernate</a>
<p></p>

Demo :<p></p>


 <div id='jqxWidget'>
 </div>

 <p></p>

 <div id='jqxWidget2'>
 </div>
 
 <p></p>
 
 <div>
 		<input type="button" value="Submit" id="btnSubmit" style="margin-right: 5px;" />
 </div>
 
  <p></p>
 
 <div id='dataTable'>
 </div>
 <p></p>
 
 <div id='dataTable2'>
 </div>
 
<!-- Bootstrap core JavaScript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script src="../js/scripts/jquery-1.11.1.min.js"></script>
    <script src="../js/scripts/bootstrap.min.js"></script>
    <!-- jQWidgets JavaScript files -->
    <script src="../js/jqwidgets/jqxcore.js"></script>
    
    <script type="text/javascript" src="../js/jqwidgets/jqxbuttons.js"></script>  
    
    <script type="text/javascript" src="../js/jqwidgets/jqxscrollbar.js"></script>
    <script type="text/javascript" src="../js/jqwidgets/jqxlistbox.js"></script>
    <script type="text/javascript" src="../js/jqwidgets/jqxdropdownlist.js"></script>
    
    <script type="text/javascript" src="../js/jqwidgets/jqxdata.js"></script> 
    <script type="text/javascript" src="../js/jqwidgets/jqxdatatable.js"></script> 
    
    <script type="text/javascript">
        $(document).ready(function () {
            // $("#buttonGroup").jqxButtonGroup({mode: "checkbox", theme: "bootstrap", height: "100%"});
            
            var source = [
                    {RecordName : "Emp 1", RecordID : 1},
                    {RecordName : "Emp 2", RecordID : 2},
                    {RecordName : "Emp 3", RecordID : 3}
            ];
            
            $("#jqxWidget").jqxDropDownList({ source: source, 
            		selectedIndex: 0, 
            		width: '200', height: '25',
            		displayMember: 'RecordName',
                valueMember: 'RecordID'
            	});
            
            var source2 = [
                    {RecordName : "Meeting 1", RecordID : 1},
                    {RecordName : "Meeting 2", RecordID : 2},
                    {RecordName : "Meeting 3", RecordID : 3}
            ];
            
            $("#jqxWidget2").jqxDropDownList({ source: source2, 
            		selectedIndex: 1, width: '200', height: '25',
            		displayMember: 'RecordName',
                valueMember: 'RecordID'
            });
            
            $("#btnSubmit").click(function () {
            	
            		var item = $("#jqxWidget").jqxDropDownList('getSelectedItem');
            		var item2 = $("#jqxWidget2").jqxDropDownList('getSelectedItem');
            	
            		console.log( item.value + ' | ' + item2.value);
            		
            		var data = "inputA=" + item.value + "&inputB=" + item2.value ;
            		
            		$.ajax({
                      dataType: 'json',
                      url: '/myWebApp03/demo-009/add',
                      type:	"POST",
                      data: data,
        		      cache: false,
        		      success: function(data){
										//alert(data);
        		    	  // window.location.href = adress;
        		    	  
	        		    	var table = $("#dataTable").jqxDataTable('updateBoundData');
									
        		      },
        		      error: function(){
            		    // alert("no way");
        		      }
               });          	
          	});
          	
          	var url = "/myWebApp03/demo-009/listInJson";
            // prepare the data
            var source =
            {
                dataType: "json",
                dataFields: [
                    { name: 'employeeId', type: 'int' },
                    { name: 'firstname', type: 'string' },
                    // { name: 'subject', map: "meetings>0>subject" }                    
                    { name: 'meetings', map: "meetings" }
                ],
                id: 'employeeId',
                url: url
            };
            
            var dataAdapter = new $.jqx.dataAdapter(source);
            $("#dataTable").jqxDataTable(
            {
                width: 850,
                pageable: true,
                pagerButtonsCount: 10,
                source: dataAdapter,
                columnsResize: true,
                columns: [
                  { text: 'employeeId', dataField: 'employeeId', width: 300 },
                  { text: 'firstname', dataField: 'firstname', width: 300 },
                  // { text: 'subject', dataField: 'subject', width: 180 }
                  { 
                  	text: 'meeting' , dataField: 'meetings',
                  	
                  	// row - row's index.
										// column - column's data field.
										// value - cell's value.
										// rowData - rendered row's object.
										
                  	cellsRenderer: function (row, column, value, rowData) {
                  		var meetings = rowData.meetings;
											// var container = "<div>";
											
											var info  = "";											
											
											for (var i = 0; i < meetings.length; i++) {												
												var meeting = meetings[i];												
												info += "<div><i>" + meeting.subject + "</i></div>";
                  		}
                  		
                  		// container += "</div>";
                  		// return container;
                  		console.debug("row :" + row + " " + info);
                  		
                  		return info;
                  	}
                  }
              	]
            });
          	
         
          	
        });
    </script>

</body>
</head>
</html>
