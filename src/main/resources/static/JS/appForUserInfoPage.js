class User {
    constructor(id, fullname, email, account, avatar) {
        this.id = id;
        this.fullname = fullname;
        this.email = email;
        this.account = account;
        this.avatar;
    }

}

function showUpdateFullnameForm() {
    //  set visibili for
    let form = document.querySelector("#update-form");
    form.style = "visibility: visible";

    // show full name form
    let fullNameForm = document.querySelector("#fullname-update");
    fullNameForm.style = "display:block";

    // get user from local storage
    let user = JSON.parse(localStorage.getItem("user"));
    console.log(user);
    // get full name and add it to input value
    let fullname = document.querySelector("#fullname");
    fullname.value = user.fullname;
}

// close update form
//function closeUpdateForm() {
//  //  set visibili for
//    let form = document.querySelector("#update-form");
//    form.style = "visibility: hidden";
//
//    // hide full name form
//    let fullNameForm = document.querySelector("#fullname-update");
//    fullNameForm.style = "display:none";
//
//    // hide email form
//    let emailForm = document.querySelector("#email-update");
//    emailForm.style = "display:none";
//}

function showUpdateEmailForm() {
     //  set visibili for
        let form = document.querySelector("#update-form");
        form.style = "visibility: visible";

        // show full name form
        let fullNameForm = document.querySelector("#email-update");
        fullNameForm.style = "display:block";

        // get user from local storage
        let user = JSON.parse(localStorage.getItem("user"));
        // get email and add it to input value
        let email = document.querySelector("#email");
        email.value = user.email;
}

async function updateFullname() {

    // get fullname input value
    let fullnameValue = document.querySelector("#fullname").value;

    // get user from localhost
    let user = localStorage.getItem("user");
    if (user != null) user = JSON.parse(user);

    // set new fullname
    if (fullnameValue == '') {
        alert("fullname can't be empty!!")
        return;
    }

    user.fullname = fullnameValue;
    // call api to save new pupdating info
    let api = `http://localhost:8080/api/v1/dev_updating/update_full_name?user_id=${user.id}&fullname=${user.fullname}`;

    let options = {
        "method" : "PUT",
        "headers": {
            "Content-Type": "application/json",
             "Cache-Control": "no-cache"
        },
    }

    let response = await fetch(api, options);

    if (!response.ok) {
        let error = await response.json();
        alert(error[0].error);
        return;
    }

    let updateUser = await response.json();

    // update user in local storage
    localStorage.setItem("user", JSON.stringify(updateUser));

    alert("Thay đổi thông tin thành công!")

    // display new changes
    location.reload();
}


function showGmailUpdatingForm() {
    // show update form parent
    document.querySelector("#update").classList.add("di-flex");

    // get gmail from localhost fill in input
    let email = getUserInfoFiledByKeyFromLocalStorage("email");
    document.querySelector("#email-input").value = email;

    // show gmail form
    document.querySelector("#gmail-form").classList.add("di-block")
}


function showFullNameUpdatingForm() {
    // show update form parent
    document.querySelector("#update").classList.add("di-flex");

    //  fill fullname to input
     let fullname = getUserInfoFiledByKeyFromLocalStorage("fullname");
        document.querySelector("#fullname-input").value = fullname;
    // show gmail form
    document.querySelector("#fullname-form").classList.add("di-block")
}

let closeBtns = document.querySelectorAll(".close-update-form");

for (let closeBtn of closeBtns) {
    closeBtn.onclick = function () {
       // change image source file to current img
        let src = document.querySelector("#body-avatar_image").src;

        closeUpdatingForm(src);
    }
}

function closeUpdatingForm() {
        // show update form parent
        document.querySelector("#update").classList.remove("di-flex");

        // hide fullname form
        document.querySelector("#fullname-form").classList.remove("di-block");

        // hide gmail form
        document.querySelector("#gmail-form").classList.remove("di-block");
    }

function getUserInfoFiledByKeyFromLocalStorage(key) {
    let userObj = localStorage.getItem("user");
    if (userObj) {
        let user = JSON.parse(userObj);
        return user[key];
    } else {
        return "";
    }
}

function getUserFromLocalStorage() {
  let userObj = localStorage.getItem("user");
    if (userObj) {
        let user = JSON.parse(userObj);
        return user;
    } else {
        return null;
    }
}


// handle update info to server

