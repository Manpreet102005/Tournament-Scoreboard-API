const url=`${BASE_URL}/scoreboard`;

function generateScoreboardRows(scoreboardDetails){
    
    const scoreboardTableBody=document.querySelector("#scoreboard-table-body");
    let html="";
    scoreboardDetails.forEach(teamDTO => {
        html+=`<tr>
                    <td>${teamDTO.rank}</td>
                    <td>${teamDTO.teamName} </td>
                    <td>${teamDTO.totalScore} </td>
                    <td>${teamDTO.matchesPlayed} </td>
                    <td>${teamDTO.wins} </td>
                    <td>${teamDTO.winRatio} </td>
                </tr>`;
    });

    scoreboardTableBody.innerHTML=html;
}
async function init() { 
    const scoreboardDetails = await fetchData(url);
    generateScoreboardRows(scoreboardDetails);
}   

init();