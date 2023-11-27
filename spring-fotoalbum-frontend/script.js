const baseUrl = "http://localhost:8080/api/fotos";
const root = document.getElementById("root");

// Funzione per ottenere l'array delle foto
const getPhoto = async (searchName = "") => {
  try {
    const url = `${baseUrl}?search=${searchName}&visible=true`;
    const response = await axios.get(url);
    if (Array.isArray(response.data)) {
      renderPhotoList(response.data);
    } else {
      console.error("Data format is incorrect:", response.data);
    }
  } catch (error) {
    console.error("Error in getPhoto:", error);
  }
};

// Funzione per la ricerca
const searchPhoto = () => {
  const searchName = document.getElementById("searchNameField").value;
  getPhoto(searchName);
};

// Funzione per renderizzare ogni foto nell'elenco
const renderPhoto = (element) => {
  return `
    <div class="card" style="width: 500px">
      <img src="${
        element.imageUrl
      }" class="card-img-top" style="height:300px" alt="${element.title}">
      <div class="card-body">
        <div style="height:150px">
          <h5 class="card-title">${element.title}</h5>
          <p class="card-text">${element.description}</p>
        </div>
        <div class="card-footer">${renderCategory(element.categories)}</div>
        </div>
      </div>`;
};

const renderCategory = (categories) => {
  let content;
  if (!categories || categories.length === 0) {
    content = "No categories";
  } else {
    content =
      '<h3 class="text-danger">Categories</h3><ul class="list-unstyled">';
    categories.forEach((category) => {
      content += `<li>${category.name}</li>`;
    });
    content += "</ul>";
  }
  return content;
};

// Funzione per renderizzare l'elenco delle foto
const renderPhotoList = (data) => {
  let content = '<div class="d-flex justify-content-between flex-wrap">';
  data.forEach((element) => {
    content += `<div class="my-5">${renderPhoto(element)}</div>`;
  });
  content += "</div>";
  root.innerHTML = content;
};

// Esegui la funzione per ottenere l'elenco delle foto all'avvio
getPhoto();

// Leggi la query string e mostra il messaggio
const urlParams = new URLSearchParams(window.location.search);
const message = urlParams.get("message");
const error = urlParams.get("error");

if (message) {
  // Visualizza un messaggio di successo
  alert(message);
} else if (error) {
  // Visualizza un messaggio di errore
  alert(error);
}
