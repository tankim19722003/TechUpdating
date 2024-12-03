
let $ = document.querySelector.bind(document);

window.onload = async() => {

    let languages = await getLanguages();

   if (languages.length > 0) {
    let languageId = languages[0].id;

    // display language
    displayLanguages(languages);

    //    display all course of the first language
//    displayCourses(languageId);

   } else {
        alert("Chưa có bất kì khóa học nào!!");
   }


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



