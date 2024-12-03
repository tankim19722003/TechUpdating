
window.onload = async function () {
    // show all comment
    let postId = document.querySelector("#post_id").value;
    handleShowComments(postId);

    document.addEventListener('click', function(event) {
        event.stopPropagation();
        closeMoreOptions();
          console.log("Hello");
    });

    // display languages
    let languages = await getLanguages();
    displayLanguages(languages);

}


// Event saving new comment
let commentBtn = document.querySelector(".comment_title-form_btn");
if (commentBtn != null) {
    commentBtn.onclick = () => {

        // post id
        let postId = document.querySelector("#post_id").value;

        // user id
        // need to load from localhost after login
        // get user from local host
        // check if user login
        let user = localStorage.getItem("user");
        if (user == null) {
            alert("Vui Lòng đăng nhập!!");
            return;
        }
        let userId = user.id;

        // content
        let content = document.querySelector(".comment_title-form_text-area").value;

        // check content empty
        if (content === '') {
            alert("Vui lòng nhập nội dung comment!!");
            return;
        }

        console.log("content: " + content);

        // save comment
        let response = saveComment(userId, postId, content)
                        .then(commentItem => {
                            // set input empty
                            document.querySelector(".comment_title-form_text-area").value = "";

                            // show node saving
                                     let html = `
                                            <div class="comment-list-item" id = "comment-id-${commentItem.id}">
                                                <div class="comment-list-item-header">
                                                    <img src="https://i.vietgiaitri.com/2023/10/26/sao-tre-dt-viet-nam-khoe-anh-cuoi-voi-ban-gai-hon-tuoi-xinh-nhu-mong-3c9-7012606.jpg" class="comment-list-item-header_user-img">
                                                </div>
                                                <div class="comment-list-item-body">
                                                    <div class="comment-list-item-body_info">
                                                        <span class="comment-list-item-body_info-fullname">${commentItem.userResponse.fullname}</span>

                                                        <span class="comment-list-item-body_info-date">
                                                            0 ngày trước
                                                        </span>

                                                    </div>

                                                    <form class="comment-list-item-body_content">
                                                        <p class="comment-list-item-body_content_input_value" value = '${commentItem.content}' data-input-comment-value="${commentItem.id}" >${commentItem.content}</p>
                                                        <input type="text" class="comment-list-item-body_content_input" value = '${commentItem.content}' data-input-comment="${commentItem.id}"/>
                                                        <div class="comment-list-item-body_content-btns" data-control-update-id=${commentItem.id}>
                                                            <button class="comment-list-item-body_content-btns_cancle btn background-none"  onclick="hideUpdateComment(${commentItem.id})"  type="button">Hủy</button>
                                                            <button class="comment-list-item-body_content-btns_saving btn" onclick="updateComment(${commentItem.id})" type="button">Lưu</button>
                                                        </div>
                                                    </form>
                                                </div>
                                                <div class="comment-list-item-more-option">
                                                   <img class="comment-list-item-more-option_image" src="/images/more.png"/ data-comment-id="${commentItem.id}">

                                                   <ul class="comment-list-item-more-option-list"  data-list-option-id = ${commentItem.id}>
                                                       <li class="comment-list-item-more-option-list_item-li"  onclick='showFormUpdate(${commentItem.id})'>
                                                           <img class="comment-list-item-more-option-list_item-li_image" src="/images/modify.png">
                                                           <a class="comment-list-item-more-option-list_item-li_a">Chỉnh sửa</a>
                                                       </li>

                                                       <li class="comment-list-item-more-option-list_item-li" onclick='removeComment(${commentItem.id})'>
                                                           <img class="comment-list-item-more-option-list_item-li_image" src="/images/remove.png">
                                                           <a class="comment-list-item-more-option-list_item-li_a" data-remove-comment-id="${commentItem.id}">Xóa</a>
                                                       </li>

                                                   </ul>
                                               </div>
                                          </div>`;


                            // handle insert comment to first node
                            document.querySelector(".comment-list").insertAdjacentHTML('afterbegin', html);

                            // add event to new comment
                          // 2. Add event listener after inserting the HTML
                             const newComment = document.querySelector(`[data-comment-id='${commentItem.id}']`);

                             // get element want to show option
                             const optionList =  document.querySelector(`[data-list-option-id='${commentItem.id}']`);

                             newComment.onclick = function (e) {
                                showCommentOptionList(optionList);
                                e.stopPropagation()
                             }

                             // add remove event to new comment node
                             let removeCommentItem = document.querySelector(`[data-remove-comment-id='${commentItem.id}']`);
                             removeCommentItem.onclick = function() {
                                removeComment(`${commentItem.id}`);
                             }

                        })
                      .catch(error => {
                            alert(error.message);
                        });

    }
}


