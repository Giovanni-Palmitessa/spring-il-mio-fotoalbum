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
    <div class="card" style="width: 15rem;">
      <img src="${element.imageUrl}" class="card-img-top" alt="${
    element.title
  }">
      <div class="card-body">
        <h5 class="card-title">${element.title}</h5>
        <p class="card-text">${element.description}</p>
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
  let content = '<div class="row">';
  data.forEach((element) => {
    content += `<div class="col-4 mt-3">${renderPhoto(element)}</div>`;
  });
  content += "</div>";
  root.innerHTML = content;
};

// Esegui la funzione per ottenere l'elenco delle foto all'avvio
getPhoto();
