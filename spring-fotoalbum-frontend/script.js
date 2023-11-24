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
