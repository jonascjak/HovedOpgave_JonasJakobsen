document.addEventListener("DOMContentLoaded", () => {

    document.querySelectorAll(".day-button").forEach(button => {
        button.addEventListener("click", () => {
            const date = button.dataset.date;
            openCreateEventModal(date);
        });
    });

    document.getElementById("overlay").addEventListener("click", (e) => {
        if (e.target.id === "overlay") {
            closeOverlay();
        }
    });
});


function openOverlay() {
    document.getElementById("overlay").classList.remove("hidden");
}

function closeOverlay() {
    document.getElementById("overlay").classList.add("hidden");
}


function openCreateEventModal(date) {
    document.getElementById("eventDateInput").value = date;
    openOverlay();
}

function openEditModal(){
    openOverlay()
}

function deleteEvent(){
    return confirm("Er du sikker på du vil slette eventet?");
}

