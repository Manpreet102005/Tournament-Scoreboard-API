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
            <tr class="user-row">
                <td>${user.username}</td>
                <td>${user.userRole}</td>
                <td>${actionContent}</td>
            </tr>
        `
    });
    tableBody.innerHTML=html;
}

async function init(){
    const users=await fetchData("http://localhost:8081/admin/user");
    await generateUserRows(users);
}

init();