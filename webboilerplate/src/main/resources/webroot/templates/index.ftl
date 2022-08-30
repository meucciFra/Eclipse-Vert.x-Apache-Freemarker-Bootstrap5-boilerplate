<#import "boilerplate.ftl" as boilerplate>

<@boilerplate.boilerplate>
    <header class="d-flex py-2 my-2 ps-2">
        <p>Welcome</p>
    </header>
    <main class="d-flex flex-grow-1 align-items-center justify-content-center">
        <div>
            <h1>Welcome Hello ${name}!, the Request path is ${path}</h1>
            <p>And with a custom yellow background</p>
            <h1 class="custom">Welcome Hello ${name}!, the Request path is ${path}</h1>
            <hr>
            <p>This page create also a Session and add to the session the variable <code>sessionVar1=${(session.sessionVar1)!"Session not created"}</code></p>
            <ul>
                <li>Visit this page: <a href="login">login</a> to see how a simple form can be handled</li>
                <li>Visit this page: <a href="page">Page</a> to see the list of HTTP header attributes plus some
                    additional data passed via JSON object and retrieves also the session data</li>
                 <li>Visit this page: <a href="attributelocalization">Localized Attributes</a> to see a part of message translated in your browser first choice language</li>
            </ul>
        </div>
    </main>
    <#-- From there, add custom scripts as required  -->

    <#-- End scripts customization  -->

</@boilerplate.boilerplate>