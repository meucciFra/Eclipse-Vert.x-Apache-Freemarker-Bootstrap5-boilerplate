package webboilerplate.webboilerplate;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.http.HttpHeaders;
import io.vertx.core.http.HttpServer;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.User;
import io.vertx.ext.auth.authorization.*;
import io.vertx.ext.auth.sqlclient.SqlAuthentication;
import io.vertx.ext.auth.sqlclient.SqlAuthenticationOptions;
import io.vertx.ext.auth.sqlclient.SqlAuthorization;
import io.vertx.ext.auth.sqlclient.SqlAuthorizationOptions;
import io.vertx.ext.web.*;
import io.vertx.ext.web.handler.*;
import io.vertx.ext.web.sstore.LocalSessionStore;
import io.vertx.ext.web.templ.freemarker.FreeMarkerTemplateEngine;
import io.vertx.sqlclient.SqlClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class WebServer extends AbstractVerticle {
    public static final Logger logger = LoggerFactory.getLogger(WebServer.class);
    private static final Properties prop = PropertiesLoader.getPropertiesLoaderInstance().loader();
    private final static int PORT = 8080;
    private final static String TEMPLATESPREFIX = "src/main/resources/webroot/templates/";
    private static FreeMarkerTemplateEngine engine;
    private final static String INDEX = "index.ftl";
    private final static String PAGE = "page.ftl";
    private final static String LOGIN = "login.ftl";
    private final static String FORM = "form.ftl";
    private final static String ATTRLOC = "attributelocalization.ftl";
    private final static String FREEMARKERLOCEN = "freemarkerlocalization_en_EN.ftl";
    private final static String FREEMARKERLOCIT = "freemarkerlocalization_it_IT.ftl";

    //PRIVATE PAGES
    private final static String PRIVATE_PAGE = "private/private_page.ftl";

    @Override
    public void start(Promise<Void> startPromise) throws Exception {
        logger.info("Starting web server deployment");

        //Create the Freemarker Engine
        engine = FreeMarkerTemplateEngine.create(vertx);

        //Create the HTTP Server Engine
        final HttpServer server = vertx.createHttpServer();

        //Create the router
        final Router router = Router.router(vertx);

        //Create the HTTP Body Handler that is used to decode easily the POST and PUT HTTP requests
        final BodyHandler bodyHandler = BodyHandler.create();

        // Setup Upload Directory and create a new one if it doesn't exist.
        // Note that the folder is created at the same level of "src" folder
        bodyHandler.setUploadsDirectory("uploads");

        //Configure body handler to enable multipart form data parsing
        router.route().handler(bodyHandler);

        //Configure static files
        router.route().handler(StaticHandler.create());

        //Start listening on the PORT specified
        server.requestHandler(router).listen(PORT);
        logger.info("Web server listening on {}",PORT);

        //Enable session
        router.route().handler(SessionHandler.create(LocalSessionStore.create(vertx)));

        //Get the SQL Client Instance (Singletone design pattern used)
        SqlClient sqlClient = DataBase.getDatabaseInstance().getSqlClient(vertx);

        //Enable SQL Authentication & Authorization
        SqlAuthenticationOptions authenticationOptions = new SqlAuthenticationOptions();
        SqlAuthorizationOptions authorizationOptions = new SqlAuthorizationOptions();
        //Set the authentication provider
        SqlAuthentication authenticationProvider = SqlAuthentication.create(sqlClient, authenticationOptions);
        //Set the authorization provider
        SqlAuthorization authorizationProvider = SqlAuthorization.create(sqlClient,  authorizationOptions);

        //Handle requests:
        router.get().path("/").handler(this::index);
        router.get().path("/home").handler(this::index);
        router.get().path("/index").handler(this::index);
        router.get().path("/page").handler(this::page);
        router.get().path("/form").handler(this::form);
        router.post().path("/formExample").handler(this::formExample);
        router.get().path("/attributelocalization").handler(this::attributeLocalization);
        router.get().path("/freemarkerlocalization").handler(this::freemarkerlocalization);

        //LOGIN WITH AUTH HANDLER PROVIDED BY vert.x api
        router.get().path("/login").handler(this::login);
        //AUTHNETICATED ROUTS
            // Any requests to URI starting '/private/' require login
            router.route("/private/*").handler(RedirectAuthHandler.create(authenticationProvider, "../login"));
            // Serve the static private pages from directory 'private'
            router.route("/private/*").handler(StaticHandler.create().setCachingEnabled(false));
            //router.route("/private/*").handler(StaticHandler.create().setCachingEnabled(false).setWebRoot("private"));

            // Handles the actual login
            router.route("/loginhandler").handler(FormLoginHandler.create(authenticationProvider));
            // Implement logout
            router.route("/logout").handler(context -> {
                context.clearUser();
                // Redirect back to the index page
                context.response().putHeader("location", "/").setStatusCode(302).end();
            });
            router.route("/private/private_page").handler(ctx -> {
                // This will require user to be logged in
                // This will have the value true if user is logged
                if (ctx.user() != null){  //isAuthenticated
                    User user = ctx.user(); //Here I have the user
                    String username = user.principal().getValue("username").toString();
                    authorizationProvider.getAuthorizations(user).onSuccess(done -> {
                                // cache is populated, perform query
                                if (RoleBasedAuthorization.create("admin").match(user)) { //if the user has the role specified here for example "admin", then the RoleBasedAuthorization test passes
                                    logger.info("User: \""+username+"\" has the admin Role");
                                    if(PermissionBasedAuthorization.create("full_control").match(user)){
                                        logger.info("User: \""+username+"\" has the full_control Permission");
                                        //Render page
                                        JsonObject data = new JsonObject();
                                        data.put("user",username)
                                                .put("role","admin")
                                                .put("permission","full_control");
                                        renderWithTemplate(data, PRIVATE_PAGE, ctx);
                                    } else{
                                        logger.info("User: \""+username+"\" does not have the Permission"); //So I decided to redirect to the home page
                                        ctx.response().putHeader("location", "/").setStatusCode(302).end();
                                    }
                                } else {
                                    logger.info("User: \""+username+"\" does not have the Role"); //So I decided to redirect to the home page
                                    ctx.response().putHeader("location", "/").setStatusCode(302).end();
                                }
                    });
                }
            });
    }




    // Methods to serve the contents under specific path
    private void index(RoutingContext ctx) {
        //Preload data with non Locale dependent values
        JsonObject data = new JsonObject();
        //Get the session
        Session session = ctx.session();
        //Create a JsonObject to store session values
        JsonObject dataSession = new JsonObject()
                .put("sessionVar1","hello session");
        //Put a value in the session
        session.put("dataSession", dataSession);
        //Put the session JsonObject in the data JsonObjcet to be displayed in the template
        data.put("session",dataSession);
        //Put some general data to display
        data.put("name","meucciFra").put("path",ctx.request().path());
        renderWithTemplate(data, INDEX, ctx);
    }
    private void page(RoutingContext ctx) {
        //Preload data with non Locale dependent values
        JsonObject data;
        //Get the session
        Session session = ctx.session();
        data = getRequestEntries(ctx);
        //Get JsonObject with session values
        if( session.get("dataSession") != null ){
            JsonObject dataSession = session.get("dataSession");
            //Use session variables
            logger.info("sessionVar1:{}",dataSession.getString("sessionVar1"));
            data.put("session",dataSession);
        }
        //Put the session JsonObject in the data JsonObjcet to be displayed in the template
        renderWithTemplate(data,PAGE,ctx);
    }
    private void form(RoutingContext ctx) {

        //Preload data with non Locale dependent values
        JsonObject data = new JsonObject();
        renderWithTemplate(data,FORM,ctx);
    }
    private void formExample(RoutingContext ctx) {
        //Set to use HTTP chunked encoding (when having to deal with file upload)
        ctx.response().setChunked(true);

        // handle the form
        String name = ctx.request().getParam("name");
        String surname = ctx.request().getParam("surname");
        String email = ctx.request().getParam("email");
        String password = ctx.request().getParam("password");
        String checkbox = ctx.request().getParam("checkbox");
        logger.info("Post parameters are {},{},{}",email,password,checkbox);
        ctx.response().putHeader(HttpHeaders.CONTENT_TYPE, "text/plain");
        // note the form attribute matches the html form element name.

        // handle the file(s)
        for (FileUpload f : ctx.fileUploads()) {
            logger.info("file");
            ctx.response().write("Filename: " + f.fileName());
            ctx.response().write("\n");
            ctx.response().write("Size: " + f.size());
            ctx.response().write("\n");
        }

        ctx.response().end("Other Post parameters are: "+ name +" "+ surname +" "+ email +" "+ password +" "+ checkbox);
        //TODO: do something more useful
    }
    private void login(RoutingContext ctx) {
        ResourceBundle labels = null;
        //GET THE FIRST LOCALE PREFERENCE
        LanguageHeader language = ctx.acceptableLanguages().get(0); //GET THE FIRST PREFERENCE
        //use Switch to get the lables translated base don Locale preference
        labels = switch (language.tag()) {
            case "it" -> ResourceBundle.getBundle("LabelsBundle", Locale.ITALIAN);
            default ->ResourceBundle.getBundle("LabelsBundle", Locale.ENGLISH);
        };
        //Preload data with non Locale dependent values
        JsonObject data = new JsonObject();
        data.put("sitesignin",labels.getString("site.signin"))
                .put("sitesignup",labels.getString("site.signup"));
        renderWithTemplate(data,LOGIN,ctx);
    }
    private void attributeLocalization(RoutingContext ctx) {
        //Preload data with non Locale dependent values
        JsonObject data = new JsonObject();
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
        data.put("value",1000010.72);
        renderWithTemplate(data,ATTRLOC,ctx);
    }
    private void freemarkerlocalization(RoutingContext ctx) {
        //Preload data with non Locale dependent values
        JsonObject data = new JsonObject();
        data.put("value",1000010.72);
        //GET THE FIRST LOCALE PREFERENCE
        LanguageHeader language = ctx.acceptableLanguages().get(0); //GET THE FIRST PREFERENCE
        /** Configuration cfg = engine.unwrap(); //this wraps the configuration but not the environement... not so good!
         *         Configuration conf = engine.unwrap();
         *         Template temp = conf.getTemplate("freemarkerlocalization",Locale.ITALIAN);
         *         String localizedTemplate = temp.getSourceName(); "--> expected to be: freemarkerlocalization_it_IT, I always get: freemarkerlocalization"
         *  It seems that any trial to change locale doesn't work so I abandoned this solution in favor of the simple switch above
         */
        //use Switch to get the lables translated base don Locale preference
        String templateLocalized = switch (language.tag()) {
            case "it" -> FREEMARKERLOCIT;
            default -> FREEMARKERLOCEN;
        };
        renderWithTemplate(data,templateLocalized,ctx);
    }

    //Auxiliaries Methods
    private static void renderWithTemplate(JsonObject data, String templateName, RoutingContext ctx) {
        engine.render(data, TEMPLATESPREFIX+ templateName, res->{
            if(res.succeeded()){
                ctx.response().end(res.result());
            }else{
                ctx.fail(res.cause());
            }
        });
    }
    private static JsonObject getRequestEntries(RoutingContext ctx) {
        JsonObject content = new JsonObject();
        ctx.request().headers().forEach((key, value)->{
            content.put(key,value);
        });
        content.put("RemoteHostName", ctx.request().remoteAddress().hostName());
        content.put("RemoteHostAddress", ctx.request().remoteAddress().hostAddress());
        content.put("RemoteHostPort", ctx.request().remoteAddress().port());
        content.put("path", ctx.request().path());
        ctx.request().params().forEach( (key, value)->{
           content.put(key,value);
        });
        //Log all the entries
        content.forEach(key->{
            logger.info("{}: {}",key.getKey(),content.getString(key.getKey()));
        });

        //Set the data object to be passed to template
        JsonObject data = new JsonObject();
        data.put("content",content);
        return data;
    }
}
