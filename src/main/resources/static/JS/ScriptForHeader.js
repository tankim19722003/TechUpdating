function start() {

    // check user login
    let userObj = localStorage.getItem("user");


    if (userObj != null) {

        //get user
        let user = JSON.parse(userObj);
        let avatarName = (user.avatar === '')?"user.png":user.avatar;

        let src = "http://localhost:8080/api/v1/dev_updating/image/avatar/" + avatarName;
        // hide login button
        let html = ` <div class="header-right-auth-user">
                        <img src="${src}" class="header-right-auth-user_image"/>
                        <div class="header-right-auth-user_wrap">
                            <span class="header-right-auth-user_account" style="margin:0 4px;font-size: 1.1rem;font-family: monospace;">${user.account}</span>
                            <img src="/Images/down.png" class="header-right-auth-user_down" onclick ="showAuthOption(event)"/>
                        </div>

                        <!-- login controller          -->
                        <ul class="header-right-auth-list">
                            <li class="header-right-auth-list-item">
                                <a class="header-right-auth-list-item_link" href = "http://localhost:8080/api/v1/dev_updating/show-user-info-page/${user.id}">Chỉnh sửa thông tin </a>
                            </li>

                            <li class="header-right-auth-list-item">
                                <a class="header-right-auth-list-item_link">Khóa học đăng kí </a>
                            </li>

                            <hr/>
                            <li class="header-right-auth-list-item">
                                <a class="header-right-auth-list-item_link" style="color:red; font-weight:bold" onclick="logout()" >Đăng xuất</a>
                            </li>
                        </ul>
                    </div>`;

        // get User info wrap to display
        let authWrap = document.querySelector(".header-right-auth");
        authWrap.innerHTML = html;

        document.querySelector(".header-right-auth-user").style = "display:flex";

    }

    // add event when click out side auth box have to close it if it is displayed
    document.onclick = function() {
        let authListOption = document.querySelector(".header-right-auth-list");
        if (authListOption != null && authListOption.classList.contains("show-auth-list")) authListOption.classList.remove("show-auth-list");
    }
}

start();

function showAuthOption(e) {
    e.stopPropagation();
    document.querySelector(".header-right-auth-list").classList.toggle("show-auth-list");
}

function logout() {
    console.log("Process here!")
    localStorage.removeItem("user");
    // call api to logout

    window.location.href = "http://localhost:8080/api/v1/dev_updating/login";
}
