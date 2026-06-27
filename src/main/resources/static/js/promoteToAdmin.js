async function generateUserRows(users){
    const tableBody=document.querySelector("#user-body");
    let html="";
    users.forEach(user => {
        let actionContent="";
        if(user.userRole==="ROLE_ADMIN"){
            actionContent="N/A";
        }else{
            actionContent=`
            <button class="buttons promote-btn">Make Admin</button>
            `
        }
        html+=`
            <tr id="user-row-${user.username}" class="user-row">
                <td>${user.username}</td>
                <td>${user.userRole}</td>
                <td>${actionContent}</td>
            </tr>
        `
    });
    tableBody.innerHTML=html;
}

async function init(){
    const users=await fetchData(`${BASE_URL}/admin/user`);
    await generateUserRows(users);
}

init();
let selectedUsername=null;
document.querySelector("#user-body").addEventListener("click", (e) => {
    if(e.target.classList.contains("promote-btn")){
        selectedUsername=e.target.closest(".user-row").id.replace("user-row-","");
        document.querySelector("#promote-to-admin").style.display="flex";
    }  
})

document.querySelector("#make-admin-ok").addEventListener("click",async () => {
    const response=await modificationRequest(`${BASE_URL}/admin/user/promote/${selectedUsername}`,"PUT");
    document.querySelector("#promote-to-admin").style.display="none";
    init();
    if(!response.ok){
        const data=await response.text();
        alert(data);
    }
})
document.querySelector("#make-admin-cancel").addEventListener("click",async () => {
    document.querySelector("#promote-to-admin").style.display="none";
})