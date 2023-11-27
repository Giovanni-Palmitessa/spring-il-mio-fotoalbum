document
  .getElementById("contactForm")
  .addEventListener("submit", async function (event) {
    event.preventDefault();

    const name = document.getElementById("name").value;
    const email = document.getElementById("email").value;
    const message = document.getElementById("message").value;
    try {
      const response = await axios.post("http://localhost:8080/api/contacts", {
        name: name,
        email: email,
        message: message,
      });

      const successMessage = "Messaggio inviato con successo!";
      window.location.href = `index.html?message=${encodeURIComponent(
        successMessage
      )}`;
    } catch (error) {
      console.error("Errore nell'invio del messaggio:", error);
      const errorMessage = "Errore nell'invio del messaggio.";
      window.location.href = `index.html?error=${encodeURIComponent(
        errorMessage
      )}`;
    }
  });
