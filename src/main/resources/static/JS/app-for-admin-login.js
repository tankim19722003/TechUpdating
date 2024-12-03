async function login() {
    // get user info login
    let account = document.querySelector("#account").value;
    let password = document.querySelector("#password").value;

    if(account == "" || password == "") {
        alert("Vui lòng điền đầy đủ thông tin đăng nhập!!");
        return;
    }

    // call api to login
    let api = "http://localhost:8080/api/v1/dev_updating/admin/login";
    let body = {
       "account": account,
       "password": password
    }

    let options = {
        "method" : "POST",
        "headers": {
            "Content-Type" : "application/json"
        },
        "body": JSON.stringify(body)

    }

    let response = await fetch(api,options);

    if (response.ok) {
        let admin = await response.json();
        localStorage.setItem("admin", JSON.stringify(admin));
        window.location.href="http://localhost:8080/api/v1/dev_updating/admin/homepage";

    } else {
        let error = await response.json();
        console.log(error);
    }
}