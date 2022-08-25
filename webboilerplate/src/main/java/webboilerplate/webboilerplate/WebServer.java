package webboilerplate.webboilerplate;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.http.HttpHeaders;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.impl.HttpRequestHead;
import io.vertx.core.json.JsonObject;
import io.vertx.core.spi.observability.HttpRequest;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.StaticHandler;
import io.vertx.ext.web.templ.freemarker.FreeMarkerTemplateEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebServer extends AbstractVerticle {
    public static final Logger logger = LoggerFactory.getLogger(WebServer.class);
    private final static int PORT = 8080;
    private final static String TEMPLATESPREFIX = "src/main/resources/webroot/templates/";

    private static FreeMarkerTemplateEngine engine;
    private final static String INDEX = "index.ftl";
    private final static String PAGE = "page.ftl";
    private final static String LOGIN = "login.ftl";

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

        //Configure static files
        router.route().handler(StaticHandler.create());
        //Start listening on the PORT specified
        server.requestHandler(router).listen(PORT);
        logger.info("Web server listening on {}",PORT);

        //Handle requests:
        router.get().path("/").handler(this::index);
        router.get().path("/home").handler(this::index);
        router.get().path("/index").handler(this::index);
        router.get().path("/page").handler(this::page);
        router.get().path("/login").handler(this::login);
    }

    private void login(RoutingContext ctx) {
        JsonObject data = new JsonObject()
                .put("sitename","a super site!");
        engine.render(data, TEMPLATESPREFIX+LOGIN,res->{
            if(res.succeeded()){
                ctx.response().end(res.result());
            }else{
                ctx.fail(res.cause());
            }
        });
    }

    private void page(RoutingContext ctx) {
        JsonObject data = getRequestEntries(ctx);
        engine.render(data, TEMPLATESPREFIX+PAGE,res->{
            if(res.succeeded()){
                ctx.response().end(res.result());
            }else{
                ctx.fail(res.cause());
            }
        });
    }

    private void index(RoutingContext ctx) {
        JsonObject data = new JsonObject()
                .put("name","meucciFra")
                .put("path",ctx.request().path());
        engine.render(data, TEMPLATESPREFIX+INDEX, res->{
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
