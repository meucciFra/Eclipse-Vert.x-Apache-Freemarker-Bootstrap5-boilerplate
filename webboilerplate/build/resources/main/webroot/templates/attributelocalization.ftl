<#import "boilerplate.ftl" as boilerplate>

<@boilerplate.boilerplate>
    <header class="d-flex py-2 my-2">
        <p>Welcome</p>
    </header>
    <main class="d-flex flex-grow-1 align-items-center justify-content-center">
        <div>
            <p>If your browser first preferred language is Italian you should see "<strong>${sitewelcome!"Welcome"}</strong>" <br><br>
            otherwise you see "<strong>Welcome to this wonderfull site!</strong>" (it uses the default EN)</p>
        </div>
    </main>
    <#-- From there, add custom scripts as required  -->

    <#-- End scripts customization  -->

</@boilerplate.boilerplate>