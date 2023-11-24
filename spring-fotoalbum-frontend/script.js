/* COSTANTI*/
const apiUrl = "http://localhost:8080/api/fotos";
const root = document.getElementById("root");

/* FUNZIONI */
// funzione che renderizza le categorie delle foto
const renderCategories = (categories) => {
  let content;
  //   controllo se categorie sono uguali a 0
  if (categories.length === 0) {
    content = "No categories";
  } else {
    // se categorie presenti mostro lista categorie
    content = '<ul class="list-unstyled">';
    categories.forEach((cat) => {
      content += `<li>${cat.name}</li>`;
    });
    content += "</ul>";
  }
  return content;
};

// funzione che renderizza la card della foto
const renderFoto = (element) => {
  return `<div class="card mb-5" style="width: 18rem;">
      <img src="${
        element.imageUrl
      }" class="card-img-top" alt="Pizza Image" style="height: 300px">
      <div class="card-body">
        <h5 class="card-title">${element.title}</h5>
        <p class="card-text">${element.description}</p>
      </div>
      <div class="card-body">
          ${renderCategories(element.categories)}
      </div>
    </div>`;
};
