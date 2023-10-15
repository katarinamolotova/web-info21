function showModal(a) {
  const modal = document.getElementById(arguments[0]);
  modal.style.display = "block";
}

function closeModal(a, b) {
  const modal = document.getElementById(arguments[0]);
  modal.style.display = "none";
  window.location.replace("/data/" + arguments[1]);
}