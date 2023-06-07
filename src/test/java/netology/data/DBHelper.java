package netology.data;

import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.*;

public class DBHelper {
    private static QueryRunner runner = new QueryRunner();

    private DBHelper() {
    }

    private static String url = System.getProperty("dbUrl");
    private static String user = System.getProperty("dbUser");
    private static String pass = System.getProperty("dbPassword");

    @SneakyThrows
    public static Connection getConnection() {

        return DriverManager.getConnection(url, user, pass);
    }

    @SneakyThrows
    public static String getStatusForDebit() {
        var conn = getConnection();
        var status = "SELECT status FROM payment_entity ORDER BY created DESC LIMIT 1;";
        var statusName = runner.query(conn, status, new ScalarHandler<String>());
        return statusName;
    }

    @SneakyThrows
    public static String getStatusForCredit() {
        var conn = getConnection();
        var status = "SELECT status FROM credit_request_entity ORDER BY created DESC LIMIT 1;";
        var statusName = runner.query(conn, status, new ScalarHandler<String>());
        return statusName;
    }

    @SneakyThrows
    public static boolean isOrderEntityTableEmpty() {
        var query = "SELECT COUNT(*) FROM order_entity";
        var conn = getConnection();
        var statement = conn.createStatement();
        var resultSet = statement.executeQuery(query);
        resultSet.next();
        var count = resultSet.getInt(1);
        return count == 0;
    }


    @SneakyThrows
    public static void clearDbTable() {
        var conn = getConnection();

        runner.execute(conn, "DELETE FROM payment_entity;");
        runner.execute(conn, "DELETE FROM order_entity;");
        runner.execute(conn, "DELETE FROM credit_request_entity;");
    }


}
