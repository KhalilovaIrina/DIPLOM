package netology.data;

import lombok.SneakyThrows;
import lombok.Value;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.*;

public class DBHelper {
    private static QueryRunner runner = new QueryRunner();

    private DBHelper() {

    }
//    private static String url = System.getProperty("url");
//    private static String username = System.getProperty("username");
//
//    private static String password = System.getProperty("password");

//        @Value
//    public static class InfoForConnection{
//        String url;
//        String user;
//        String password;
//    }
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/app", "app", "pass");
    }

//        public static InfoForConnection mySql(){
//        return new InfoForConnection("jdbc:mysql://localhost:3306/app", "app", "pass");
//    }
//    public static InfoForConnection postgreSql(){
//        return new InfoForConnection("jdbc:postgresql://localhost:5434/app", "app", "pass");
//    }
    @SneakyThrows
    public static String getStatus() {
        var conn = getConnection();
        var status = "SELECT status FROM payment_entity ORDER BY created DESC LIMIT 1;";
        var code = runner.query(conn, status, new ScalarHandler<String>());
        return code;
    }



    @SneakyThrows
    public static boolean isEmptyDB() {
       var query = "SELECT COUNT(*) FROM order_entity" ;
       var conn = getConnection();
        Statement statement = conn.createStatement();
           ResultSet resultSet = statement.executeQuery(query);
           resultSet.next();
           int count = resultSet.getInt(1);
           return count==0;
    }


    @SneakyThrows
    public static void removeDB() {
        var conn = getConnection();

        runner.execute(conn, "DELETE FROM payment_entity;");
        runner.execute(conn, "DELETE FROM order_entity;");
        runner.execute(conn, "DELETE FROM credit_request_entity;");
    }


}
