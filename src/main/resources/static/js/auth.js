function adminOnlyVisibility() {
    if (localStorage.getItem("userRole") === "ROLE_ADMIN") {
        document.querySelectorAll(".admin-div").forEach(el => {
            el.style.display = "block";
        });
        document.querySelectorAll(".admin-only").forEach(el => {
            el.style.display = "block";
        });
    }
}

adminOnlyVisibility();

document.addEventListener("click", (e) => {
    const dropdown = document.getElementById("user-dropdown");
    if (e.target.closest("#burger-menu-icon")) {
        dropdown.classList.toggle("show");
        return;
    }
    if (dropdown && !e.target.closest("#user-dropdown")) {
        dropdown.classList.remove("show");
    }
});

const menuLogout = document.getElementById("menu-logout");
const menuPromote = document.getElementById("menu-promote");

menuLogout.addEventListener("click", () => {
    localStorage.clear();
    window.location.href = "index.html";
});

menuPromote.addEventListener("click", async () => {
    window.location.href="promoteToAdmin.html";
});

