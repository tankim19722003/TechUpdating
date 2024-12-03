window.onload = function() {
    // get user
    let user = localStorage.getItem("user");
    if (user != null) {
        window.location.href = "http://localhost:8080/api/v1/dev_updating/show_home_page";
    }

}

class InputError {
    constructor(name, error) {
        this.name = name;
        this.error = error;
    }
}
async function login() {

    // get user name and password value
    let accountNode = document.querySelector("#account");
    let passwordNode = document.querySelector("#password");

    // check if username and password empty
    if (accountNode.value ==='' ||  passwordNode.value === '') {
        alert("Vui lòng điền thông tin đăng nhập!!");
        return;
    }

    let user = {
        "account" : accountNode.value,
        "password" : passwordNode.value
    };

    // call api to login
    let api = "http://localhost:8080/api/v1/dev_updating/processLoginForm";

    let options = {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
              'Accept': 'application/json'
        },
        body: JSON.stringify(user)
    };

    let response = await fetch(api, options);

      if (!response.ok) {
           let errorMessages = await response.json(); // or use userLoginResponse.json() for JSON errors

           // convert string error to object
           let errors = errorMessages;

           if (errors.length == 1 && errors[0].name == "error") {
                alert(errors[0].error);
                return;
           }

           if (errors.length > 0) {

              refreshErrors();
              // get node to show error
                for (let errorItem of errors) {

                    // if error field skip
                    if (errorItem.name == "error") continue;

                    let errorBody = document.querySelector("#error-" + errorItem.name);
                    let html = `<img class="error-icon" src="/images/mark.png">
                                                    <span class="error">${errorItem.error}</span>`;
                    errorBody.innerHTML = html;
                }
           }
       }
    // store user login to local storage
//    localStorage.setItem("user", JSON.stringify(userLoginResponse));

     let userLoginResponse = await response.json();
     localStorage.setItem("user", JSON.stringify(userLoginResponse));
    window.location.href = "http://localhost:8080/api/v1/dev_updating/show_home_page";
}

function refreshErrors() {
      let errorWraps = document.querySelectorAll(".error-wrap");

      // remove all error we have
      for (let errorNodeItem of errorWraps) {
            errorNodeItem.innerHTML = "";
      }
}

async function register() {

    // get input value
    let newUser = handleGetUserInputValue();
    if (newUser == null) return;

//     submit value to register
    let options = {
      method: 'POST',  // HTTP method
       headers: {
         'Content-Type': 'application/json',  // Specify the content type
       },
       body: JSON.stringify(newUser)
    };

    let api = "http://localhost:8080/api/v1/dev_updating/processRegisterForm";

    let response = await fetch(api, options);

    // handle error
    if (!response.ok) {
        let errors = await response.json();

        // check existing error
        console.log(errors);

        if (errors.length == 1 && errors[0].name == "error") {
            alert(errors[0].error);
            return;
        }

        // show field error
        for (let error of errors) {
            showInputError(error);
        }
        return;
    }

    // handle login success
    window.location.href= "http://localhost:8080/api/v1/dev_updating/show-register-successfully";


}

function showInputError(error) {
    let errorContainer = document.querySelector("#error-" + error.name);
    let html = ` <img src="/images/mark.png" alt="" sizes="" srcset="" class="error-icon">
                <span class="error">${error.error}</span>`;

    errorContainer.innerHTML = html;
}

function handleShowInputError(name, message) {
     let error = new InputError(name, message);
     showInputError(error);
}
function handleGetUserInputValue() {

    let errorFlag = false;

    // remove all the error
    refreshErrors();

    // fullname
    let fullname = document.querySelector("#fullname").value;
    if (fullname == "") {
        handleShowInputError("fullname", "Fullname can't be empty!!");
        errorFlag = true;
    }

    // account
    let account = document.querySelector("#account").value;
    if (account == "") {
       handleShowInputError("account", "Account can't be empty!!");
        errorFlag = true;
    }


    // retype password
    let retypePassword = document.querySelector("#retype-password").value;
    if (retypePassword == "") {
       handleShowInputError("retype-password", "Retype Password can't be empty!!");
        errorFlag = true;
    }


    // email
    let email = document.querySelector("#email").value;
    if (email == "") {
       handleShowInputError("email", "Email can't be empty!!");
       errorFlag = true;
    }

    // password
    let password = document.querySelector("#password").value;
    if (password == "") {
       handleShowInputError("password", "Password can't be empty!!");
       return;
    }

    if (password.length < 8) {
        handleShowInputError("password", "Password must be longer greater than 8 characters!!");
        return;
    }

    //check password and retype password match ?
   if (password !== retypePassword) {
        alert("Password does mismatch");
        return;
   }

    if (errorFlag) return;
    let newUser = {
        "fullname": fullname,
        "account" : account,
        "password" : password,
        "retype_password": retypePassword,
        "email": email
    };

    return newUser;
}

// show or hide password
document.querySelector("#show-password").onclick = function () {
    let showPasswordIcon = document.querySelector("#show-password");
    let hidingPasswordIcon = document.querySelector("#hide-password");

    // change password input's type to text
    let passwordNode = document.querySelector("#password");
    passwordNode.type = "text";

    // hide showing password icon
    showPasswordIcon.style = "display:none";

    // show hiding password icon
    hidingPasswordIcon.style = "display:block";

}

// hide password
document.querySelector("#hide-password").onclick = function() {

    let showPasswordIcon = document.querySelector("#show-password");
    let hidingPasswordIcon = document.querySelector("#hide-password");

    // change type of password input to password type
    let passwordNode = document.querySelector("#password");
    passwordNode.type = "password";

    // showing show password icon
    showPasswordIcon.style = "display:block";

    // hiding hide password icon
    hidingPasswordIcon.style = "display:none";

}


// show or hide retype password
let retypePassword = document.querySelector("#show-retype-password");
if (retypePassword != null) {
    retypePassword.onclick = function () {
            let showRetypePasswordIcon = document.querySelector("#show-retype-password");
            let hidingRetypePasswordIcon = document.querySelector("#hide-retype-password");

            // change password input's type to text
            let retypePasswordNode = document.querySelector("#retype-password");
            retypePasswordNode.type = "text";

            // hide showing password icon
            showRetypePasswordIcon.style = "display:none";

            // show hiding password icon
            hidingRetypePasswordIcon.style = "display:block";
    }

    // hide password
    document.querySelector("#hide-retype-password").onclick = function() {

        let showRetypePasswordIcon = document.querySelector("#show-retype-password");
        let hidingRetypePasswordIcon = document.querySelector("#hide-retype-password");

        // change type of password input to password type
        let passwordNode = document.querySelector("#retype-password");
        passwordNode.type = "password";

        // showing show password icon
        showRetypePasswordIcon.style = "display:block";

        // hiding hide password icon
        hidingRetypePasswordIcon.style = "display:none";

    }
}



