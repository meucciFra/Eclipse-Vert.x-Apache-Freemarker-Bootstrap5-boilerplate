package webboilerplate.webboilerplate;

import io.vertx.core.AbstractVerticle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainVerticle extends AbstractVerticle {

  public static final Logger logger = LoggerFactory.getLogger(MainVerticle.class);
  @Override
  public void start() throws Exception {
    super.start();
    logger.info("Starting verticles deployment");
    vertx.deployVerticle("webboilerplate.webboilerplate.WebServer");
    //vertx.deployVerticle("webboilerplate.webboilerplate.DBVerticle");
  }
}

