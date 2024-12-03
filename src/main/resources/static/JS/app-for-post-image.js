const apiImage = "http://localhost:8080/api/v1/dev_updating/image/";

window.onload = function() {

    // save admin to localstorage
    let localAdminSaving = localStorage.getItem("admin");
    if (localAdminSaving == null) {
        alert("Vui lòng đăng nhập!!");
        window.location.href = "http://localhost:8080/api/v1/dev_updating/admin/show_login_form";
    }

    // add language
    displayLanguageToLanguageSelect();

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

        postGroupSelect.onchange = function() {
            displayPostSelect(this.value);
        }
    }

}

async function displayPostSelect(id) {
    let api = "http://localhost:8080/api/v1/dev_updating/post/get_id_title_post/" + id;

    let response = await fetch(api);

    if (response.ok) {

        // post include 2 fields: id and title
        let posts = await response.json();

        // display posts to select
        let postSelect = document.querySelector("#post-select");

        let html = postSelect.innerHTML;
        for (let post of posts) {
            html +=  `<option class="post-image-catalog-item-select_option" value=${post.id}>${post.title}</option>`
        }

        postSelect.innerHTML = html;

        postSelect.onchange = function() {
            handleDisplayPostContentByPostId(this.value);
        }
    }
}


async function handleDisplayPostContentByPostId(id) {

    let api = "http://localhost:8080/api/v1/dev_updating/post/" + id;

    let response = await fetch(api);

    if(response.ok) {

        let post = await response.json();

        // show thumnail
        const thumbnailWrap = document.getElementById("post-body-header-image-adding");
        let imgSrc = apiImage + 'uploads/upload.png';

        if (post.thumbnail) {
            imgSrc = apiImage + 'thumbnails/' + post.thumbnail;
            thumbnailWrap.style.width = "width: 400px";
        }

        showImage(thumbnailWrap, imgSrc);

        // display header or the post content

        // --------------show header-----------
        // hide the opening catalog message
        document.querySelector("#message-before-show-post").style.display="none";

        let postDetail = document.querySelector("#post-body-header");
        postDetail.style.display = "block";

        // add header content
        document.querySelector("#post-body-header_title").textContent = post.title;

        // add short description content
        document.querySelector("#post-body-header-short-description_content").innerHTML = post.short_description;


        //---------- end display header --------------

        // ------- display body content ---------------

        // add 
        // show the content container
        let contentList =  document.getElementById("post-body-content");
        contentList.style.display = "block";
        contentList.innerHTML = '';
        let bodyContentHTML = '';
        let parts = post.parts;

        for (let part of parts) {

            let contentItem = document.createElement("div");
            contentItem.setAttribute("data-id" , part.id);
            contentItem.classList.add("post-body-content-item");

            // create title element to content part
            let titleDiv = document.createElement("div");
            titleDiv.classList.add("post-body-content-item_title", "title");
            titleDiv.textContent = part.title;
            contentItem.appendChild(titleDiv);

            // image element
            let imageHTMl;
            const imageQuantity = part.imageResponses.length;
            if (imageQuantity > 0) {

//                    imageHTMl = `<div class="post-body-content-image-wrap">
//                                       <div class="post-body-content-image-wrap-center">
//                                           <img
//                                                src= "${apiImage}uploads/close.png"
//                                                class="post-body-content_close">
//                                           <img
//                                                data-image-id= imageResponses[0].id;
//                                                src="${apiImage}image_part/${part.imageResponses[0].url_image}"
//                                                class="post-body-content-image"
//                                                alt="Ảnh thể hiện nội dung của đoạn văn"/>
//                                       </div>
//                                   </div>`;
                    const imageUrl = "http://localhost:8080/api/v1/dev_updating/image/image_part/" + part.imageResponses[0].url_image;
                    imageHTMl = getHTMLImageDisplay(
                                {
                                    imageUrl: imageUrl,
                                    partId: part.id,
                                    imageQuantity: imageQuantity,
                                    imageId: part.imageResponses[0].id
                                });

            } else {
                const imageUrl = "http://localhost:8080/api/v1/dev_updating/image/uploads/upload.png";
                imageHTMl = getHTMLImageDisplay({
                                                    imageUrl: imageUrl,
                                                    partId: part.id,
                                                });
            }

          contentItem.innerHTML += imageHTMl;

            // add para's content
            let p = document.createElement("p");
            p.classList.add("content-style")
            p.classList.add("post-body-content-body");
            p.innerHTML = part.content;
            contentItem.appendChild(p);

            contentList.appendChild(contentItem);
        }


    }
}

