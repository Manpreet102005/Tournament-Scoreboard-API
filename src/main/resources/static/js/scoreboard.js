async function showScoreBoard() {
    const response= await fetch("http://localhost:8081/scoreboard",{
        method:"GET",
        headers:{
            "authorization":`Bearer ${localStorage.getItem("accessToken")}`
        }
    });

    const data=await response.json();
    return data;
}

function generateScoreboardRows(scoreboardDetails){
    
    const scoreboardTableBody=document.querySelector("#scoreboard-table-body");
    let html="";
    scoreboardDetails.forEach(teamDTO => {
        html+=`<tr>
                    <th>${teamDTO.rank}</th>
                    <th>${teamDTO.teamId} </th>
                    <th>${teamDTO.teamName} </th>
                    <th>${teamDTO.totalScore} </th>
                    <th>${teamDTO.matchesPlayed} </th>
                    <th>${teamDTO.wins} </th>
                    <th>${teamDTO.winRatio} </th>
                </tr>`;
    });

    scoreboardTableBody.innerHTML=html;
}
async function init() { 
    const scoreboardDetails = await showScoreBoard();
    generateScoreboardRows(scoreboardDetails);
}   

init();