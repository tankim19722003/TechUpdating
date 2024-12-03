
window.onload = function() {

    // show user two ways security status
    let userObj = localStorage.getItem("user");
    if (userObj != null) {
        let user = JSON.parse(userObj);

        if (user.twoWaysSecurityEnabled) {
            document.querySelector("#two-way-security-control-info_status").textContent = "Đang bật";
        } else {
            document.querySelector("#two-way-security-control-info_status").textContent = "Đã tắt";
        }

    } else {
        alert("Vui lòng đăng nhập");
        window.location.href = "http://localhost:8080/api/v1/dev_updating/login";
        return;
    }
}

function getUserFromLocalStorage() {
    // update local storage user
    let localUserObj = localStorage.getItem("user");

    if (localUserObj == null) {
        alert("please login!!");
        window.location.href = "http://localhost:8080/api/v1/dev_updating/login";
    }

    let localUser = JSON.parse(localUserObj);
    return localUser;
}

function showChangingPasswordForm() {

    // show body
    let securityBody = document.querySelector("#security-form");
    securityBody.style = "display:flex";

    // show changing password form
    document.querySelector("#changing-password-form").style = "display:block;"
}

function closeSecurityForm() {
     // hide security form
    let securityBody = document.querySelector("#security-form");
    securityBody.style = "display:none";

     // hide changing password form
    document.querySelector("#changing-password-form").style = "display:none;"

    // clear changing password input value
    let inputList = document.querySelectorAll(".changing-password-form-input-list-item_input");
    for(let inputItem of inputList) {
        inputItem.value = "";
    }


  // hide changing two ways security
    document.querySelector("#security-form_body").style = "display:none;"

  // clear chaging two ways security input
  document.querySelector("#two-ways-confirmation-form_password-input").value = "";

  // hide inform
  document.querySelector("#two-ways-confirmation-form-message_content").textContent = "";
  document.querySelector("#two-ways-confirmation-form-message_img").classList.add("d-none");

  // hide the password message on changing password form
  let messagesWrap = document.querySelectorAll(".changing-password-form-input-list-message");

  for (let messageWrapItem of messagesWrap) {
    messageWrapItem.style = "display:none";
  }
}

function showTwoWaysConfirmationForm() {
    // show body
    let securityBody = document.querySelector("#security-form");
    securityBody.style = "display:flex";

    // show changing password form
    document.querySelector("#security-form_body").style = "display:block;"
}

let eyeBtns = document.querySelectorAll(".changing-password-form-input-list-item_img");

for (let eyeBtnItem of eyeBtns) {
    eyeBtnItem.onclick = function(e) {
        // get eye btn's parent
        let eyeBtn = e.currentTarget;
        let parent = eyeBtn.parentNode;

        // change type input to text
        let input = parent.querySelector(".changing-password-form-input-list-item_input");
        input.type = "text";

        // hide eye btn
        eyeBtn.classList.add("d-none");

        // show hidding password btn
        let hidingEyeIcon = parent.querySelector(".hiding-changing-password-form-input-list-item_img");
        hidingEyeIcon.classList.remove("d-none");
    }
}

let eyeHidingBtns = document.querySelectorAll(".hiding-changing-password-form-input-list-item_img");

for (let eyeHidingBtnItem of eyeHidingBtns) {
    eyeHidingBtnItem.onclick = function(e) {
        // get hidding eye btn's parent
        let eyeHidingBtn = e.currentTarget;
        let parent = eyeHidingBtn.parentNode;

        // change type input to text
        let input = parent.querySelector(".changing-password-form-input-list-item_input");
        input.type = "password";

        // hide eye btn
        eyeHidingBtn.classList.add("d-none");

        // show hidding password btn
        let eyeIcon = parent.querySelector(".changing-password-form-input-list-item_img");
        eyeIcon.classList.remove("d-none");
    }
}

