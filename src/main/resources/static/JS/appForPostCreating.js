
let $ = document.querySelector.bind(document);
let $$ = document.querySelectorAll.bind(document);

//  <div class="content-part_item">
//    <div class="content-part_item-header">
//        <input type="text" class="content-part_item-header_title" placeholder="Tiêu đề đoạn">
//        <input type="file" class="content-part_item-header_image">
//    </div>
//    <textarea class="content-part_item-para"></textarea>
//   </div>

let btnAddPara = $(".creating-post-btn_add-para");

btnAddPara.onclick = () => {
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
    textAreaContent.classList.add("content-part_item-para");

    // add header and content to wrap
    divParaItem.appendChild(divWrapHeader);
    divParaItem.appendChild(textAreaContent);

    paraList.appendChild(divParaItem);

     tinymce.init({
            selector: '.content-part_item-para', // Target the last added textarea
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