async function saveComment(userId, postId, content) {

    try {
        let url =  '/api/v1/dev_updating/comment/create_comment';

            let payload = {
                'user_id': userId,
                'post_id': postId,
                'content': content
            };

            // Make the POST request
           const options = {
               method: 'POST', // Use the POST method
               headers: {
                   'Content-Type': 'application/json' // Set the content type to JSON
               },
               body: JSON.stringify(payload) // Convert the payload to a JSON string
           };


            const response = await fetch(url, options);

             if (!response.ok) {
                   return response.json().then(errorMessages => {
                       throw new Error(errorMessages.join(', '));
                   });
               }

            // Parse the JSON response
            const commentResponse = await response.json();

            return commentResponse;
    } catch(error) {
          const errorMessageString = error.message; // This is now a string of error messages
          throw errorMessageString;
    }

}

async function handleShowComments(postId) {
    let commentList = document.querySelector(".comment-list");
    let api = "http://localhost:8080/api/v1/dev_updating/comment/find_all_comments/" + postId;

    let response = await fetch(api);

    let commentResponses = await response.json();

    for (let commentItem of commentResponses) {
        let days = commentItem.days_difference;
        let months = commentItem.months_difference;
        let years = commentItem.years_difference;

        let createdAtText = '';
        if (years > 0 ) createdAtText = years + ' năm trước';
        else if (years < 1 && months > 0) createdAtText = months + ' tháng trước';
        else if (years < 1 && months < 1) createdAtText = days + ' ngày trước';

        let html = `
                    <div class="comment-list-item" id = "comment-id-${commentItem.id}">
                        <div class="comment-list-item-header">
                            <img src="https://i.vietgiaitri.com/2023/10/26/sao-tre-dt-viet-nam-khoe-anh-cuoi-voi-ban-gai-hon-tuoi-xinh-nhu-mong-3c9-7012606.jpg" class="comment-list-item-header_user-img">
                        </div>
                        <div class="comment-list-item-body">
                            <div class="comment-list-item-body_info">
                                <span class="comment-list-item-body_info-fullname">${commentItem.userResponse.fullname}</span>

                                <span class="comment-list-item-body_info-date">
                                    ${createdAtText}
                                </span>

                            </div>

                            <form class="comment-list-item-body_content">
                                <p class="comment-list-item-body_content_input_value" value = '${commentItem.content}' data-input-comment-value="${commentItem.id}" >${commentItem.content}</p>
                                <input type="text" class="comment-list-item-body_content_input" value = '${commentItem.content}' data-input-comment="${commentItem.id}"/>
                                <div class="comment-list-item-body_content-btns" data-control-update-id=${commentItem.id}>
                                    <button class="comment-list-item-body_content-btns_cancle btn background-none" onclick="hideUpdateComment(${commentItem.id})" type="button">Hủy</button>
                                    <button class="comment-list-item-body_content-btns_saving btn" onclick="updateComment(${commentItem.id})" type="button" >Lưu</button>
                                </div>
                            </form>
                        </div>`;


        // handle more option
        // load use from local storage
        let userId = localStorage.getItem("userId");

        if (userId == commentItem.userResponse.id) {
            let moreOptionNode =`<div class="comment-list-item-more-option">
                               <img class="comment-list-item-more-option_image" src="/images/more.png"/>

                               <ul class="comment-list-item-more-option-list">
                                   <li class="comment-list-item-more-option-list_item-li"  onclick='showFormUpdate(${commentItem.id})'>
                                       <img class="comment-list-item-more-option-list_item-li_image" src="/images/modify.png">
                                       <a class="comment-list-item-more-option-list_item-li_a" >Chỉnh sửa</a>
                                   </li>

                                   <li class="comment-list-item-more-option-list_item-li" onclick='removeComment(${commentItem.id})'>
                                       <img class="comment-list-item-more-option-list_item-li_image" src="/images/remove.png">
                                       <a class="comment-list-item-more-option-list_item-li_a">Xóa</a>
                                   </li>

                               </ul>
                           </div>
                          </div>`;

            html += moreOptionNode;
        } else {

            // close item comment
            html += '</div>';
        }

        // add comment item to comment wrap
        commentList.innerHTML += html;
    }

     // show more options
    showMoreOptions();

    // handle more option
    handleMoreOption();


}

