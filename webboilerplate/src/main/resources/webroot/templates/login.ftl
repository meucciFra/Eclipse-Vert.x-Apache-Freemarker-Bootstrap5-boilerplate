<#import "boilerplate.ftl" as boilerplate>

<@boilerplate.boilerplate>
     <header class="d-flex py-2 my-2 ps-2">
         <p>Welcome</>
     </header>
     <main class="d-flex flex-grow-1 align-items-center justify-content-center">
         <div>
             <h1>Welcome Guest...</h1>
                <form class="d-flex flex-column align-items-center" action="loginForm" method="post">
                    <img src="assets/company/smiles.svg" alt="" width="200" height="200">
                    <h1 class="h3 mb-3 fw-normal text-center">Sign in</h1>
                    <div class="form-floating">
                      <input type="email" class="form-control" id="email" name="email" placeholder="name@example.com">
                      <label for="email">Email address</label>
                    </div>
                    <div class="form-floating">
                      <input type="password" class="form-control" id="password" name="password" placeholder="Password">
                      <label for="password">Password</label>
                    </div>
                    <div class="checkbox mb-3">
                      <label>
                        <input type="checkbox" name="checkbox" value="remember-me"> Remember me
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