function openEditModal() {
    document.getElementById("overlay").classList.remove("hidden");
}

function closeOverlay() {
    document.getElementById("overlay").classList.add("hidden");
}

function validatePasswordMatch(){
    const password = document.getElementById("password").value
    const passwordcheck = document.getElementById("passwordcheck").value
    const error = document.getElementById("error");

    if(password !== passwordcheck){
        error.textContent = "kodeordene matcher ikke"
        return false
    }
    return true;
}

function deleteProfile(){
    return confirm("Er du sikker på du vil slette din profil?");
}
