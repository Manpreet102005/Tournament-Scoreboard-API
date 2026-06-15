function adminOnlyVisibility() {
    if (localStorage.getItem("userRole") === "ROLE_ADMIN") {
        document.querySelectorAll(".admin-div").forEach(el => {
            el.style.display = "block";
        });
    }
}

adminOnlyVisibility();