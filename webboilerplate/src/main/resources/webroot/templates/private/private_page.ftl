<#import "../boilerplate.ftl" as boilerplate>

<@boilerplate.boilerplate>
     <header class="d-flex py-2 my-2 ps-2">
         <p>Welcome</p>
     </header>
     <main>
        <h2>You can only see this page if you are logged in!</h2>
        <br>
        <br>
        <h2><a href="/logout">Logout</a></h2>
        <br>
        <br>
        <h2>Can you see assets located outside private?</h2>
        <img src="assets/site/dog.jpg" class="img-fluid" alt="a dog">
        <p> Photo by <a href="https://unsplash.com/@chrismcintosh?utm_source=unsplash&utm_medium=referral&utm_content=creditCopyText">Chris McIntosh</a>
            on <a href="https://unsplash.com/?utm_source=unsplash&utm_medium=referral&utm_content=creditCopyText">Unsplash</a></p>
     </main>
    <#-- From there, add custom scripts as required  -->
    <script src="javascript/script.js"></script>
    <#-- End scripts customization  -->

</@boilerplate.boilerplate>