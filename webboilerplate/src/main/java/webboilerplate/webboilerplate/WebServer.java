package webboilerplate.webboilerplate;

import freemarker.core.Environment;
import freemarker.template.Configuration;
import freemarker.template.Template;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.http.HttpHeaders;
import io.vertx.core.http.HttpServer;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.LanguageHeader;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.Session;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.SessionHandler;
import io.vertx.ext.web.handler.StaticHandler;
import io.vertx.ext.web.sstore.LocalSessionStore;
import io.vertx.ext.web.templ.freemarker.FreeMarkerTemplateEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.ResourceBundle;

public class WebServer extends AbstractVerticle {
    public static final Logger logger = LoggerFactory.getLogger(WebServer.class);
    private final static int PORT = 8080;
    private final static String TEMPLATESPREFIX = "src/main/resources/webroot/templates/";

    private static FreeMarkerTemplateEngine engine;
    private final static String INDEX = "index.ftl";
    private final static String PAGE = "page.ftl";
    private final static String LOGIN = "login.ftl";
    private final static String ATTRLOC = "attributelocalization.ftl";
    private final static String FREEMARKERLOCEN = "freemarkerlocalization_en_EN.ftl";
    private final static String FREEMARKERLOCIT = "freemarkerlocalization_it_IT.ftl";

    @Override
    public void start(Promise<Void> startPromise) throws Exception {
        logger.info("Starting web server deployment");
        //Create the Freemarker Engine
        engine = FreeMarkerTemplateEngine.create(vertx);
        //Create the HTTP Server Engine
        final HttpServer server = vertx.createHttpServer();
        //Create the router
        final Router router = Router.router(vertx);
        //Create the HTTP Body Handler that is used to decode easly the POST and PUT HTTP requests
        final BodyHandler bodyHandler = BodyHandler.create();
        //Configure body handler to enable multipart form data parsing
        router.route().handler(bodyHandler);
        //Configure static files
        router.route().handler(StaticHandler.create());
        //Start listening on the PORT specified
        server.requestHandler(router).listen(PORT);
        logger.info("Web server listening on {}",PORT);
        //Enable session
        router.route().handler(SessionHandler.create(LocalSessionStore.create(vertx)));

        //Handle requests:
        router.get().path("/").handler(this::index);
        router.get().path("/home").handler(this::index);
        router.get().path("/index").handler(this::index);
        router.get().path("/page").handler(this::page);
        router.get().path("/login").handler(this::login);
        router.post().path("/loginForm").handler(this::loginForm);
        router.get().path("/attributelocalization").handler(this::attributeLocalization);
        router.get().path("/freemarkerlocalization").handler(this::freemarkerlocalization);
    }


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

    private void login(RoutingContext ctx) {
        //Preload data with non Locale dependent values
        JsonObject data = new JsonObject();
        renderWithTemplate(data,LOGIN,ctx);
    }

    private void loginForm(RoutingContext ctx) {
        // handle the form
        String email = ctx.request().getParam("email");
        String password = ctx.request().getParam("password"); //HASH THAT!
        String checkbox = ctx.request().getParam("checkbox");
        logger.info("Post parameters are {},{},{}",email,password,checkbox);
        ctx.response().putHeader(HttpHeaders.CONTENT_TYPE, "text/plain");
        // note the form attribute matches the html form element name.
        ctx.response().end("Post parameters are: "+ email +" "+ password +" "+ checkbox);
        //TODO: do something more useful
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
         *  It seems that any trial to change locale doesn't work so I abandoned this solution in favor of the simple switch above
         */
        //use Switch to get the lables translated base don Locale preference
        String templateLocalized = switch (language.tag()) {
            case "it" -> FREEMARKERLOCIT;
            default -> FREEMARKERLOCEN;
        };
        renderWithTemplate(data,templateLocalized,ctx);
    }

    //AUX METHODS
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
