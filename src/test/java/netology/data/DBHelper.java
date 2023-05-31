package netology.data;

import lombok.SneakyThrows;
import lombok.Value;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class DBHelper {
    private static QueryRunner runner = new QueryRunner();

    private DBHelper() {
    }

    private static String url = System.getProperty("dbUrl");

    @SneakyThrows
    public static Connection getConnection() throws SQLException {

        return DriverManager.getConnection(url, "app", "pass");
    }

    @SneakyThrows
    public static String getStatus() {
        var conn = getConnection();
        var status = "SELECT status FROM payment_entity ORDER BY created DESC LIMIT 1;";
        var statusName = runner.query(conn, status, new ScalarHandler<String>());
        return statusName;
    }


    @SneakyThrows
    public static boolean isEmptyDB() {
        var query = "SELECT COUNT(*) FROM order_entity";
        var conn = getConnection();
        Statement statement = conn.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        resultSet.next();
        int count = resultSet.getInt(1);
        return count == 0;
    }


    @SneakyThrows
    public static void removeDB() {
        var conn = getConnection();

        runner.execute(conn, "DELETE FROM payment_entity;");
        runner.execute(conn, "DELETE FROM order_entity;");
        runner.execute(conn, "DELETE FROM credit_request_entity;");
    }


}
