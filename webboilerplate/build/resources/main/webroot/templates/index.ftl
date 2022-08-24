<#import "boilerplate.ftl" as boilerplate>

<@boilerplate.boilerplate>
    <header class="d-flex py-2 my-2">
        <p>Ciao, Hola, Hello</>
    </header>
    <main class="d-flex flex-grow-1 align-items-center justify-content-center">
        <div>
            <h1>Welcome Hello ${name}!, the Request path is ${path}</h1>
            <p>And with a custom yellow background</p>
            <h1 class="custom">Welcome Hello ${name}!, the Request path is ${path}</h1>
            <p>Visit this page: <a href="page">Page</a></p>
        </div>
    </main>
    <#-- From there, add custom scripts as required  -->

    <#-- End scripts customization  -->

</@boilerplate.boilerplate>