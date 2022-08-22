<#import "boilerplate.ftl" as boilerplate>

<@boilerplate.boilerplate>

    <h1>Welcome Hello</h1>
    <h2>The user agent is: ${useragent!"user-agent"}</h2>
    <h2>The Request path is: ${path}</h2>
    <p>See this nice dog</p>
    <img src="assets/site/dog.jpg" class="img-fluid" alt="a dog">
    <p> Photo by <a href="https://unsplash.com/@chrismcintosh?utm_source=unsplash&utm_medium=referral&utm_content=creditCopyText">Chris McIntosh</a> on <a href="https://unsplash.com/?utm_source=unsplash&utm_medium=referral&utm_content=creditCopyText">Unsplash</a></p>



    <#-- From there, add custom scripts as required  -->
    <script src="javascript/script.js"></script>
    <#-- End scripts customization  -->

</@boilerplate.boilerplate>