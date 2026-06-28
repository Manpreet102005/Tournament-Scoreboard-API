const url=`${BASE_URL}/user/match`;
let page=0;

const nextBtn=document.querySelector("#next");
const prevBtn=document.querySelector("#prev");

function generateMatchRows(matches){
    
    const matchesTableBody=document.querySelector("#matches-table-body");
    let html="";
    matches.forEach(match => {
        html+=`<tr>
                    <td>${match.matchId}</td>
                    <td>${match.matchTitle} </td>
                    <td>${match.matchDateTime} </td>
                    <td>${match.teamAName} </td>
                    <td>${match.teamBName} </td>
                    <td>${match.teamAScore} </td>
                    <td>${match.teamBScore} </td>
                    <td>${match.matchStatus} </td>
                </tr>`;
    });

    matchesTableBody.innerHTML=html;
}
async function init() { 
    const matches = await fetchData(url,true,page);
    generateMatchRows(matches.content);
    handlePagination(matches,page,nextBtn,prevBtn);
}   

init();


const addMatchBtn=document.querySelector("#add-match");
const addMatchModal=document.querySelector("#add-match-modal");

addMatchBtn.addEventListener("click",()=>{
    document.querySelector("#match-date-time").min = new Date().toISOString().slice(0,16);
    addMatchModal.style.display="flex";
})
const confirmAddMatchBtn= document.querySelector("#add-match-ok");
confirmAddMatchBtn.addEventListener("click",async ()=>{
    const matchTitle=document.querySelector("#match-title");
    const matchDateTime=document.querySelector("#match-date-time");
    const teamAId=document.querySelector("#team-A-id");
    const teamBId=document.querySelector("#team-B-id");
    const response=await modificationRequest(`${BASE_URL}/admin/match/${teamAId.value}/${teamBId.value}`,
        "POST",
        {
            matchTitle:matchTitle.value,
            matchDateTime:matchDateTime.value
    });
    const data=await response.text();
    console.log(data);
    if(response.ok){
        matchTitle.value="";
        matchDateTime.value="";
        teamAId.value="";
        teamBId.value="";

        document.querySelector("#add-match-modal").style.display="none";
        init();
    }else{
        alert(data);
    }
})

const cancelAddMatchBtn=document.querySelector("#add-match-cancel");
cancelAddMatchBtn.addEventListener("click",()=>{
    addMatchModal.style.display="none";
})

const matchConsoleBtn=document.querySelector("#match-console");
matchConsoleBtn.addEventListener("click",()=>{
    window.location.href="matchAdminConsole.html";
})