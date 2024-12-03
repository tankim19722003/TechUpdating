window.onload = function() {

    // save admin to localstorage
    let localAdminSaving = localStorage.getItem("admin");
    if (localAdminSaving == null) {
        alert("Vui lòng đăng nhập!!");
        window.location.href = "http://localhost:8080/api/v1/dev_updating/admin/show_login_form";
    }

    // add language
    displayLanguageToLanguageSelect()


}

async function displayLanguageToLanguageSelect() {

    // get language to display
    let response = await fetch("http://localhost:8080/api/v1/dev_updating/language/get_all_languages");

    if (response.ok) {
        let languages = await response.json();

        // set value to language select
        displayOptionInLanguageSelect(languages);
    }

}

function displayOptionInLanguageSelect(languages) {
  let languageSelect = document.querySelector("#language-select");
        let html = '';
        for (let languageItem of languages) {
            html += `<option class="content-category-item-option" value="${languageItem.id}" class="language-option">${languageItem.name}</option>`;
        }

    languageSelect.innerHTML += html;

    // display course
    languageSelect.onchange = function() {
         displayCourseInCourseSelect(this.value);
    }
}


async function displayCourseInCourseSelect(id) {

    let response = await fetch("http://localhost:8080/api/v1/dev_updating/course/" + id);

    if (response.ok) {
        let courses = await response.json();
        let courseSelect = document.querySelector("#course-select");
        courseSelect.innerHTML = '<option class="content-category-item-option" selected disabled>Khóa học</option>';

        let html = '';
        for (let courseItem of courses) {
            html += `<option class="content-category-item-option" value =${courseItem.id}>${courseItem.course_name} </option>`;
        }

        courseSelect.innerHTML += html;

        // display post group
        courseSelect.onchange = function() {
             displayPostGroupOptions(this.value);
        }

    }

}

async function displayPostGroupOptions(id) {
    let response = await fetch("http://localhost:8080/api/v1/dev_updating/topic/get_all_post_groups/" + id);

    if (response.ok) {
        let postGroups = await response.json();

        // display post group to option
        let postGroupSelect = document.querySelector("#post-group-select");

        postGroupSelect.innerHTML = '<option class="content-category-item-option" selected disabled>Nhóm kiến thức</option>';
        let html = '';
        for (let postGroupItem of postGroups) {
            html += `<option class="content-category-item-option" value="${postGroupItem.id}">${postGroupItem.title_name}</option>`;
        }

        postGroupSelect.innerHTML += html;
    }

}
function addNewPara() {

    let contentList = document.querySelector("#post-content");

    let id = "para-content-" + Date.now();
    let html = `
         <div class="post-content-item">

            <div class="post-content-item-header">
                <h3 class="post-content-item-header_title">Tiêu đề đoạn</h3>
                <input class="post-content-item-header_input" placeholder="Tên tiêu đề"/>
            </div>

            <textarea id="${id}" placeholder="Tên tiêu đề" class="para-content"></textarea>

        </div>
    `;

     contentList.insertAdjacentHTML('beforeend', html);


    // initialize for tinymce
    let selector = "#" + id;
      tinymce.init({
        selector: selector,
         plugins: 'media',
         toolbar: 'media'
     });


    setTimeout(() => {
      window.scrollTo({
        top: document.body.scrollHeight,
        behavior: 'smooth' // Optional for smooth scrolling
      });
    }, 100);
}

async function createPost() {

    let postBody = getPostCreatingDTO();

    if (postBody == '') {
        alert("Vui lòng nhập đầy đủ thông tin!!");
        return;
    }

    // call api to save post
    // get user id
    let adminObj = localStorage.getItem("admin");
    let admin = JSON.parse(adminObj);

    let api = "http://localhost:8080/api/v1/dev_updating/post/create_post/" + admin.id;
    let options = {
        'method' : "POST",
        'headers' : {
            "Content-Type": 'application/json'
        },
        body: JSON.stringify(postBody)
    }

    let response = await fetch(api, options);

    if (response.ok) {
        alert("Tạo bài viết thành công!!");
        window.location.reload();
    } else {
        alert("Tạo bài viết thất bại!!");
    }

}

function getPostCreatingDTO() {

    let courseId = document.querySelector("#course-select").value;
    let postGroupId = document.querySelector("#post-group-select").value;
    let postTitle = document.querySelector("#post-header_input").value;
    let shortDescription = tinymce.get("post-short-description_area").getContent();
    let postContentItems = document.querySelectorAll(".post-content-item");
    let postContentBody = [];
    if (courseId == '' || postGroupId == '' || postTitle == '' || shortDescription == '') {
        return '';
    }

    // get body content of post
    for (let postContentItem of postContentItems) {

        // get title of para
        let paraTitle = postContentItem.querySelector(".post-content-item-header_input").value;

        // get body of para
        let paraId = postContentItem.querySelector(".para-content").getAttribute("id");
        let paraContent = tinymce.get(paraId).getContent();

        postContentBody.push(
            {
                "title": paraTitle,
                "content": paraContent
            }
        )

    }

    return  {
        "course_id": courseId,
        "topic_id": postGroupId,
        "content_post_creating": {
            "title" : postTitle,
            "short_description": shortDescription,
            "para_list": postContentBody
        }
    }
}