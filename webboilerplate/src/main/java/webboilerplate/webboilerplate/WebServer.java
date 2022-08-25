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
import io.vertx.ext.web.handler.StaticHandler;
import io.vertx.ext.web.templ.freemarker.FreeMarkerTemplateEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebServer extends AbstractVerticle {
    public static final Logger logger = LoggerFactory.getLogger(WebServer.class);
    private final static int PORT = 8080;

    @Override
    public void start(Promise<Void> startPromise) throws Exception {
        logger.info("Starting web server deployment");

        final FreeMarkerTemplateEngine engine = FreeMarkerTemplateEngine.create(vertx);

        final HttpServer server = vertx.createHttpServer();

        final Router router = Router.router(vertx);

        router.get().path("/").handler(ctx->{
            JsonObject data = new JsonObject()
                    .put("name","meucciFra")
                    .put("path",ctx.request().path());
            engine.render(data, "src/main/resources/webroot/templates/index.ftl",res->{
                if(res.succeeded()){
                    ctx.response().end(res.result());
                }else{
                    ctx.fail(res.cause());
                }
            });
        });

        router.get().path("/page").handler(ctx->{
            JsonObject data = getRequestEntries(ctx);
            engine.render(data, "src/main/resources/webroot/templates/page.ftl",res->{
                if(res.succeeded()){
                    ctx.response().end(res.result());
                }else{
                    ctx.fail(res.cause());
                }
            });
        });

        //  Configure static files
        router.route().handler(StaticHandler.create());

        server.requestHandler(router).listen(PORT);
        logger.info("Web server listening on {}",PORT);
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