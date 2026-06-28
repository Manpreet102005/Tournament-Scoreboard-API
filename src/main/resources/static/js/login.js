if(localStorage.getItem("refreshToken")){
    window.location.href="scoreboard.html";
}

const register=document.querySelector("#register-button");
const login=document.querySelector("#login-button");

register.addEventListener("click",()=>{
    register.classList.add("active");
    login.classList.remove("active");
    message.innerHTML = "Username: 6-20 characters, letters & numbers only<br>Password: 6-30 characters";
})
login.addEventListener("click",()=>{
    login.classList.add("active");
    register.classList.remove("active");
    message.innerHTML="";
})

const username=document.querySelector("#username");
const password=document.querySelector("#password");
const submit=document.querySelector("#submit-button");
const message = document.querySelector("#message");

submit.addEventListener("click",async ()=>{
    submit.textContent="Submitting...";
    submit.disabled=true;
    let mode="";
    if(register.classList.contains("active")){
       mode="register";
    }
    if(login.classList.contains("active")){
        mode="login"
    }
    try{
    if(mode==="register"){
        const allowed = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        if(username.value.length<6 ||username.value.length>20){
            message.textContent="Username must be 6-20 characters";
            return;
        }
        if(password.value.length<6 ||password.value.length>30){
            message.textContent="Password must be 6-30 characters";
            return;
        }
        for(let char of username.value){
            if(!allowed.includes(char)){
                message.textContent="Only letters and numbers allowed in username";
                return;
            }
        }
    }
        const response=await fetch(`${BASE_URL}/auth/${mode}`,{
            method:"POST",
            headers:{"content-type":"application/json"},
            body:JSON.stringify({username:username.value,password:password.value})
        });
    
    if(mode=="login"){
        const data= await response.json();
        if(response.ok){
            localStorage.setItem("accessToken",data.accessToken);
            localStorage.setItem("refreshToken",data.refreshToken);
            localStorage.setItem("userRole",data.userRole);
            window.location.href = "scoreboard.html";
        }else{
            message.textContent = "Invalid username or password";
        }
    }
    else{
        const data= await response.text();
        if(!response.ok){
            console.log(response.status);
            message.textContent=data;
            return;
        }
        message.textContent=data+"Login to Proceed.";
        login.classList.add("active");
        register.classList.remove("active");
        password.value="";
        
    }
    }catch(e){
        message.textContent="Server not reachable. Try again later.";
    }finally{
        submit.textContent="Submit";
        submit.disabled=false;
    }
});