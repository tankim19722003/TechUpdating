let $ = document.querySelector.bind(document);
window.onload = async() => {
    let languages = await getLanguages();

// display languages for nav
    displayLanguages(languages);
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

async function getLanguages  () {

    let api = "/api/v1/dev_updating/language/get_all_languages";
    let languagesObj = await fetch(api);

    return await languagesObj.json();
}