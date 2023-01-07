<#import "boilerplate.ftl" as boilerplate>

<@boilerplate.boilerplate>
     <header class="d-flex py-2 my-2 ps-2">
         <p>Welcome</p>
     </header>
     <main class="d-flex flex-grow-1 align-items-center justify-content-center">
         <div>
             <h1>Welcome Guest...</h1>
                <form class="d-flex flex-column align-items-center" action="/loginhandler" method="post">
                    <img src="assets/company/smiles.svg" alt="" width="200" height="200">
                    <h1 class="h3 mb-3 fw-normal text-center">Sign in</h1>
                    <div class="form-floating">
                      <input type="text" class="form-control" id="username" name="username" placeholder="username">
                      <label for="username">Username ('tim')</label>
                    </div>
                    <div class="form-floating">
                      <input type="password" class="form-control" id="password" name="password" placeholder="Password">
                      <label for="password">Password ('sausages')</label>
                    </div>
                    <button class="w-100 btn btn-lg btn-primary" type="submit">${sitesignin!"Sign in"}</button>
                  </form>
             <p>Or <a href="#">${sitesignup!"Sign up"}</a></p>
         </div>
     </main>

    <#-- From there, add custom scripts as required  -->

    <#-- End scripts customization  -->

</@boilerplate.boilerplate>