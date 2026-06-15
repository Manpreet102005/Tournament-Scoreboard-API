const register=document.querySelector("#register-button");
const login=document.querySelector("#login-button");

register.addEventListener("click",()=>{
    register.classList.add("active");
    login.classList.remove("active");
})
login.addEventListener("click",()=>{
    login.classList.add("active");
    register.classList.remove("active");
})

const username=document.querySelector("#username");
const password=document.querySelector("#password");
const submit=document.querySelector("#submit-button");

submit.addEventListener("click",async ()=>{
    let mode="";
    if(register.classList.contains("active")){
       mode="register";
    }
    if(login.classList.contains("active")){
        mode="login"
    }
    const response=await fetch(`http://localhost:8081/auth/${mode}`,{
            method:"POST",
            headers:{"content-type":"application/json"},
            body:JSON.stringify({username:username.value,password:password.value})
        });

    if(mode=="login"){
        const data= await response.json();
        localStorage.setItem("accessToken",data.accessToken);
        localStorage.setItem("refreshToken",data.refreshToken);
        window.location.href="scoreboard.html"
    }
    else{
        const data= await response.text();
        const message=document.querySelector("#message"+"Login to Proceed.")
        message.textContent=data;
        console.log(data);
        login.classList.add("active");
        register.classList.remove("active");
        password.value="";
    }
})