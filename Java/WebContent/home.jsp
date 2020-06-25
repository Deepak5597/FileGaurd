<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="java.sql.*" %>
<%@ page import="org.json.*" %>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link href = "https://code.jquery.com/ui/1.10.4/themes/ui-lightness/jquery-ui.css" rel = "stylesheet">
	<script src = "json2.js"></script>
	<script src = "https://code.jquery.com/jquery-1.10.2.js"></script>
	<script src = "https://code.jquery.com/ui/1.10.4/jquery-ui.js"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css" integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk" crossorigin="anonymous">
    <link rel="stylesheet" href="main.css">
    <script src="script.js" type="text/javascript"> </script>
    <title>File Gaurd</title>
  </head>
  <script>
  var filesData;
  var fileSelected;
  var folderSelectedByUser;
  $(document).ready(function() {
	  
	  $('#createFolderButton').click(function(e) {
		     e.preventDefault();
		     folder_name = document.getElementById("folder_name").value;
			  formdata = {
					'filename': folder_name
			  }
			  console.log(formdata)
			  jQuery.ajax({headers: {"cache-control": "no-cache", "Access-Control-Allow-Origin": "*",'Accept': 'application/json'},
			      type: "POST",    
			      cache: false,
			      dataType: "text",
			      url: "./CreateFolder", 
			      data:formdata,
			      success: function(jsonResponse) {
			    	  jsondata=JSON.parse(jsonResponse);
			    	  if(jsondata.status == 'success'){
			    		  alert(jsondata.message);
				      		document.getElementById("createfoldercancelbutton").click();
			    		  refreshFolders();
			    	  }else if(jsondata.status == 'error'){
			    		  	document.getElementById("createFolderErrorBox").innerHTML =res.message;
				      		document.getElementById("createFolderErrorBox").style.display='block';
			    	  }
			      }, 
			      error: function(jsonResponse)
				     {
				      	res=JSON.parse(jsonResponse);
				      	alert(res.message)
				     }
			  });
	  });
	  

	  
	  $('#logoutButton').click(function(e) {
		     e.preventDefault();
		     jQuery.ajax({headers: {"cache-control": "no-cache", "Access-Control-Allow-Origin": "*",'Accept': 'application/json'},
			      type: "POST",    
			      cache: false,
			      dataType: "text",
			      url: "./LogoutService", 
			      success: function(jsonResponse) {
			      	res=JSON.parse(jsonResponse);
			      	if(res.status == 'success'){
			      		alert(res.message)
			      		window.location.href = 'index.jsp';
			      	}
			      }, 
			      error: function(jsonResponse)
			     {
			      	res=JSON.parse(jsonResponse);
			      	alert(res.message)
			     }
			    });
	  	});
	  
	  $(document).on('click','#indfolder',function(e){
			var folder = e.target;
			var foldername = folder.childNodes[0].childNodes[1].innerHTML;
			folderSelectedByUser = foldername;
			var row = document.getElementById("folder_row");
			for (var i=0; i<row.childNodes.length; i++) {
				  var child = row.childNodes[i];
				  child.classList.remove("folder-box-active");
			}
			folder.classList.add("folder-box-active");
			
			refreshFiles(folderSelectedByUser);
	  });
	  
	  $('input[type=file]').change(function () {
		    this.fileSelected = this.files[0];
		});
	  
	  refreshFolders();
  });
  
  function refreshFiles(directoryname){
	  
	  var table = jQuery("#filesDataTable");
	  $("#filesDataTable > tbody").html("");
	  for(var i=0;i<filesData.length;i++){
		  if(filesData[i].isDirectory == 'NO' && filesData[i].directoryname == directoryname){
			  var dataCol="";
			    row = $('<tr onclick="showfile(this)"></tr>');
			    dataCol = $('<td></td>').text(filesData[i].fileid);
			    row.append(dataCol);
			    dataCol = $('<td></td>').text(filesData[i].filename);
			    row.append(dataCol);
			    dataCol = $('<td></td>').text(filesData[i].filetype);
			    row.append(dataCol);
			    dataCol = $('<td></td>').text(filesData[i].filepath);
			    row.append(dataCol);
			    dataCol = $('<td></td>').text(filesData[i].encryptiontype);
			    row.append(dataCol);
			    dataCol = $('<td></td>').text(filesData[i].creationdate);
			    row.append(dataCol);
			    dataCol = $('<td></td>').text(filesData[i].filesize);
			    row.append(dataCol);
			    table.append(row);
		  }
	  }
  }

  
  function performAjaxSubmit() {
      var encryptiontype = document.getElementById("encryptiontype").value;

      var filedata = document.getElementById("file_name").files[0];

      var formdata = new FormData();
      formdata.append("encryptiontype", encryptiontype);
	  formdata.append("foldername",folderSelectedByUser);	
      formdata.append("filedata", filedata);
      var xhr = new XMLHttpRequest();       

      xhr.open("POST","./CreateFile", true);
      xhr.send(formdata);
      alert("File Added Successfully");
  }
  
  function refreshFolders(){
	  document.getElementById("createFolderPopup").style.display = 'none'; 
	  document.getElementById("custom_loader").style.display = 'block'; 
	  jQuery.ajax({headers: {"cache-control": "no-cache", "Access-Control-Allow-Origin": "*",'Accept': 'application/json'},
	      type: "POST",    
	      cache: false,
	      url: "./RefreshFolder",
	      success: function(jsonResponse) {
	      	res=JSON.parse(JSON.stringify(jsonResponse));
	      	folderData = JSON.parse(res.data);
	      	filesData = folderData;
	      	if(res.status == 'success'){
	      		$("#folder_row").html("");
	      		if(folderData.length){
		      		for(var i=0;i<folderData.length;i++){
		      			if(folderData[i].isDirectory == 'YES'){
		      				f_box = document.createElement("div");
		      				f_box.className = 'col-md-12 folder-box';
		      				f_box.id = 'indfolder';
		      				
		      				h4 =  document.createElement("h4");
		      				h4.innerHTML= getFileCount(folderData,folderData[i].filename);
		      				
		      				p = document.createElement("p");
		      				p.innerHTML=folderData[i].filename;
		      				
		      				icon =  document.createElement("i");
		      				icon.className = 'fa fa-folder';
		      				
		      				f_box.appendChild(h4);
		      				h4.appendChild(p);
		      				f_box.appendChild(icon);
		      				document.getElementById("folder_row").appendChild(f_box);
		      			}
		      		}
		      		
	      		}else{
	      			f_box = document.createElement("div");
      				f_box.className = 'col-md-12 folder-box';
      				p = document.createElement("p");
      				p.className = 'centered-text';
      				p.innerHTML='No Folders Found';
      				f_box.appendChild(p);
      				document.getElementById("folder_row").appendChild(f_box);
	      		}
	      		document.getElementById("custom_loader").style.display = 'none';
	      	}
	      }, 
	      error: function(jsonResponse)
	     {
	    	document.getElementById("custom_loader").style.display = 'none';
	      	alert(res.message)
	     }
	    });
	  

  }
  
  function getFileCount(data,directoryname){
	  var count=0;
	  
	  for(var i=0;i<data.length;i++){
		  if(data[i].isDirectory == 'NO' && data[i].directoryname == directoryname){
			  count++;
		  }
	  }
	  	return count;
  }

  
  function showfile(e){
		var tablerow = e;
		var filetype=e.childNodes[2].innerHTML;
		var filename= e.childNodes[1].innerHTML;
		formdata = {
			filepath:e.childNodes[3].innerHTML,
			encryptiontype:e.childNodes[4].innerHTML,
			filetype:e.childNodes[2].innerHTML
		}
		console.log(formdata) 
	
		jQuery.ajax({headers: {"cache-control": "no-cache", "Access-Control-Allow-Origin": "*"},
		      type: "POST",    
		      cache: false,
		      dataType: "text",
		      url: "./GetFile", 
		      data:formdata,
		      success: function(jsonResponse) {
		    	  addContentToView(jsonResponse,filetype,filename);
		      }, 
		      error: function(jsonResponse)
			     {
			      	aler(jsonResponse,filetype)
			     }
		  });
		  
  }
  
  function addContentToView(content,type,name){
	  var contentbox = document.getElementById("viewcontentbody");
	  contentbox.innerHTML = "";
	  console.log(type)
	  var filetoshow = type.split('/')[0];
	  document.getElementById("viewfileheader").innerHTML = name;
	  if(filetoshow == 'image'){
		  var blob = new Blob([content], {type: type});
	        var reader = new FileReader();
	        reader.readAsDataURL(blob);
	        reader.onload = function() {
	            base64data = reader.result;                
	            var newimg = document.createElement("img");
	            newimg.setAttribute("src",base64data);
	            contentbox.appendChild(newimg);
	        };
	        document.getElementById("viewfilecontentbutton").click();
	  }else if(filetoshow == 'text' || (type == 'application/octet-stream')){
		  var blob = new Blob([content], {type: type});
	        var reader = new FileReader();
	        reader.readAsText(blob);
	        reader.onload = function() {
	            base64data = reader.result;                
	            var newimg = document.createElement("textarea");
	            newimg.innerHTML = base64data;
	            newimg.style.width='100%';
	            newimg.style.minHeight='500px';
	            newimg.style.padding='40px';
	            newimg.style.border='none';
	            contentbox.appendChild(newimg);
	        };
	        document.getElementById("viewfilecontentbutton").click();
	  }else if((type == 'application/zip') || (type == 'application/pdf')){
		  alert("File can't be shown here");
	  }
	  
  }
  </script>
  <body> 
 	<div class="body-wrapper">
  <%
 	 String username = "GUEST";
	if(session == null){ %>
		<script type="text/javascript">
			alert('Your Session has been Expired\nPlease click ok')
			window.location.href='index.jsp';
		</script>
	<%}else{ username = session.getAttribute("fName").toString();
	} %>
    <nav class="navbar navbar-light bg-light">
        <a class="navbar-brand logo" href="index.jsp">
            <i class="fa fa-shield "></i>
            File Gaurd
        </a>
        
        	<p class="welcome-tag">Welcome <%=username%></p>
        <ul class="sl-link">
            <li class="nav-item">
              <a class="nav-link" href="javascript:void(0)"  data-toggle="modal" data-target="#logoutPopup">
                  <i class="fa fa-sign-out"></i>
              </a>
            </li>
            <li class="nav-item">
                <a class="nav-link " href="javascript:void(0)">
                    <i class="fa fa-cog"></i>
                </a>
            </li>
        </ul>

    </nav>
    <section class="container-fluid wrapper">
        <div class="row content-box">
            <div class="col-md-2 folders-wrapper">

                <div class="row">
                    <div class="col folder-actions">
                        <a data-toggle="modal" data-target="#createFolderPopup" ><i class="fa fa-plus-square" ></i>New Folder</a>
                    </div>
                </div> 
                 <div class="row " id="folder_row">
                 </div>   
            </div>
            <div class="col-md-10 content-wrapper">
            	<table id="filesDataTable" class="table">
            		<thead class="thead-dark custom-thead">
            			<th scope="col">File Id</th>
            			<th scope="col">File Name</th>
            			<th scope="col">Type</th>
            			<th scope="col">Path</th>
            			<th scope="col">Encryption</th>
            			<th scope="col">Creation Date</th>
            			<th scope="col">File Size(KB)</th>
            		 </thead>
            		<tbody> </tbody>
                 </table>
                 <i class="fa fa-plus-square add-files-icon" data-toggle="modal" data-target="#addNewFilePopup"></i>
            </div>
        </div>
    </section>
    <section class="footer">
         <h5 class="text-center bg-dark p-2">FileGuard @All rights reserved - 2020</h5>   
    </section>
    <div class="page-loader" id="custom_loader">
        <div class="spinner-border m-5 custom-spinner" role="status">
          <span class="sr-only custom-spinner-text">Loading...</span>
        </div>
    </div>
    </div>
    <!-- Create Folder Modal starts-->
    <div class="modal fade" id="createFolderPopup" data-backdrop="static" data-keyboard="false" tabindex="-1" role="dialog" aria-labelledby="createFolderPopupLabel" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered">
            <div class="modal-content">
                <div class="modal-body">
                    <form>
                        <div class="alert alert-danger" role="alert" id="createFolderErrorBox"> 
                          Something is not right
                        </div>

                        <div class="form-group">
                          <label for="exampleInputEmail1">Folder Name</label>
                          <input type="text" class="form-control" id="folder_name" aria-describedby="folder_name" required>
                        </div>
                        <div class="action-btn">
                            <button type="button float-right cancel-btn " data-dismiss="modal" class="btn btn-danger" id="createfoldercancelbutton">Cancel</button>
                            <button type="button float-right success-btn" class="btn btn-success" id="createFolderButton" >Create Folder</button>
                        </div>
                      </form>
                </div>
            </div>
        </div>  
    </div>
    <!-- Create Folder Modal ends-->
    
    <!-- Create Files Modal starts-->
    <div class="modal fade" id="addNewFilePopup" data-backdrop="static" data-keyboard="false" tabindex="-1" role="dialog" aria-labelledby="addNewFilePopup" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered">
            <div class="modal-content">
                <div class="modal-body">
                    <form id="createFileForm" method="POST" enctype="multipart/form-data">
	                    <div class="row">
	                    	<div class="col-md-12">
		                    	<div class="alert alert-danger" role="alert" id="createFileErrorBox"> 
		                          Something is not right
		                        </div>
	                    	</div>
	                    </div>
	                    <div class="row">
	                    	<div class="col-md-12">
								<div class="form-group">
		                          <label for="exampleInputEmail1">Choose File</label>
		                          <input type="file" class="form-control" id="file_name" name="file_name" aria-describedby="file_name" required>
	                        	</div>
	                    	</div>
	                    </div>
						<div class="row">
	                    	<div class="col-md-12">
	          
								<select class="form-control" id="encryptiontype">
								  <option vaue="-1" selected disabled>Select Algorithm To Encrypt</option>
								  <option value="AES">AES Algorithm</option>
								</select>
	                    	</div>
	                    </div>
	                    <div class="row">
	                    	<div class="col-md-12">
			                    <div class="mt-2 action-btn">
			                        <button type="button float-right cancel-btn " data-dismiss="modal" class="btn btn-danger" id="addFilecancelbutton">Cancel</button>
			                        <button type="button float-right success-btn" class="btn btn-success" id="addFileButton"  onclick="performAjaxSubmit()">Add File</button>
			                    </div>
			                 </div>
	                    </div>
                  </form>
                </div>
            </div>
        </div>  
    </div>
    <!-- Create Files Modal ends-->
    
          <!-- Login Modal starts-->
        <div class="modal fade" id="logoutPopup" data-backdrop="static" data-keyboard="false" tabindex="-1" role="dialog" aria-labelledby="logoutPopup" aria-hidden="true">
          <div class="modal-dialog modal-dialog-centered"> 

              <div class="modal-content">
                  <div class="modal-body">
                        <div class="row">
                          <div class="col-md-12">
                            <div class="alert" role="alert" >
                              Are you sure , You want to Logout ?
                            </div>
                          </div>
                        </div>
                          <div class="action-btn">
                              <button type="button cancel-btn " data-dismiss="modal" class="btn btn-danger">No, Stay Here</button>
                              <button type="button success-btn" class="btn btn-success" id="logoutButton">Yes, Sure</button>
                          </div>
                  </div>
              </div>
          </div>  
      </div>
      <!-- Login Modal ends-->
      
     <!--  View File Modal starts-->
        <div class="modal fade" id="viewfilePopup" data-backdrop="static" data-keyboard="false" tabindex="-1" role="dialog" aria-labelledby="viewfilePopup" aria-hidden="true">
          <div class="modal-dialog modal-dialog-centered modal-lg"> 
              <div class="modal-content">
                 <div class="modal-header">
	                <h5 class="modal-title" id="viewfileheader"></h5>
	                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
	                  <span aria-hidden="true">&times;</span>
	                </button>
             	 </div>
                  <div class="modal-body " id="viewcontentbody">

                  </div>
                  <div class="modal-footer">
                  <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
              	  </div>
              </div>
              
          </div>  
      </div>
      <!-- View File Modal ends-->
      <a data-toggle="modal" data-target="#viewfilePopup" style="display:hidden;" id="viewfilecontentbutton"><i class="fa fa-plus-square" ></i></a>
    <script src="https://code.jquery.com/jquery-3.4.1.min.js" integrity="sha256-CSXorXvZcTkaix6Yvo6HppcZGetbYMGWSFlBw8HfCJo="  crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js" integrity="sha384-OgVRvuATP1z7JjHLkuOU7Xw704+h835Lr+6QL9UvYjZE3Ipu6Tp75j7Bh/kR0JKI" crossorigin="anonymous"></script>
  </body>
</html>