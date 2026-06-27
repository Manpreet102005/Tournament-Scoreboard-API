function generateOngoingMatchCards(ongoingMatches){
    
    const ongoingContainer=document.querySelector("#ongoing-container");
    let html="";
    if(ongoingMatches.length==0){
        html+="<h3>No Ongoing Matches</h3>"
    }
    ongoingMatches.forEach((ongoingMatch) => {
        html+=`
            <div id="card-${ongoingMatch.matchId}" class="match-card " data-team-a-id="${ongoingMatch.teamAId}" data-team-b-id="${ongoingMatch.teamBId}">
                <div class="match-info">
                    <div class="match-title">${ongoingMatch.matchTitle}</div>
                    <div class="match-teams">${ongoingMatch.teamAName} vs ${ongoingMatch.teamBName}</div>
                    <div class="match-date-time">${ongoingMatch.matchDateTime}</div>
                </div>
                <div class="match-scores">${ongoingMatch.teamAScore} - ${ongoingMatch.teamBScore}</div>
                <div class="match-card-actions">
                    <button class="buttons admin-buttons update-btn">Update Score</button>
                    <button class="buttons admin-buttons end-btn">End Match</button>
                </div>
            </div>
            `;
    });

    ongoingContainer.innerHTML=html;
}
function generateScheduledMatchCards(scheduledMatches){
    
    const scheduledContainer=document.querySelector("#scheduled-container");
    let html="";
    if(scheduledMatches.length==0){
        html+="<h3>No Scheduled Matches</h3>"
    }
    scheduledMatches.forEach((scheduledMatch) => {
        html+=`
            <div id="card-${scheduledMatch.matchId}" class="match-card">
                <div class="match-info">
                    <div class="match-title">${scheduledMatch.matchTitle}</div>
                    <div class="match-teams">${scheduledMatch.teamAName} vs ${scheduledMatch.teamBName}</div>
                    <div class="match-date-time">${scheduledMatch.matchDateTime}</div>
                </div>
                <div class="match-scores">-</div>
                <div class="match-card-actions">
                    <button class="buttons admin-buttons start-btn">Start Match</button>
                    <button class="buttons admin-buttons reschedule-btn">Reschedule</button>
                    <button class="buttons admin-buttons delete-btn">Delete Match</button>
                </div>
            </div>
            `;
    });

    scheduledContainer.innerHTML=html;
}
function generateCompletedMatchCards(completedMatches){
    
    const completedContainer=document.querySelector("#completed-container");
    let html="";
    if(completedMatches.length==0){
        html+="<h3>No Completed Matches</h3>"
    }
    completedMatches.forEach((completedMatch) => {
        let winnerName = completedMatch.teamAScore > completedMatch.teamBScore ? completedMatch.teamAName : (completedMatch.teamAScore < completedMatch.teamBScore ? completedMatch.teamBName : 'DRAW');
        html+=`
            <div id="card-${completedMatch.matchId}" class="match-card">
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
    const baseUrl=`${BASE_URL}/admin/match/status/`;
    
    const ongoingMatches=await fetchData(baseUrl+"ONGOING");
    const scheduledMatches=await fetchData(baseUrl+"SCHEDULED");
    const completedMatches=await fetchData(baseUrl+"COMPLETED");
    
    generateOngoingMatchCards(ongoingMatches);
    generateScheduledMatchCards(scheduledMatches);
    generateCompletedMatchCards(completedMatches);

    activateBtns();
    
}

let selectedMatchId=null;
let selectedTeamAId = null;
let selectedTeamBId = null;

function activateBtns(){
    document.querySelectorAll(".start-btn").forEach(btn=>{
        btn.addEventListener("click",(e)=>{
            selectedMatchId=e.target.closest(".match-card").id.replace("card-", "");
            document.querySelector("#start-match").style.display="flex";
        });
    });

    document.querySelectorAll(".reschedule-btn").forEach(btn=>{
        btn.addEventListener("click",(e)=>{
            selectedMatchId=e.target.closest(".match-card").id.replace("card-", "");
            document.querySelector("#reschedule-match").style.display="flex";
        });
    });

    document.querySelectorAll(".delete-btn").forEach(btn=>{
        btn.addEventListener("click",(e)=>{
            selectedMatchId=e.target.closest(".match-card").id.replace("card-", "");
            document.querySelector("#delete-match").style.display="flex";
        });
    });

    document.querySelectorAll(".update-btn").forEach(btn=>{
        btn.addEventListener("click",(e)=>{
            const card = e.target.closest(".match-card");
            selectedMatchId = card.id.replace("card-", "");
            selectedTeamAId = card.dataset.teamAId;
            selectedTeamBId = card.dataset.teamBId;
            document.querySelector("#update-team-scores").style.display="flex";
        });
    });

    document.querySelectorAll(".end-btn").forEach(btn=>{
        btn.addEventListener("click",(e)=>{
            selectedMatchId=e.target.closest(".match-card").id.replace("card-", "");
            document.querySelector("#end-match").style.display="flex";
        });
    });
}

document.querySelector("#start-match-ok").addEventListener("click",async (e)=>{
    const response=await modificationRequest(`${BASE_URL}/admin/match/start/${selectedMatchId}`,"PUT");
    if(!response.ok){
        const data=await response.text();
        alert(data);
    }
    else{
        document.querySelector("#start-match").style.display="none";
        await init();
    }
});

document.querySelector("#start-match-cancel").addEventListener("click", () => {
    document.querySelector("#start-match").style.display="none";
});

document.querySelector("#update-score-ok").addEventListener("click",async (e)=>{
    const teamAScore=document.querySelector("#team-A-score").value;
    const teamBScore=document.querySelector("#team-B-score").value;
    if(teamAScore === "" || teamBScore === "") {
        alert("Scores cannot be empty.");
        return;
    }
    const response1=await modificationRequest(`${BASE_URL}/admin/match/${selectedMatchId}/${selectedTeamAId}/${teamAScore}`,"PUT");
    const response2=await modificationRequest(`${BASE_URL}/admin/match/${selectedMatchId}/${selectedTeamBId}/${teamBScore}`,"PUT");
    if(!(response1.ok && response2.ok)){
        const data1=await response1.text();
        const data2=await response2.text();
        alert(data1+"\n"+data2);
    }else{
        document.querySelector("#update-team-scores").style.display="none";
        await init();
    }
});

document.querySelector("#update-score-cancel").addEventListener("click", () => {
    document.querySelector("#update-team-scores").style.display="none";
});

document.querySelector("#reschedule-match-ok").addEventListener("click",async (e)=>{
    const newDateTime=document.querySelector("#new-date-time").value;
    if(!newDateTime) {
        alert("Date Time cannot be empty.");
        return;
    }
    const response=await modificationRequest(`${BASE_URL}/admin/match/reschedule/${selectedMatchId}/${newDateTime}`,"PUT");
    if(!response.ok){
        const data=await response.text();
        alert(data);
    }else{
        document.querySelector("#reschedule-match").style.display="none";
        await init();
    }
});

document.querySelector("#reschedule-match-cancel").addEventListener("click", () => {
    document.querySelector("#reschedule-match").style.display="none";
});

document.querySelector("#delete-match-ok").addEventListener("click",async (e)=>{
    const response=await modificationRequest(`${BASE_URL}/admin/match/${selectedMatchId}`,"DELETE");
    if(!response.ok){
        const data=await response.text();
        alert(data);
    }else{
        document.querySelector("#delete-match").style.display="none";
        await init();
    }
});

document.querySelector("#delete-match-cancel").addEventListener("click", () => {
    document.querySelector("#delete-match").style.display="none";
});

document.querySelector("#end-match-ok").addEventListener("click",async (e)=>{
    const response=await modificationRequest(`${BASE_URL}/admin/match/end/${selectedMatchId}`,"PUT");
    if(!response.ok){
        const data=await response.text();
        alert(data);
    }else{
        document.querySelector("#end-match").style.display="none";
        await init()
    }
});

document.querySelector("#end-match-cancel").addEventListener("click", () => {
    document.querySelector("#end-match").style.display="none";
});

init();


