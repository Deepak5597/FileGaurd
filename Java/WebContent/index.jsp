<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
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
    <script type="text/javascript">
    
    $(document).ready(function() {
    	
    	$('#signup_button').click(function(e) {
		    e.preventDefault();
		    var fname =  document.getElementById("fName").value;
	        var lname =  document.getElementById("lName").value;
	        var signup_email_address = document.getElementById("signup_email_address").value;
	        var password = document.getElementById("signup_user_password").value;
	        var confirm_password = document.getElementById("user_confirm_password").value;
	    	var formData ={
	            	'fName' : fname,
	            	'lName' : lname,
	            	'password' :  password,
	            	'email' : signup_email_address
	            };
	            jQuery.ajax({headers: {"cache-control": "no-cache", "Access-Control-Allow-Origin": "*"},
	                type: "POST",    
	                cache: false,
	                dataType: "text",
	                data:formData,
	                url: "./SignupService", 
	                success: function(jsonResponse) {
	                	res=JSON.parse(jsonResponse);
	                	if(res.status == 'success'){
	                		alert(res.message);
	                		document.getElementById("signupcancelbutton").click();
	                		window.location.href = 'home.jsp';
	                	}else if(res.status == 'error'){
	                		alert(res.message);
	                	}
	                }, 
	                error: function(jsonResponse)
	               {
	                	res=JSON.parse(jsonResponse);
	                	alert(res.message)
	               }
	           });
    	});
    	
    	$('#login_button').click(function(e) {
		    e.preventDefault();
		    var login_email_address = document.getElementById("login_email_address").value;
	        var password = document.getElementById("login_user_password").value;
	    	var formData ={
	            	'password' :  password,
	            	'email' : login_email_address
            };
            jQuery.ajax({headers: {"cache-control": "no-cache", "Access-Control-Allow-Origin": "*"},
                type: "POST",    
                cache: false,
                dataType: "text",
                data:formData,
                url: "./LoginService", 
                success: function(jsonResponse) {
                	res=JSON.parse(jsonResponse);
                	if(res.status == 'success'){
                		alert(res.message);
                		window.location.href = 'home.jsp';
                	}else if(res.status == 'error'){
                		alert(res.message);
                	}
                }, 
                error: function(jsonResponse)
               {
                	res=JSON.parse(jsonResponse);
                	alert(res.message)
               }
              });
    	});
    });

    </script>
   <title>File Gaurd</title>
  </head>
  <body> 
    <div class="body-wrapper">
      <nav class="navbar navbar-light bg-light">
        <a class="navbar-brand logo" href="/view/home.html">
            <i class="fa fa-shield "></i>
            File Gaurd
        </a>
        <ul class="sl-link">
            <li class="nav-item btn-border">
              <a class="nav-link" href="#" data-toggle="modal" data-target="#loginPopup">Sign In</a>
            </li>
            <li class="nav-item ">
                <a class="nav-link" href="#" data-toggle="modal" data-target="#signupPopup">Sign Up</a>
            </li>
        </ul>
    </nav>
    <section class="container wrapper">
        <div class="text-center quote-text">
            <i class="fa fa-shield quote-logo" ></i>
            <h5 class="display-3">Secure Anything </h5>
            <p class="">Secure Your personel Files, Images and much more with File Guard!</p> 
            <a href="/view/home.html" class="button btn btn-success btn-lg">Explore More</a>
        </div>
    </section>
    <section class="footer">
         <h5 class="text-center bg-dark p-2">FileGuard @All rights reserved - 2020</h5>   
    </section>
    <!-- <div class="page-loader">
        <div class="spinner-border m-5 custom-spinner" role="status">
          <span class="sr-only custom-spinner-text">Loading...</span>
        </div>
    </div> -->
    </div>
    

        <!-- Signup Modal starts-->
        <div class="modal fade" id="signupPopup" data-backdrop="static" data-keyboard="false" tabindex="-1" role="dialog" aria-labelledby="createFolderPopupLabel" aria-hidden="true">
          <div class="modal-dialog modal-dialog-centered">
              <div class="modal-content">
                  <div class="modal-header">
                    <h5 class="modal-title" id="staticBackdropLabel">Sign up</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                      <span aria-hidden="true">&times;</span>
                    </button>
                  </div>
                  <div class="modal-body">
                      <form id="signupForm">
                        <div class="row">
                          <div class="col-md-12">
                            <div class="alert alert-danger" role="alert" id="signupErrorBox">
                              Something is not right
                            </div>
                          </div>
                        </div>
                        <div class="row">
                          <div class="col-md-6">
                            <div class="form-group">
                              <label for="fName">First Name</label>
                              <input type="text" class="form-control" id="fName" aria-describedby="fName" required>
                            </div>
                          </div>
                          <div class="col-md-6">
                            <div class="form-group">
                              <label for="lName">Last Name</label>
                              <input type="text" class="form-control" id="lName" aria-describedby="lName" required>
                            </div>
                          </div>
                        </div>
                        <div class="row">
                          <div class="col-md-12">
                            <div class="form-group">
                              <label for="signup_email_address">Email Address</label>
                              <input type="email" class="form-control" id="signup_email_address" aria-describedby="signup_email_address" required>
                            </div>
                          </div>
                        </div>
                        <div class="row">
                          <div class="col-md-6">
                            <div class="form-group">
                              <label for="signup_user_password">Password</label>
                              <input type="password" class="form-control" id="signup_user_password" aria-describedby="signup_user_password" required  onblur="validateSignup()">
                            </div>
                          </div>
                          <div class="col-md-6">
                            <div class="form-group">
                              <label for="user_confirm_password">Confirm Password</label>
                              <input type="password" class="form-control" id="user_confirm_password" aria-describedby="user_confirm_password" required  onblur="validateSignup()">
                            </div>
                          </div>
                        </div>
                          <div class="action-btn">
                              <button type="button cancel-btn " data-dismiss="modal" class="btn btn-danger" id=signupcancelbutton> Cancel</button>
                              <button type="button success-btn" class="btn btn-success" id="signup_button">Signup</button>
                          </div>
                        </form>
                  </div>
              </div>
          </div>  
      </div>
      <!-- Signup Modal ends-->

        <!-- Login Modal starts-->
        <div class="modal fade" id="loginPopup" data-backdrop="static" data-keyboard="false" tabindex="-1" role="dialog" aria-labelledby="createFolderPopupLabel" aria-hidden="true">
          <div class="modal-dialog modal-dialog-centered">
              <div class="modal-content">
                  <div class="modal-header">
                    <h5 class="modal-title" id="staticBackdropLabel">Sing in</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                      <span aria-hidden="true">&times;</span>
                    </button>
                  </div>
                  <div class="modal-body">
                      <form id="loginForm">
                        <div class="row">
                          <div class="col-md-12">
                            <div class="alert alert-danger" role="alert" id="loginErrorBox">
                              Something is not right
                            </div>
                          </div>
                        </div>
                        <div class="row">
                          <div class="col-md-12">
                            <div class="form-group">
                              <label for="login_email_address">Email Address</label>
                              <input type="email" class="form-control" id="login_email_address" aria-describedby="login_email_address" required>
                            </div>
                          </div>
                        </div>
                        <div class="row">
                          <div class="col-md-12">
                            <div class="form-group">
                              <label for="login_user_password">Password</label>
                              <input type="password" class="form-control" id="login_user_password" aria-describedby="login_user_password" required>
                            </div>
                          </div>
                        </div>
                          <div class="action-btn">
                              <button type="button cancel-btn " data-dismiss="modal" class="btn btn-danger">Cancel</button>
                              <button type="button success-btn" class="btn btn-success" id="login_button">Login</button>
                          </div>
                        </form>
                  </div>
              </div>
          </div>  
      </div>
      <!-- Login Modal ends-->
    <script src="https://code.jquery.com/jquery-3.4.1.min.js" integrity="sha256-CSXorXvZcTkaix6Yvo6HppcZGetbYMGWSFlBw8HfCJo="  crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js" integrity="sha384-OgVRvuATP1z7JjHLkuOU7Xw704+h835Lr+6QL9UvYjZE3Ipu6Tp75j7Bh/kR0JKI" crossorigin="anonymous"></script>
  </body>
</html>