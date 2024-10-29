export  default function LoginPage(){
    return(
       <div className="mt-4 ">
    
        <h1 className="text-4xl text-center">Login</h1>
        <form className="max-w-md mx-auto ">
            <input type="email"placeholder='your@rmail.com'></input>
            <input type = "password" placeholder="password"/>
            <button>Login</button>

        </form>
    </div> 
    )
    
}