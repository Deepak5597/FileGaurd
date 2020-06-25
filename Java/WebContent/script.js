
function validateSignup(){
    var fname =  document.getElementById("fName").value;
    var lname =  document.getElementById("lName").value;
    var signup_email_address = document.getElementById("signup_email_address").value;
    var password = document.getElementById("signup_user_password").value;
    var confirm_password = document.getElementById("user_confirm_password").value;
    var signupErrorBox = document.getElementById("signupErrorBox");
    var signupBtn = document.getElementById("signup_button");
    if(fname.length > 0 && lname.length > 0 && signup_email_address.length > 0 && password.length > 0 && confirm_password.length){
      if(password != confirm_password){
      signupErrorBox.innerHTML = "Password Didn't match"
      signupErrorBox.style.display = 'block';
//      signupBtn.disabled = 'true';
      }else{
        signupErrorBox.style.display = 'none';
//        signupBtn.disabled = 'false';
      }
    }
}

