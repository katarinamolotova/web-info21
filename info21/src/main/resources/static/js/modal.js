function showModal(a) {
  const modal = document.getElementById('del_modal'+arguments[0]);
  modal.style.display = "block";
}

function closeModal(a) {
  const modal = document.getElementById('del_modal'+arguments[0]);
  modal.style.display = "none";
}