# Eclipse-Vert.x-Apache-Freemarker-Bootstrap5 boilerplate of a simple Web Application

This is intended to be a boilerplate for a (non-professional as I am) developer that decides to embrace the power of Vert.x and build a simple web application. 

## Background
I was studying Vert.x because I was looking for something powerfull, immediate to undertand, easy to scale and without overwelming Notations or the additonal complication in having Tomcat up and running in my IDE (I'm using Intellij Idea community edition, do you get my point right?!).
I found from the beginning Vert.x very tailored for my brain and it's so easy to have a service (any kind of) up and running that looks kinda of magic.
So let's play with it...

## What I did 
I started from the Eclips Vert.x [application generator](https://start.vertx.io/) that gives all the code and Gradle settings necessary to have a working system.
I tried to expand this powerfull starting point with the following customizations:

1. Rewrite the MainVerticle to be a kind of Verticle deployer/coordinator class. In fact, the standard template start the webserver in the MainVerticle class but this was a bit confusing for me, at the first glance.
   The main verticle should be, in my humble opinion, where the magic of services happens.
   
2. Create a structure of folders that can be used by Verti.x with the Static Handler.
   ```
       //  Configure static files
       router.route().handler(StaticHandler.create());
    ```
   This is necessary to serve static files (for example **.css** and **.js**) in separate file and it requires to put the static contents in the webroot folder under resources folder.
   All of them should be created manually if you don't start from this template. I spent a couple of hours before to understand this simple rule.
   
3. Get Freemarker working properly. 
   1. The dependency is added by [application generator](https://start.vertx.io/) if the option is selected but that was not enough because you need at least one **.ftl** file to play with
   2. I added the **boilerplate.ftl** (a Freemarker page) this is actually a kind of **importer** of Freemarker Macros living in other **.ftl** files (one for the head, one for the general scripts, one for the footer, etc...)
   
4. The HTML behind the above mentioned boilerplate and relative macros is a composition of [Bootstrap5](https://getbootstrap.com/docs/5.0/getting-started/introduction/) template and this one [htmlboilerplates](https://htmlboilerplates.com/) that added [jQuery](https://jquery.com/) but also a good starting point for the cookies code

5. Added a simple form management on a different route that should clarify how to get form variable setting up the routeter to use ```BodyHandler``` 
   ```
      //Configure body handler to enable multipart form data parsing
      router.route().handler(bodyHandler);
   ```
   
5. Introduced session 
   ```
      //Enable session
      router.route().handler(SessionHandler.create(LocalSessionStore.create(vertx)));
   ``` 
   generation and usage (see how session variable is generated in the index route and used in page route.

6. Set the target and source compatibility to Java 14 to get benefits from the last Switch constructor in point below
   ```
      java {
        sourceCompatibility = JavaVersion.VERSION_14
        targetCompatibility = JavaVersion.VERSION_14
      }   
   ``` 
7. Added the ResourceBundle usage for Localization with LabelsBundle.properties and LabelsBundle_it.properties to translate the welcome message in Italian if the browser first language preference is Italian
   ```
     //Initialize the resource Bundle for this request
     ResourceBundle labels = null;
     //GET THE FIRST LOCALE PREFERENCE
     LanguageHeader language = ctx.acceptableLanguages().get(0); //GET THE FIRST PREFERENCE
     //use Switch to get the lables translated base don Locale preference
     labels = switch (language.tag()) {
         case "it" -> ResourceBundle.getBundle("LabelsBundle", Locale.ITALIAN);
         default ->ResourceBundle.getBundle("LabelsBundle", Locale.ENGLISH);
     };
     data.put("sitewelcome",labels.getString("site.welcome"));
   ``` 
## Next steps
Share it... Done  
Improve it... On going