function showImage(imageContainer, imgSrc) {
    imageContainer.src = imgSrc;
}

function handleShowUpLoadImageForm(partId) {
    document.getElementById("modal").showModal();

    // add event for the image saving button
    document.getElementById("save-image").onclick = () => {
        const api = "http://localhost:8080/api/v1/dev_updating/image/part_image?part_id=" + partId;
        const container = document.querySelector(`[data-id="${partId}"]`);
//        container.style = style;
        saveImage(api, partId);
    }
}



// handle for display image input
const imageInput = document.getElementById('modal-input');
const imagePreview = document.getElementById('modal-image-upload');

// Add an event listener to handle file selection
imageInput.addEventListener('change', function(event) {
     const file = event.target.files[0]; // Get the selected file

     if (file) {
         // Create a URL for the file
         const imageUrl = URL.createObjectURL(file);

         // Set the src of the image tag to the file URL
         imagePreview.src = imageUrl;
         imagePreview.style.display = 'block'; // Make the image visible
     }
});

// handle close modal
function closeModal(modal) {
    document.getElementById("modal").close();

    // reset image to upload image if user does not save image
    imagePreview.src = "http://localhost:8080/api/v1/dev_updating/image/uploads/upload.png";
    imageInput.value = "";
}

// handle save image to server
async function saveImage(api, partId) {

    // get image
    const imageInput = document.getElementById('modal-input');
    const file = imageInput.files[0];
     if (!file) {
        // use sweet alert to make it better
        alert("Vui lòng chọn ảnh trước khi lưu");
        return;
     } else {

        // call api to save it

        // reload page
        const formData = new FormData();
        formData.append("file", file); // Key must match your backend's parameter name

        // Call the API
        const response = await fetch(api, {
            method: "POST",
            body: formData,
        });

        if (response.ok) {
            const image = await response.json();

            showMessage("Lưu ảnh thành công!!");

            // close modal
            document.getElementById("modal").close();

            // display image just adding

            // get image html to render
            const imageUrl = apiImage + "image_part/" + image.url_image;
            const html = `
                <div class="post-body-content-image-wrap-center">
                      <img
                           src= "${apiImage}uploads/close.png"
                           class="post-body-content_close">
                      <img
                           data-image-id = ${partId};
                           src="${imageUrl}"
                           class="post-body-content-image"
                           alt="Ảnh thể hiện nội dung của đoạn văn"/>
                  </div>
            `;

            // append image just adding to container
            const part = document.querySelector(`[data-id="${partId}"]`);
            const imageContainer = part.querySelector(".post-body-content-image-wrap");
            imageContainer.innerHTML = html;


        } else {
            showMessage("Lưu ảnh thất bại. Vui lòng thử lại!!");
        }
     }
}

// use sweet alert to make it better
function showMessage(message) {
    alert(message);
}

// display image
function getHTMLImageDisplay({imageUrl, partId, imageQuantity, imageId }) {

    let html = `
    <div class="post-body-content-image-wrap">
                <img
                   src= ${imageUrl}
                   class="post-body-content-image"
                   alt="Ảnh thể hiện nội dung của đoạn văn"
                   onclick="handleShowUpLoadImageForm(${partId})"/>
    </div>`;

    if (imageQuantity) {
        html = `<div class="post-body-content-image-wrap">
                      <div class="post-body-content-image-wrap-center">
                          <img
                               src= "${apiImage}uploads/close.png"
                               class="post-body-content_close"
                               onclick="removeImage(${partId})">
                          <img
                               data-image-id = ${imageId};
                               src="${imageUrl}"
                               class="post-body-content-image"
                               alt="Ảnh thể hiện nội dung của đoạn văn"/>
                      </div>
                  </div>`
    }

    return html;
}