<#import "boilerplate.ftl" as boilerplate>

<@boilerplate.boilerplate>
     <header class="d-flex py-2 my-2 ps-2">
         <p>Welcome</>
     </header>
    <h1>Welcome</h1>
    <br>
    <h2>Request parameters</h2>
        <#list content>
            <ul>
            <#items as key, value>
                <li>${key}:${value!"Not available"}</li>
            </#items>
            </ul>
        </#list>
    <br>
    <h2>The Request path is: ${content.path}</h2>
    <h2>The session variable is <code>sessionVar1=${(session.sessionVar1)!"Session not created"}</code></h2>
    <h2>Hey, see this nice dog!!!</h2>
    <img src="assets/site/dog.jpg" class="img-fluid" alt="a dog">
    <p> Photo by <a href="https://unsplash.com/@chrismcintosh?utm_source=unsplash&utm_medium=referral&utm_content=creditCopyText">Chris McIntosh</a>
        on <a href="https://unsplash.com/?utm_source=unsplash&utm_medium=referral&utm_content=creditCopyText">Unsplash</a></p>

    <#-- From there, add custom scripts as required  -->
    <script src="javascript/script.js"></script>
    <#-- End scripts customization  -->

</@boilerplate.boilerplate>