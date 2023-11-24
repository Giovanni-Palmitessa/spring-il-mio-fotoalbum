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
      }" class="card-img-top" alt="Foto Image" style="height: 300px">
      <div class="card-body">
        <h5 class="card-title">${element.title}</h5>
        <p class="card-text">${element.description}</p>
      </div>
      <div class="card-body">
          ${renderCategories(element.categories)}
      </div>
    </div>`;
};

// funzione che renderizza la gallery di card
const renderFotoList = (data) => {
  let content;
  if (data.length > 0) {
    // creo la gallery
    content = '<div class="row">';
    // itero sull'array di foto
    data.forEach((element) => {
      content += '<div class="col-3">';
      // chiamo il metodo che restituisce la card della foto
      content += renderFoto(element);
      content += "</div>";
    });
    content += "</div>";
  } else {
    content = '<div class="alert alert-info">La lista è vuota</div>';
  }
  // sostituisco il contenuto di root con il mio content
  root.innerHTML = content;
};

// funzione che chiama l'api e ottiene il json con l'array di books
const getBooks = async () => {
  try {
    // ottengo la response dell'api
    const response = await axios.get(apiUrl);
    // passo i dati alla funzione che li renderizza
    renderBookList(response.data);
  } catch (error) {
    console.log(error);
  }
};

/* codice globale che viene eseguito al load dello script */
getBooks();
