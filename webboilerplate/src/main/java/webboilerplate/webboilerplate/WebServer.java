package webboilerplate.webboilerplate;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.http.HttpServer;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
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
                    .put("name","Francesco")
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
            JsonObject data = new JsonObject()
                    .put("useragent",ctx.request().headers().get("User-Agent"))
                    .put("path",ctx.request().path());
            logger.info(data.getString("useragent"));
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
}