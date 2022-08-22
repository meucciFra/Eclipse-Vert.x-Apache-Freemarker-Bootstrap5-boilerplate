<#import "boilerplate.ftl" as boilerplate>

<@boilerplate.boilerplate>

    <h1>Welcome Hello</h1>
    <h2>The user agent is: ${useragent!"user-agent"}</h2>
    <h2>The Request path is: ${path}</h2>
    <p>See this nice cat</p>
    <img src="assets/site/cat.jpeg" alt="a cat">

    <#-- From there, add custom scripts as required  -->
    <script src="javascript/index.js"></script>
    <#-- End scripts customization  -->

</@boilerplate.boilerplate>