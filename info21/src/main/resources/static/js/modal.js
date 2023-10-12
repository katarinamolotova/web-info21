function showModal() {
  const modal = document.getElementById('myModal');
  modal.style.display = "block";
}

function closeModal() {
  const modal = document.getElementById('myModal');
  modal.style.display = "none";
}

window.onclick = function(event) {
  const modal = document.getElementById('myModal');
  if (event.target == modal) {
    modal.style.display = "none";
  }
}