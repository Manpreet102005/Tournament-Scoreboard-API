const url=`${BASE_URL}/user/player`;
let page=0;

const nextBtn=document.querySelector("#next");
const prevBtn=document.querySelector("#prev");

function generatePlayerRows(players){
    
    const playerTableBody=document.querySelector("#players-table-body");
    let html="";
    players.forEach((player) => {
        html+=`<tr>
                    <td>${player.playerId}</td>
                    <td>${player.playerName} </td>
                    <td>${player.teamName} </td>
                </tr>`;
    });

    playerTableBody.innerHTML=html;
}

async function init() { 
    const players = await fetchData(url,true,page);
    generatePlayerRows(players.content);  
    handlePagination(players,page,nextBtn,prevBtn);
}   

init();


nextBtn.addEventListener("click",async ()=>{
    page++;
    init()
});

prevBtn.addEventListener("click",async ()=>{
    if(page>0) page--;
    init()
});

const addPlayerBtn=document.querySelector("#add-player");
const deletePlayerBtn=document.querySelector("#delete-player");
const addModal=document.querySelector("#add-player-modal");
const deleteModal=document.querySelector("#delete-player-modal");
addPlayerBtn.addEventListener("click",()=>{
    addModal.style.display="flex";
    deleteModal.style.display="none";
})

const confirmAddPlayerBtn= document.querySelector("#add-player-ok");
confirmAddPlayerBtn.addEventListener("click",async ()=>{    
    const playerName=document.querySelector("#player-name").value;
    const response=await modificationRequest(`${BASE_URL}/admin/player`,"POST",{playerName});
    const data=await response.text();

    if(!response.ok){
        alert(data);
        return;
    }
    document.querySelector("#add-player-modal").style.display="none";
    document.querySelector("#player-name").value = "";
    init();
})

deletePlayerBtn.addEventListener("click",()=>{
    deleteModal.style.display="flex";
    addModal.style.display="none";
})

const confirmDeletePlayerBtn=document.querySelector("#delete-player-ok");
confirmDeletePlayerBtn.addEventListener("click",async ()=>{
    const playerId=document.querySelector("#player-id").value;
    const response = await modificationRequest(`${BASE_URL}/admin/player/${playerId}`,"DELETE");
    const data=await response.text();
    if(!response.ok){
        alert(data);
        return;
    }
    document.querySelector("#delete-player-modal").style.display="none";
    document.querySelector("#player-id").value = "";
    init();
})


document.querySelectorAll(".modal-cancel").forEach((btn) => {
    btn.addEventListener("click", () => {
        btn.closest(".modal").style.display = "none";
    });
});