async function updateFullname() {
    // get input from form
    let fullname = document.querySelector("#fullname-input").value;
    if (fullname == "") {
        alert("Fullname không được rỗng!!");
        return;
    }
    // compare to existing user
    let existingFullname = getUserInfoFiledByKeyFromLocalStorage("fullname");
    if (existingFullname === fullname) {
        alert("Fullname không được trùng với fullname sẵn có!!");
        return;
    }
    // update fullname
    let user = getUserFromLocalStorage();
    user.fullname = fullname;

    // update fullname
      let options = {
            "method" : "PUT",
            "headers": {
                "Content-Type": "application/json",
                 "Cache-Control": "no-cache"
            },
        }
    let api = `http://localhost:8080/api/v1/dev_updating/update_full_name?user_id=${user.id}&fullname=${fullname}`

    let response = await fetch(api, options);

    if (!response.ok) {
        let error = await response.json();
        alert(error[0].error);
        return;
    } else {

        let user = await response.json();

        // inform update successfully
        alert("Update fullname successfully!!");

        // change user in localstorage to updating user
        localStorage.setItem("user", JSON.stringify(user));

        // close form
        closeUpdatingForm();

        //display new fullname
        document.querySelector("#fullname").textContent = user.fullname;
    }
}

async function updateEmail() {
    // get input from form
    let email = document.querySelector("#email-input").value;
    if (email == "") {
        alert("Email không được rỗng!!");
        return;
    }
    // compare to existing user
    let existingEmail = getUserInfoFiledByKeyFromLocalStorage("email");
    if (existingEmail === email) {
        alert("Email không được trùng với email sẵn có!!");
        return;
    }
    // update fullname
    let user = getUserFromLocalStorage();
    user.email = email;

    // update fullname
  let options = {
        "method" : "PUT",
        "headers": {
            "Content-Type": "application/json",
             "Cache-Control": "no-cache"
        },
    }
    let api = `http://localhost:8080/api/v1/dev_updating/update_email?user_id=1&email=${email}`

    let response = await fetch(api, options);

    if (!response.ok) {
        let error = await response.json();
        alert(error[0].error);
        return;
    } else {

        let user = await response.json();

        // inform update successfully
        alert("Update Email successfully!!");

        // change user in localstorage to updating user
        localStorage.setItem("user", JSON.stringify(user));

        // close form
        closeUpdatingForm();

        //display new fullname
        document.querySelector("#email").textContent = user.email;
    }
}

/* handling updating avatar */
const fileInput =  document.querySelector("#update-avatar-body_input");
 const imageDisplay = document.getElementById('update-avatar-body_img');
 fileInput.addEventListener('change', function() {
    const file = fileInput.files[0];  // Get the first file selected
    if (file) {
        const reader = new FileReader();  // Create a new FileReader
        reader.onload = function(e) {
            imageDisplay.src = e.target.result;  // Set the src attribute of the img
        };
        reader.readAsDataURL(file);  // Read the file as a Data URL (base64)
    }
});

// show form update avatar
function showFormUpdateAvatar() {
      document.querySelector("#form-update-avatar").style.display = "flex";
}

function closeUpdatingAvatarForm(src) {
    document.querySelector("#form-update-avatar").style.display = "none";

    // reset avatar to current avatar
    document.querySelector("#update-avatar-body_img").style.backgroundImage = `url('${src}')`;

    // reset avatar to current avatar on update avatar form

    // reset value of file to null;
    document.querySelector("#update-avatar-body_input").value = "";
}


document.querySelector("#update-avatar-body").addEventListener("submit", async function (event) {
     event.preventDefault();  // Prevent default form submission

    // Create a FormData object from the form
    const formData = new FormData(this);

    try {
        // Send the form data via a POST request
        const response = await fetch(this.action, {
            method: 'POST',
            body: formData
        });

        if (response.ok) {

            let imageResponse = await response.json();

            // reset avatar src
            let src = "http://localhost:8080/api/v1/dev_updating/image/avatar/" +imageResponse.message;
            closeUpdatingAvatarForm(src);

            // change image course of current avatar
            document.querySelector("#body-avatar-body").style.backgroundImage = `url('${src}')`;

            // update the avatar in local storage
            let userJSON = localStorage.getItem("user");
            let user = JSON.parse(userJSON);
            user.avatar = imageResponse.message;
            localStorage.setItem("user", JSON.stringify(user));


            // inform updating avatar successfully
            alert("Thay đổi ảnh đại diện thành công!!");

        } else {
            // Show an error message if the request failed
//            const errorMsg = await response.text();
//            alert('Failed to upload the image: ' + errorMsg);
            alert("faiil!!");

        }
    } catch (error) {
        // Handle any network errors
        alert('An error occurred: ' + error.message);
    }
})

/* ending handling updating avatar*/
