
let $ = document.querySelector.bind(document);

window.onload = async() => {

    let languages = await getLanguages();

// display languages for nav
    displayLanguages(languages);

//    display course relate to language
    let languageId = 1;
    displayCourses(languageId);

}


// <div class="course-item">
//   <div class="course-item_body">
//       <img th:src="@{/images/frontend/bootstrap.jpeg}" class="course-item_image">
//       <h4 class="course-item_title">Java core</h4>
//       <p class="course-item_short_description">Khóa học cung cấp kiến thức cơ bản về lập trình java </p>
//       <div class="course-item-footer">
//           <div class="course-item-footer-user">
//               <img th:src="@{/images/user.png}" class="course-item-footer-user_image">
//               <span class="course-item-footer-user_quantity">20500</span>
//           </div>
//
//           <p class="course-item-footer_price">Free</p>
//       </div>
//       <div class="course-register-wrap">
//           <a href="#" class="course-register-wrap_btn">Đăng kí</a>
//       </div>
//   </div>
//</div>
async function displayCourses(id) {

    let api = "/api/v1/dev_updating/course/" + id;
    let coursesObj = await fetch(api);
    let courses = await coursesObj.json();

    // check if course null
    


    let courseWrap = $(".course");


// generate html code from course data
    let html = "";

    for (let courseItem of courses) {
        html += `<div class="course-item" onclick="showCourse(${courseItem.id})">
                    <div class="course-item_body">
                        <img src="/images/frontend/bootstrap.jpeg" class="course-item_image">
                        <h4 class="course-item_title">${courseItem.course_name}</h4>
                        <p class="course-item_short_description">${courseItem.short_description}</p>
                        <div class="course-item-footer">
                            <div class="course-item-footer-user">
                                <img src="/images/user.png" class="course-item-footer-user_image">
                                <span class="course-item-footer-user_quantity">${courseItem.quantity_of_user}</span>
                            </div>

                            <p class="course-item-footer_price">${courseItem.price}</p>
                        </div>
                    </div>
                 </div>`;
    }

//    add html to course wrap
    courseWrap.innerHTML = html;

}


async function getLanguages  () {

    let api = "/api/v1/dev_updating/language/get_all_languages";
    let languagesObj = await fetch(api);

    return await languagesObj.json();
}

function displayLanguages(languages) {

//    get parent node
    let languagueContainter = $(".nav-course");

    for (let language of languages) {
        let liElement = document.createElement("li");

        liElement.classList.add("nav-course_item");
        liElement.textContent = language.name;
        languagueContainter.appendChild(liElement);
    }
}

function showCourse(id) {

    // api
    let api = "http://localhost:8080/api/v1/dev_updating/course/process_show_lesson?"+"course_id="+id+"&"+"user_id=1";
    window.location.href = api;


}