function showFormUpdate(commentId) {


    // show input comment
    const commentInputShowing = document.querySelector(`[data-input-comment-value="${commentId}"]`);
    // style for input
    commentInputShowing.classList.add("d-none");


    // hide comment value
      // show input comment
    const commentInput = document.querySelector(`[data-input-comment="${commentId}"]`);
    // style for input
    commentInput.classList.add("show-input-text");

    // show button controll update comment
    const commentUpdateControl = document.querySelector(`[data-control-update-id="${commentId}"]`);
    commentUpdateControl.classList.add("d-flex");

    // update comment
}

async function updateComment(commentId) {

    console.log("process here");
    const api = "http://localhost:8080/api/v1/dev_updating/comment/update_comment";

    // get user id from localhost after login
    const userId = localStorage.getItem("userId");
    if (userId == undefined) {
        alert("You have to login first!!");
        return;
    }

    // get value from input
    const commentInputValue = document.querySelector(`[data-input-comment="${commentId}"]`).value;

    console.log(commentInputValue);

    const body = {
        "id" : commentId,
        "user_id" : userId,
        "content" : commentInputValue
    }

  const options = {
      method: 'PUT', // Specify the HTTP method
      headers: {
          'Content-Type': 'application/json', // Set the content type to JSON
      },
      body: JSON.stringify(body) // Convert the payload to a JSON string
  };

  const response = await fetch(api, options);

  if (!response.ok) {
    alert("Can't save comment");
    window.location.reload();
    return;
  }

  const commentResponse = await response.json();

  // hide update form and button control
   hideUpdateComment(commentId);
}

// after click save or cancle we need to change input to text and hide button
function hideUpdateComment(commentId) {

      // show comment value
      const commentInputShowing = document.querySelector(`[data-input-comment-value="${commentId}"]`);
      commentInputShowing.classList.remove("d-none");


      // hide comment input
      const commentInput = document.querySelector(`[data-input-comment="${commentId}"]`);
      // style for input
      commentInput.classList.remove("show-input-text");
      commentInputShowing.innerHTML = commentInput.value;

       // hide button control update comment
       const commentUpdateControl = document.querySelector(`[data-control-update-id="${commentId}"]`);
       commentUpdateControl.classList.remove("d-flex");
}

async function removeComment(commentId) {
        let selector = '#comment-id-'+ commentId;
        document.querySelector(selector).remove();

        // call api to delete comment
        let api ='http://localhost:8080/api/v1/dev_updating/comment/' + commentId;
         const options = {
               method: 'DELETE', // Use the POST method
               headers: {
                   'Content-Type': 'application/json' // Set the content type to JSON
               }
          };
        await fetch(api, options);
}


function showMoreOptions() {
    let moreOptionBtns = document.querySelectorAll(".comment-list-item-more-option_image");
     for (let item of moreOptionBtns) {
            item.onclick =function(e) {

                    // showing item clicking
                let parent = e.currentTarget.closest(".comment-list-item-more-option");
                let optionCommentList = parent.querySelector(".comment-list-item-more-option-list");

                // show list comment's more option
                showCommentOptionList(optionCommentList);
                e.stopPropagation()

            }
        }
}


// handling show comment option list
function showCommentOptionList(listCommentOption) {
    console.log(listCommentOption);
    if (listCommentOption.classList.contains("show-more-option")) {
        listCommentOption.classList.remove("show-more-option");
    } else {
         closeMoreOptions();
        listCommentOption.classList.add("show-more-option");
        console.log(listCommentOption);
    }
}
//handling more comment option
function handleMoreOption() {

    let modifyBtn = document.querySelectorAll(".comment-list-item-more-option-list_item-li_a");
}

function closeMoreOptions() {
     // close comment options if it
      let moreOptionWrap = document.querySelectorAll(".comment-list-item-more-option-list");
        for (let moreOptionItem of moreOptionWrap) {
            moreOptionItem.classList.remove("show-more-option");
        }
}


