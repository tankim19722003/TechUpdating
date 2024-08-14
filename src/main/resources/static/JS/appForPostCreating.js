
let $ = document.querySelector.bind(document);
let $$ = document.querySelectorAll.bind(document);
let counter = 0;

window.onload = () => {

    showLanguageOption();

}

async function showLanguageOption() {

   let api = "/api/v1/dev_updating/admin/language/get_all_languages";
    try {
       let languagesObj = await fetch(api);

        let languages = await languagesObj.json();

        let selectLanguageWrap = $(".creating-wrap_body-category-language_select");

        for (let language of languages) {
            let option = document.createElement("option");
            option.value = language.id;
            option.text = language.name;
            selectLanguageWrap.appendChild(option);

            // set event for option
            option.onclick = () => {
                console.log("Hello");
            }
        }
    } catch (error) {
        console.error(error)
    }
}


// set event for display course
let selectLanguageWrap = $(".creating-wrap_body-category-language_select");

selectLanguageWrap.onchange = () => {
    let languageId = selectLanguageWrap.options[selectLanguageWrap.selectedIndex].value;

    console.log(languageId);
    // call api to display
    let courses = findCoursesByLanguageId(languageId);
}

async function findCoursesByLanguageId(languageId) {

    let url = "/api/v1/dev_updating/admin/course/" + languageId;

    let coursesObject = await fetch(url);
    let courses = await coursesObject.json();

    // append option to course select tag
    let courseSelectWrap = $(".creating-wrap_body-category-course_select");

   courseSelectWrap.innerHTML = "";
    for (let course of courses) {
        let option = document.createElement("option");
        option.value = course.id;
        option.text = course.course_name;

        // append course option to select
        courseSelectWrap.appendChild(option);
    }
}



let btnAddPara = $(".creating-post-btn_add-para");

btnAddPara.onclick = () => {

    // increase counter to set id for textarea tinymce
    counter++;

    let paraList = $(".content-part");

    // create para
    let divParaItem = document.createElement("div");
    divParaItem.classList.add("content-part_item");

    // create title and file element input
    let divWrapHeader = document.createElement("div");
    divWrapHeader.classList.add("content-part_item-header");

    // create title para
    let titleInput = document.createElement("input");
    titleInput.type = "text";
    titleInput.classList.add("content-part_item-header_title");
    titleInput.placeholder = "Tiêu đề đoạn";

    // create imput file
    let imageInput = document.createElement("input");
    imageInput.type = "file";
    imageInput.classList.add("content-part_item-header_image");

    // add title and image to parent
    divWrapHeader.appendChild(titleInput);
    divWrapHeader.appendChild(imageInput);

    // create content input
    let textAreaContent = document.createElement("textarea");
    textAreaContent.id = 'content-part_item-para_' + counter;

    // add header and content to wrap
    divParaItem.appendChild(divWrapHeader);
    divParaItem.appendChild(textAreaContent);

    paraList.appendChild(divParaItem);

     tinymce.init({
            selector: '#content-part_item-para_' + counter// Target the last added textarea
            // ... other TinyMCE options
        });
}

// function to submit post content to server
const createBtn = $(".creating-post-btn-js");


createBtn.onclick = () => {
        let post = {};

    // get title value
    let titleValue = $(".creating-wrap_body-title").value;

    // check is title empty
    if (titleValue == "") {
        alert("Vui lòng nhập tiêu đề bài viết!");
        return;
    }
    post.title = titleValue;

    // get short description
    let shortDescriptionValue = $(".creating-wrap_body-short-description").value;
    post.short_description = shortDescriptionValue;

    // get language
    let selectLanguageWrap = $(".creating-wrap_body-category-language_select");
    let languageId = selectLanguageWrap.options[selectLanguageWrap.selectedIndex];
    // check empty language Id
    if (languageId == null) {
        alert("Vui lòng chọn ngôn ngữ");
        return;
    }
    post.languageId = languageId.value;


    // get course
    let selectCourseWrap = $(".creating-wrap_body-category-course_select")
    let courseId = selectCourseWrap.options[selectCourseWrap.selectedIndex];
    if (courseId == null) {
        alert("Vui lòng chọn khóa học");
        return;
    }

    post.courseId = courseId.value

    // get part
    let contentItems = $$(".content-part_item");

    let index = 0;
    post.part = [];

    for(let contentItem of contentItems) {

       let part = getPart(contentItem, index);
       post.part.push(part);
        index++;
    }

//    createPost(post);

    console.log(post);

}

function getPart(parentNode, index) {

    let part = {};

    // get title of part
      let titlePart = parentNode.querySelector(".content-part_item-header_title").value;
      part.titlePart = titlePart;
    // get image of part
     const fileImage = parentNode.querySelector(".content-part_item-header_image");
     const file = fileImage.files[0];
     part.image = file;

    // get content of part
        let selector = "content-part_item-para_" + index;
        let contentItem = tinymce.get(selector).getContent();

        part.content = contentItem;
    return part;
}

async function createPost(post) {

     const formData = new FormData();

    // append title for post
    formData.append("title", post.title);

    // append short description
    console.log(post.shortDescription);
    formData.append("shortDescription", post.short_description);

    let parts = post.part;

    // append user
    formData.append("userId", 1);

    // append language id
    formData.append("languageId", post.languageId);

    // append course id
    formData.append("courseId", post.courseId);

    parts.forEach( (partItem, index) => {

        // append title part
        formData.append(`parts[${index}].titlePart`, partItem.titlePart);

        // append image
        console.log(partItem.image);
//        const imageFile = new File([partItem.image], `image${index}.jpg`, { type: 'image/jpeg' });
        formData.append(`parts[${index}].image`, partItem.image);

        // append content
        formData.append(`parts[${index}].content`, partItem.content);


    })


    await fetch("/api/v1/dev_updating/admin/post/create_post",{
        method: 'POST',
        body: formData
    });

    alert("Create post successully!");

}
