document
  .getElementById("contactForm")
  .addEventListener("submit", async function (event) {
    event.preventDefault();

    const name = document.getElementById("name").value;
    const email = document.getElementById("email").value;
    const message = document.getElementById("message").value;
    axios
      .post("http://localhost:8080/api/contacts", {
        name: name,
        email: email,
        message: message,
      })
      .then(function (response) {
        console.log("Messaggio inviato:", response);
        alert("Messaggio inviato con successo!");
      })
      .catch(function (error) {
        console.error("Errore nell'invio del messaggio:", error);
        alert("Errore nell'invio del messaggio.");
      });
  });
