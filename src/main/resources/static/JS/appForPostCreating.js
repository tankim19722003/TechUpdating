
let $ = document.querySelector.bind(document);
let $$ = document.querySelectorAll.bind(document);
let counter = 0;
//  <div class="content-part_item">
//    <div class="content-part_item-header">
//        <input type="text" class="content-part_item-header_title" placeholder="Tiêu đề đoạn">
//        <input type="file" class="content-part_item-header_image">
//    </div>
//    <textarea class="content-part_item-para"></textarea>
//   </div>

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

// creating post
let btnCreatingPost = $(".creating-post-btn-js");

btnCreatingPost.onclick = () => {
//    let image = $(".content-part_item-header_image");
//
//    let file =image.files[0];
//    console.log(file);

        let postTitle = $(".creating-wrap_body-title").value;
        let shortDescription = $(".creating-wrap_body-short-description").value;

        let paraTitle = $(".content-part_item-header_title").value;
        let content = tinymce.get("content-part_item-para").getContent();

        console.log(postTitle);
        console.log(shortDescription);
        console.log(paraTitle);
        console.log(content);

}

// function to submit post content to server
const createBtn = $(".creating-post-btn-js");


createBtn.onclick = () => {
        let post = {};
//    let contents = [
//    ];
//    for (let i = 0; i <= counter; i++) {
//        let selector = "content-part_item-para_" + i;
//        console.log(selector);
//        let contentItem = tinymce.get(selector).getContent();
//        contents.push(contentItem);
//    }
//
//    console.log(contents);

    // get title value
    let titleValue = $(".creating-wrap_body-title").value;
    post.title = titleValue;

    // get short description
    let shortDescriptionValue = $(".creating-wrap_body-short-description").value;
    post.short_description = shortDescriptionValue;

    // get part
    let contentItems = $$(".content-part_item");

    let index = 0;
    post.part = [];

    for(let contentItem of contentItems) {

       let part = getPart(contentItem, index);
       post.part.push(part);
        index++;
    }

    createPost(post);

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

    // append course id
    formData.append("courseId", 1);

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
