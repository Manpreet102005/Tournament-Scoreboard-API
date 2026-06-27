const getAllUrl=`${BASE_URL}/user/team`;

function generateTeamRows(teams){
    
    const teamsTableBody=document.querySelector("#teams-table-body");
    let html="";
    teams.forEach(team => {
        html+=`<tr>
                    <td>${team.teamId}</td>
                    <td>${team.teamName} </td>
                    <td>${team.totalScore} </td>
                    <td>${team.matchesPlayed} </td>
                    <td>${team.wins} </td>
                    <td>${team.draws} </td>
                </tr>`;
    });

    teamsTableBody.innerHTML=html;
}
async function init() { 
    const teams = await fetchData(getAllUrl,false);
    generateTeamRows(teams);
}   

init();

const addTeamBtn=document.querySelector("#add-team");
const removeTeamBtn=document.querySelector("#remove-team");
const renameTeamBtn=document.querySelector("#rename-team");
const assignPlayerBtn=document.querySelector("#assign-player-to-team");

const addModal=document.querySelector("#add-team-modal");
const removeModal=document.querySelector("#remove-team-modal");
const renameModal=document.querySelector("#rename-team-modal");
const assignPlayerModal=document.querySelector("#assign-player-to-team-modal");

addTeamBtn.addEventListener("click",()=>{
    addModal.style.display="flex";
    removeModal.style.display="none";
    renameModal.style.display="none";
    assignPlayerModal.style.display="none";
})
removeTeamBtn.addEventListener("click",()=>{
    addModal.style.display="none";
    removeModal.style.display="flex";
    renameModal.style.display="none";
    assignPlayerModal.style.display="none";
})
renameTeamBtn.addEventListener("click",()=>{
    addModal.style.display="none";
    removeModal.style.display="none";
    renameModal.style.display="flex";
    assignPlayerModal.style.display="none";
})
assignPlayerBtn.addEventListener("click",()=>{
    addModal.style.display="none";
    removeModal.style.display="none";
    renameModal.style.display="none";
    assignPlayerModal.style.display="flex";
})

document.querySelectorAll(".modal-cancel").forEach((btn) => {
    btn.addEventListener("click", () => {
        btn.closest(".modal").style.display = "none";
    });
});


const confirmAddTeamBtn= document.querySelector("#add-team-ok");
confirmAddTeamBtn.addEventListener("click",async ()=>{    
    const teamName=document.querySelector("#team-name").value; 
    const response=await modificationRequest(`${BASE_URL}/admin/team`,"POST",{teamName});
    const data=await response.text();
    console.log(data);
    if(response.ok){
        document.querySelector("#add-team-modal").style.display="none";
        init();
    }
    else{
        alert(data);
    }
});


const confirmRenameTeamBtn=document.querySelector("#rename-team-ok");
confirmRenameTeamBtn.addEventListener("click",async ()=>{
    const teamId=document.querySelector("#rename-team-id");
    const newTeamName=document.querySelector("#new-team-name");
    const response=await modificationRequest(`${BASE_URL}/admin/team/${teamId.value}/${newTeamName.value}`,
        "PUT");
    const data=await response.text();
    if(response.ok){
        teamId.value="";
        newTeamName.value="";
        document.querySelector("#rename-team-modal").style.display="none";
        init();
    }
    else{
        alert(data);
    }
    console.log(data);
});

const confirmRemoveTeamBtn=document.querySelector("#remove-team-ok");
confirmRemoveTeamBtn.addEventListener("click",async ()=>{
    const teamId=document.querySelector("#remove-team-id").value;
    const response=await modificationRequest(`${BASE_URL}/admin/team/${teamId}`,"DELETE");
    const data=await response.text();
    if(response.ok){
        document.querySelector("#remove-team-modal").style.display="none";
        init();
    }
    else{
        alert(data);
    }
    console.log(data);
});

const confirmAssignPlayerBtn=document.querySelector("#assign-player-ok");
confirmAssignPlayerBtn.addEventListener("click",async ()=>{
    const teamId=document.querySelector("#assign-team-id");
    const playerId=document.querySelector("#assign-player-id");
    const response=await modificationRequest(`${BASE_URL}/admin/team/${teamId.value}/player/${playerId.value}`,
        "PUT"
    );
    const data=await response.text();
    if(response.ok){
        teamId.value="";
        playerId.value="";
        document.querySelector("#assign-player-to-team-modal").style.display="none";
        init();
    }
    else{
        alert(data);
    }
    console.log(data);
});

