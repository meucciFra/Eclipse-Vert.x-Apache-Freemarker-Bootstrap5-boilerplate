<#import "boilerplate.ftl" as boilerplate>
<#setting locale="it_IT">
<@boilerplate.boilerplate>
    <header class="d-flex py-2 my-2 ps-2">
        <p>Benvenuto</p>
    </header>
    <main class="d-flex flex-grow-1 align-items-center justify-content-center">
        <div>
            <p>Se il tuo browser ha come prima preferenza la lingua italiana, dovresti vedere il contenuto della pagina <strong>freemarkerlocalization_it_IT.ftl</strong></p>
            <p>In Italia siamo soliti rappresentare le cifre decimale con il simbolo "," come ad esempio ${value}</p>
        </div>
    </main>
    <#-- From there, add custom scripts as required  -->

    <#-- End scripts customization  -->

</@boilerplate.boilerplate>