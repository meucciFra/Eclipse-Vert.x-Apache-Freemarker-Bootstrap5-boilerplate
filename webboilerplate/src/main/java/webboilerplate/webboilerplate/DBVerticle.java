package webboilerplate.webboilerplate;

import io.vertx.core.AbstractVerticle;
import io.vertx.ext.auth.VertxContextPRNG;
import io.vertx.ext.auth.sqlclient.SqlAuthentication;
import io.vertx.ext.auth.sqlclient.SqlAuthenticationOptions;
import io.vertx.ext.auth.sqlclient.SqlAuthorizationOptions;
import io.vertx.sqlclient.Row;
import io.vertx.sqlclient.RowSet;
import io.vertx.sqlclient.SqlClient;
import io.vertx.sqlclient.Tuple;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DBVerticle extends AbstractVerticle {

    public static final Logger logger = LoggerFactory.getLogger(DBVerticle.class);
    @Override
    public void start() throws Exception {
        super.start();
        logger.info("Starting DB Verticle");

        //Enable SQL Authentication
        SqlAuthenticationOptions authenticationOptions = new SqlAuthenticationOptions();
        authenticationOptions.getAuthenticationQuery();
        logger.info(authenticationOptions.getAuthenticationQuery());

        SqlAuthorizationOptions authorizationOptions = new SqlAuthorizationOptions();
        authorizationOptions.getPermissionsQuery();
        authorizationOptions.getRolesQuery();
        logger.info(authorizationOptions.getPermissionsQuery());
        logger.info(authorizationOptions.getRolesQuery());

        //Get the SQL Client Instance (Singletone design pattern used)
        SqlClient sqlClient = DataBase.getDatabaseInstance().getSqlClient(vertx);

        //Set the authentication provider
        SqlAuthentication authProvider = SqlAuthentication.create(sqlClient, authenticationOptions);

        ////////////// Setup database for a test /////////////////////////
        prepareDatabase(sqlClient, authProvider);
        //////////////////////////////////////////////////////////////////

    }

    //Temp Methods to test Database
    private static void prepareDatabase(SqlClient sqlClient, SqlAuthentication sqlAuthentication){

        String user = "tim";
        String password = "sausages";


        String hash = sqlAuthentication.hash( //Hashed Password
                "pbkdf2", // hashing algorithm (OWASP recommended)
                VertxContextPRNG.current().nextString(32), // secure random salt
                password// password in clear text
        );

        // save to the database
        sqlClient
                .preparedQuery("INSERT INTO users (username, password) VALUES (?, ?)")
                .execute(Tuple.of(user, hash),  ar->{
                    if (ar.succeeded()) {
                        RowSet<Row> rows = ar.result();
                        logger.info("Success: " + rows.rowCount());
                    } else {
                        logger.info("Failure: " + ar.cause().getMessage());
                    }
                });

        sqlClient
                .preparedQuery("INSERT INTO users_roles (username, role) VALUES (?, ?)")
                .execute(Tuple.of(user, "dev"),  ar->{
                    if (ar.succeeded()) {
                        RowSet<Row> rows = ar.result();
                        logger.info("Success: " + rows.rowCount());
                    } else {
                        logger.info("Failure: " + ar.cause().getMessage());
                    }
                });

        sqlClient
                .preparedQuery("INSERT INTO users_roles (username, role) VALUES (?, ?)")
                .execute(Tuple.of(user, "admin"),  ar->{
                    if (ar.succeeded()) {
                        RowSet<Row> rows = ar.result();
                        logger.info("Success: " + rows.rowCount());
                    } else {
                        logger.info("Failure: " + ar.cause().getMessage());
                    }
                });

        sqlClient
                .preparedQuery("INSERT INTO roles_perms (role, perm) VALUES (?, ?)")
                .execute(Tuple.of("admin", "full_control"),  ar->{
                    if (ar.succeeded()) {
                        RowSet<Row> rows = ar.result();
                        logger.info("Success: " + rows.rowCount());
                    } else {
                        logger.info("Failure: " + ar.cause().getMessage());
                    }
                });

        sqlClient
                .preparedQuery("INSERT INTO roles_perms (role, perm) VALUES (?, ?)")
                .execute(Tuple.of("dev", "develop_code"),  ar->{
                    if (ar.succeeded()) {
                        RowSet<Row> rows = ar.result();
                        logger.info("Success: " + rows.rowCount());
                    } else {
                        logger.info("Failure: " + ar.cause().getMessage());
                    }
                });
    }

}
