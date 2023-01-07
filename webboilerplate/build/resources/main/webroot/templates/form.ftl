<#import "boilerplate.ftl" as boilerplate>

<@boilerplate.boilerplate>
     <header class="d-flex py-2 my-2 ps-2">
         <p>Welcome</p>
     </header>
     <main class="d-flex flex-grow-1 align-items-left">
         <div>
             <h1>Example Form</h1>
                <form class="d-flex flex-column align-items-left" action="formExample" method="post" enctype="multipart/form-data">
                    <h1 class="h3 mb-3 fw-normal text-center">An example of a form</h1>
                    <div class="form-floating">
                      <input type="text" class="form-control" id="name" name="name" placeholder="Your Name">
                      <label for="name">Name</label>
                    </div>
                    <div class="form-floating">
                      <input type="text" class="form-control" id="surname" name="surname" placeholder="Your Surname">
                      <label for="surname">Surname</label>
                    </div>
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
                        <input type="checkbox" name="checkbox" value="checked"> Check me
                      </label>
                    </div>
                    <div class="mb-3">
                      <label for="file" class="form-label">Default file input example</label>
                      <input class="form-control" type="file" id="file" name="file" multiple>
                    </div>
                    <button class="w-100 btn btn-lg btn-primary" type="submit">Send Form Data</button>
                </form>
         </div>
     </main>

    <#-- From there, add custom scripts as required  -->

    <#-- End scripts customization  -->

</@boilerplate.boilerplate>