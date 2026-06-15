async function showTeams() {
    const response= await fetch("http://localhost:8081/user/team",{
        method:"GET",
        headers:{
            "authorization":`Bearer ${localStorage.getItem("accessToken")}`
        }
    });

    if(!response.ok){
        console.log(response.status);
        return;
    }
    const data=await response.json();
    return data;
}

function generateTeamRows(teams){
    
    const teamsTableBody=document.querySelector("#teams-table-body");
    let html="";
    teams.forEach(team => {
        html+=`<tr>
                    <th>${team.teamId}</th>
                    <th>${team.teamName} </th>
                    <th>${team.totalScore} </th>
                    <th>${team.matchesPlayed} </th>
                    <th>${team.wins} </th>
                    <th>${team.draws} </th>
                </tr>`;
    });

    teamsTableBody.innerHTML=html;
}
async function init() { 
    const teams = await showTeams();
    generateTeamRows(teams);
}   

init();