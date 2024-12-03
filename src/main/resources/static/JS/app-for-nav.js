function displayLanguages(languages) {
    let $ = document.querySelector.bind(document);

//    get parent node
    let languageContainer = $(".nav-course");

    // to active first course when the first time move to show courses of language

    for (let language of languages) {
        let liElement = document.createElement("li");
        liElement.classList.add("nav-course_item");
        liElement.setAttribute("data-id", language.id);

        let aElement = document.createElement("a");
        aElement.textContent = language.name;

        liElement.appendChild(aElement);
        languageContainer.appendChild(liElement);

        // set event for the language
        liElement.onclick = function() {

            let languagesNode = document.querySelectorAll(".nav-course_item");
            for (let languagesNodeItem of languagesNode) {
                languagesNodeItem.style = "background-color:transparent;";
                languagesNodeItem.querySelector("a").style = "color:white";
            }

            // display all courses of languages
            window.location.href = "http://localhost:8080/api/v1/dev_updating/course/show_course/" + language.id;
        }
    }

    // active first language
    let currentUrl = window.location.href;
    var url = window.location.pathname;
    var segments = url.split('/');
    var id = segments[segments.length - 1];

    // inactive all languages item
    let languagesNode = document.querySelectorAll(".nav-course_item");
    for (let languagesNodeItem of languagesNode) {
        languagesNodeItem.style = "background-color:transparent;";
        languagesNodeItem.querySelector("a").style = "color:white";
    }

    // active current language
    for (let languagesNodeItem of languagesNode) {
       if (languagesNodeItem.getAttribute("data-id") == id) {
              languagesNodeItem.style = "background-color:white; border-radius:20px;";
               languagesNodeItem.querySelector("a").style = "color:black; font-weight:bold";
       }
    }
}

function showCourse(id) {

    // get user
    let user = localStorage.getItem("user");
    if (user != null) {
        user = JSON.parse(user);
        let api = "http://localhost:8080/api/v1/dev_updating/course/process_show_course?"+"course_id="+id+"&"+"user_id="+user.id;
        window.location.href = api;

//        let api = "http://localhost:8080/api/v1/dev_updating/course/show_course/" + id;
//
//        fetch(api)
//            .then(() => {
//                alert("Hiển thị thành công")
//            })

    } else{
        alert("Vui lòng đăng nhập trước khi vào khóa học!!");
    }
}


async function getLanguages  () {

    let api = "/api/v1/dev_updating/language/get_all_languages";
    let languagesObj = await fetch(api);

    return await languagesObj.json();
}

async function displayCourses(id) {
//    let api = "/api/v1/dev_updating/course/" + id;
//    let coursesObj = await fetch(api);
//    let courses = await coursesObj.json();
//
//    // check if course null
//    if (courses.length < 1) {
//        document.querySelector("#message").textContent = "Chưa có bất kì khóa học nào!!";
//    } else {
//        document.querySelector("#message").textContent = "";
//    }


//    let courseWrap = $(".course");


// generate html code from course data
//    let html = "";
//
//    for (let courseItem of courses) {
//        html += `<div class="course-item" onclick="showCourse(${courseItem.id})">
//                    <div class="course-item_body">
//                        <img src="/images/frontend/bootstrap.jpeg" class="course-item_image">
//                        <h4 class="course-item_title">${courseItem.course_name}</h4>
//                        <p class="course-item_short_description">${courseItem.short_description}</p>
//                        <div class="course-item-footer">
//                            <div class="course-item-footer-user">
//                                <img src="/images/user.png" class="course-item-footer-user_image">
//                                <span class="course-item-footer-user_quantity">${courseItem.quantity_of_user}</span>
//                            </div>
//
//                            <p class="course-item-footer_price">${courseItem.price} VND</p>
//                        </div>
//                    </div>
//                 </div>`;
//    }

//    add html to course wrap
//    courseWrap.innerHTML = html;
}
