<#import "boilerplate.ftl" as boilerplate>

<@boilerplate.boilerplate>

     <header class="d-flex py-2 my-2 ps-2">
         <p>Welcome on ${sitename!"sitename"}</>
     </header>
     <main class="d-flex flex-grow-1 align-items-center justify-content-center">
         <div>
             <h1>Welcome Guest...</h1>
                <form>
                    <img class="m-4" src="assets/company/smiles.svg" alt="" width="75" height="75">
                    <h1 class="h3 mb-3 fw-normal">Sign in</h1>
                    <div class="form-floating">
                      <input type="email" class="form-control" id="floatingInput" placeholder="name@example.com">
                      <label for="floatingInput">Email address</label>
                    </div>
                    <div class="form-floating">
                      <input type="password" class="form-control" id="floatingPassword" placeholder="Password">
                      <label for="floatingPassword">Password</label>
                    </div>
                    <div class="checkbox mb-3">
                      <label>
                        <input type="checkbox" value="remember-me"> Remember me
                      </label>
                    </div>
                    <button class="w-100 btn btn-lg btn-primary" type="submit">Sign in</button>
                  </form>
             <p>Or register: <a href="page">Page</a></p>
         </div>
     </main>

    <#-- From there, add custom scripts as required  -->

    <#-- End scripts customization  -->

</@boilerplate.boilerplate>