// handling updating 2 ways security
async function updateTwoWaysSecurity() {

    // check empty password input
    let passwordInput = document.querySelector("#two-ways-confirmation-form_password-input");

    if (passwordInput.value === "") {
        alert("Mật khẩu không được rỗng!!");
        return;
    }

    let updatingTwoWaysSecurityAPI = "http://localhost:8080/api/v1/dev_updating/update-two-way-security";

    // get user login from local storage
    let user = localStorage.getItem("user");
    if (user == null) {
        alert("Vui lòng đăng nhập");
        return;
    }

    user = JSON.parse(user);
    let twoWaySecurityDTO = {
        "user_id" : user.id,
        "password": passwordInput.value
    };
    let options = {
        method:"PUT",
        headers :{
            "Content-Type": "application/json"
        },
        body : JSON.stringify(twoWaySecurityDTO)
    }

    let response = await fetch(updatingTwoWaysSecurityAPI, options);

    // if update 2 ways security error
    if (!response.ok) {
        let  errorResponse = await response.json();
        document.querySelector("#two-ways-confirmation-form-message_content").textContent = errorResponse.error;
        document.querySelector("#two-ways-confirmation-form-message_img").classList.remove("d-none");
        return;
    }

    let userResponse = await response.json();

    // update local storage user
    let localUser = getUserFromLocalStorage();

    localUser.twoWaysSecurityEnabled = userResponse.twoWaysSecurityEnabled;
    localStorage.setItem("user", JSON.stringify(localUser));

    // update two ways security status
    if (userResponse.twoWaysSecurityEnabled) {
        document.getElementById("two-way-security-control-info_status").textContent = "Đang bật";
    } else {
        document.getElementById("two-way-security-control-info_status").textContent = "Đã tắt";
    }

    alert("Cập nhật bảo mật 2 chiều thành công!!");

    closeSecurityForm();

}

async function changePassword() {

    let isError = false;
    // check input value
    // check old password empty
    let oldPasswordInput = document.querySelector("#changing-password-form-input-list-item_old-password");
    if (oldPasswordInput.value == "") {
        document.querySelector("#old-password-message-wrap").style = "display:block";
        isError = true;
    }

    // check new password empty
    let newPasswordInput =  document.querySelector("#changing-password-form-input-list-item_new-password");
    if (newPasswordInput.value == "") {
        document.querySelector("#new-password-message-wrap").style = "display:block";
        isError = true;
    }
    // check retype password empty
    let retypePasswordInput =  document.querySelector("#changing-password-form-input-list-item_retype-password");
    if (retypePasswordInput.value == "") {
        document.querySelector("#retype-password-message-wrap").style = "display:block";
        isError = true;
    }

    if (isError) return;

    // check password and retype password mismatch
    if (retypePasswordInput.value != newPasswordInput.value) {
        alert("Mật khẩu không khớp!!");
        return;
    }


    // get ip from local host
    let userObj = localStorage.getItem("user");
    let user;
    if (userObj != null) {
        user = JSON.parse(userObj);
    } else {
        alert("Please login!!!");
        window.location.href = "http://localhost:8080/api/v1/dev_updating/login";
    }

    //create user change password dto from input
    let userChangingPasswordDTO = {
        "old_password" : oldPasswordInput.value,
        "new_password": newPasswordInput.value,
        "retype_password": retypePasswordInput.value
    }

    // call api to update password
    let updatePasswordAPI = "http://localhost:8080/api/v1/dev_updating/change-password/" + user.id;

    let options = {
        "method": "PUT",
        "headers": {
            "Content-Type" :"application/json"
        },
        body: JSON.stringify(userChangingPasswordDTO)
    }

    // call api to update password
    let response = await fetch(updatePasswordAPI, options);

    // check updating password status
    if (!response.ok) {
        let errors = await response.json();
        alert(errors[0].error);
    } else {
        alert("Cập nhật mật khẩu thành công!!");
        closeSecurityForm();
    }


}