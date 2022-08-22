<#import "boilerplate.ftl" as boilerplate>

<@boilerplate.boilerplate>

    <h1>Welcome Hello ${name}!, the Request path is ${path}</h1>
    <h1 class="custom">Welcome Hello ${name}!, the Request path is ${path}</h1>
    <p>Visit this page: <a href="page">Page</a></p>


    <#-- From there, add custom scripts as required  -->

    <#-- End scripts customization  -->

</@boilerplate.boilerplate>