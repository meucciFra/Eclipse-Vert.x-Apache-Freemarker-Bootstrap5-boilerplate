<#import "boilerplate.ftl" as boilerplate>

<@boilerplate.boilerplate>
    <header class="d-flex py-2 my-2 ps-2">
        <p>Welcome</p>
    </header>
    <main class="d-flex flex-grow-1 align-items-center justify-content-center">
        <div>
            <p>If your browser first preferred language is Italian you should see "<strong>${sitewelcome!"Welcome"}</strong>" <br><br>
            otherwise you see "<strong>Welcome to this wonderfull site!</strong>" (it uses the default EN)</p>
            <p>And the numbers are presented like this: ${value}</p>
            <br>
            <h2>But, but, but....</h2>
            <br>
            <p>Even if you are Italian, you will see decimal separator with the symbol "." like for example 1000010.72 and not 1000010,72</p>
            <p>This is due to the fact that I didn't specify the Freemarker localization setting: <code><</code><code>#setting locale="it_IT"</code><code>></code> on top of this template page<p>
            <li>Visit this page: <a href="freemarkerlocalization">Localization with Freemarker</a> to see a page translated in your browser first choice language</li>
        </div>
    </main>
    <#-- From there, add custom scripts as required  -->

    <#-- End scripts customization  -->

</@boilerplate.boilerplate>