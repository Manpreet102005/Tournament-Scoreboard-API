function generateOngoingMatchCards(ongoingMatches){
    
    const ongoingContainer=document.querySelector("#ongoing-container");
    let html="";
    ongoingMatches.forEach((ongoingMatch) => {
        html+=`
            <div class="match-card">
                <div class="match-info">
                    <div class="match-title">${ongoingMatch.matchTitle}</div>
                    <div class="match-teams">${ongoingMatch.teamAName} vs ${ongoingMatch.teamBName}</div>
                    <div class="match-date-time">${ongoingMatch.matchDateTime}</div>
                </div>
                <div class="match-scores">${ongoingMatch.teamAScore} - ${ongoingMatch.teamBScore}</div>
                <div class="match-card-actions">
                    <button class="buttons admin-buttons ">Update Score</button>
                    <button class="buttons admin-buttons ">End Match</button>
                </div>
            </div>
            `;
    });

    ongoingContainer.innerHTML=html;
}
function generateScheduledMatchCards(scheduledMatches){
    
    const scheduledContainer=document.querySelector("#scheduled-container");
    let html="";
    scheduledMatches.forEach((scheduledMatch) => {
        html+=`
            <div class="match-card">
                <div class="match-info">
                    <div class="match-title">${scheduledMatch.matchTitle}</div>
                    <div class="match-teams">${scheduledMatch.teamAName} vs ${scheduledMatch.teamBName}</div>
                    <div class="match-date-time">${scheduledMatch.matchDateTime}</div>
                </div>
                <div class="match-scores">-</div>
                <div class="match-card-actions">
                    <button class="buttons admin-buttons ">Start Match</button>
                    <button class="buttons admin-buttons ">Reschedule</button>
                    <button class="buttons admin-buttons ">Delete Match</button>
                </div>
            </div>
            `;
    });

    scheduledContainer.innerHTML=html;
}
function generateCompletedMatchCards(completedMatches){
    
    const completedContainer=document.querySelector("#completed-container");
    let html="";
    completedMatches.forEach((completedMatch) => {
        let winnerName = completedMatch.teamAScore > completedMatch.teamBScore ? completedMatch.teamAName : (completedMatch.teamAScore < completedMatch.teamBScore ? completedMatch.teamBName : 'DRAW');
        html+=`
            <div class="match-card">
                <div class="match-info">
                    <div class="match-title">${completedMatch.matchTitle}</div>
                    <div class="match-teams">${completedMatch.teamAName} vs ${completedMatch.teamBName}</div>
                    <div class="match-date-time">${completedMatch.matchDateTime}</div>
                </div>
                <div class="match-scores">${completedMatch.teamAScore} - ${completedMatch.teamBScore}</div>
                <div class="match-card-actions completed-match-stats">
                   <div class="match-winner-details">
                        <div class="match-winner-title match-title">
                            ${winnerName === 'DRAW' ? 'RESULT:' : 'WINNER:'}
                        </div>
                        <div class="match-winner">
                            ${winnerName}
                        </div>
                   </div>
                </div>
            </div>
            `;
    });

    completedContainer.innerHTML=html;
}

async function init(){
    const baseUrl="http://localhost:8081/admin/match/status/";
    
    const ongoingMatches=await fetchData(baseUrl+"ONGOING");
    const scheduledMatches=await fetchData(baseUrl+"SCHEDULED");
    const completedMatches=await fetchData(baseUrl+"COMPLETED");
    
    generateOngoingMatchCards(ongoingMatches);
    generateScheduledMatchCards(scheduledMatches);
    generateCompletedMatchCards(completedMatches);
}

init();
