async function showPlayers() {
    const response= await fetch("http://localhost:8081/user/player",{
        method:"GET",
        headers:{
            "authorization":`Bearer ${localStorage.getItem("accessToken")}`
        }
    });

    const data=await response.json();
    return data;
}

function generatePlayerRows(players){
    
    const playerTableBody=document.querySelector("#matches-table-body");
    let html="";
    players.forEach(player => {
        html+=`<tr>
                    <th>${player.playerId}</th>
                    <th>${player.playerName} </th>
                    <th>${player.teamName} </th>
                </tr>`;
    });

    playerTableBody.innerHTML=html;
}
async function init() { 
    const players = await showPlayers();
    generateplayerRows(players);
}   

init();