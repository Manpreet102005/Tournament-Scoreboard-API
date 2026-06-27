const BASE_URL = window.location.hostname === "localhost"
  ? "${BASE_URL}"
  : "https://tournament-scoreboard-api-production.up.railway.app";
  
async function fetchData(url,paginated=false,page=0,size=9) {
    const fullUrl=paginated?`${url}?page=${page}&size=${size}`:url;
    const response= await fetch(fullUrl,{
        method:"GET",
        headers:{
            "authorization":`Bearer ${localStorage.getItem("accessToken")}`
        }
    });
    //token expired
    if(response.status==401){
        await refreshNewAccessToken();
        const fullUrl=paginated?`${url}?page=${page}&size=${size}`:url;
        const response= await fetch(fullUrl,{
            method:"GET",
            headers:{
                "authorization":`Bearer ${localStorage.getItem("accessToken")}`
            }
        });
        if(!response.ok){
            return paginated ? { content: [], totalPages: 0 } : [];
        }
        const data=await response.json();
        console.log(data);
        return data;
    }

    if(!response.ok){
        return paginated ? { content: [], totalPages: 0 } : [];
    }
    const data=await response.json();
    console.log(data);
    return data;
}

function handlePagination(data,page,nextBtn,prevBtn){
    if(page>=data.totalPages-1){
        nextBtn.disabled=true;
        nextBtn.style.backgroundColor="grey";
    }
    else{
        nextBtn.disabled=false;
        nextBtn.style.backgroundColor="#1d57a1";

    }
    if(page===0){
        prevBtn.disabled=true;
        prevBtn.style.backgroundColor="grey";
    }
    else{
        prevBtn.disabled=false; 
        prevBtn.style.backgroundColor="#1d57a1";
    }
}
async function refreshNewAccessToken() {
    const refreshToken=localStorage.getItem("refreshToken");
    const response=await fetch(`${BASE_URL}/auth/refresh`,{
        method:"POST",
        headers:{
            "content-type":"application/json",
        },
        body:JSON.stringify({refreshToken})
    });
    if(!response.ok){
        localStorage.clear();
        window.location.href="index.html";
        return;
    }
    const data=await response.json();
    localStorage.setItem("accessToken",data.accessToken);
}
async function modificationRequest(url, method, body=null){
    const options = {
        method,
        headers:{
            "authorization": `Bearer ${localStorage.getItem("accessToken")}`,
            "content-type": "application/json"
        }
    };
    if(body) options.body = JSON.stringify(body);

    let response = await fetch(url, options);

    if(response.status == 401){
        await refreshNewAccessToken();
        options.headers["authorization"] = `Bearer ${localStorage.getItem("accessToken")}`;
        response = await fetch(url, options);
    }

    return response;
}