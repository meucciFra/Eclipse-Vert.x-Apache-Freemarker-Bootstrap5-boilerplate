<#import "boilerplate.ftl" as boilerplate>
<#setting locale="en_EN">
<@boilerplate.boilerplate>
    <header class="d-flex py-2 my-2 ps-2">
        <p>Welcome</p>
    </header>
    <main class="d-flex flex-grow-1 align-items-center justify-content-center">
        <div>
            <p>If your browser first preferred language is different form Italian, you should see the content of the page <strong>freemarkerlocalization.ftl</strong></p>
            <p>If you are not Italian, usually you represent decimals with the symbol "." like for example ${value}</p>
        </div>
     </main>
     <#-- From there, add custom scripts as required  -->

     <#-- End scripts customization  -->

 </@boilerplate.boilerplate>