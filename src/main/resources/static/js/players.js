async function getPlayers() {
    const response= await fetch("http://localhost:8081/user/player",{
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
    console.log(data);
    return data.content;
}

function generatePlayerRows(players){
    
    const playerTableBody=document.querySelector("#players-table-body");
    let html="";
    players.forEach((player) => {
        html+=`<tr>
                    <th>${player.playerId}</th>
                    <th>${player.playerName} </th>
                    <th>${player.teamName} </th>
                </tr>`;
    });

    playerTableBody.innerHTML=html;
}
async function init() { 
    const players = await getPlayers();
    generatePlayerRows(players);
}   

init();