package webboilerplate.webboilerplate;

import io.vertx.core.Vertx;
import io.vertx.mysqlclient.MySQLConnectOptions;
import io.vertx.mysqlclient.MySQLPool;
import io.vertx.sqlclient.PoolOptions;
import io.vertx.sqlclient.SqlClient;

import java.util.Properties;

public class DataBase {
    private static final Properties prop = PropertiesLoader.getPropertiesLoaderInstance().loader();
    private String ipserver = prop.getProperty("db.ipserver");
    private Integer port = Integer.parseInt(prop.getProperty("db.port"));
    private String databasename = prop.getProperty("db.databasename");
    private String user=prop.getProperty("db.user");
    private String password=prop.getProperty("db.password");
    private MySQLConnectOptions connectOptions = null;
    private PoolOptions poolOptions = null;
    private static DataBase database = null;
    private DataBase(){}

    public static DataBase getDatabaseInstance(){
        if (database==null){
            database = new DataBase();
        }
        return database;
    }

    private MySQLConnectOptions setConnectionOptions(){
        if(connectOptions == null){
            connectOptions = new MySQLConnectOptions()
                    .setPort(port)
                    .setHost(ipserver)
                    .setDatabase(databasename)
                    .setUser(user)
                    .setPassword(password);
        }
        return connectOptions;
    }

    private PoolOptions setPoolOptionsOptions(){
        if (poolOptions==null){
            poolOptions = new PoolOptions()
                    .setMaxSize(5);
        }
        return poolOptions;
    }

    public SqlClient getSqlClient(Vertx vertx){
        // Set ConnectionOptions
        connectOptions = setConnectionOptions();
        // Set PoolOptions
        poolOptions = setPoolOptionsOptions();
        // Create the client pool
        return MySQLPool.client(vertx, connectOptions, poolOptions);
    }
}
