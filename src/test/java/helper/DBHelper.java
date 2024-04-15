package helper;

import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.DriverManager;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DBHelper {
    private static QueryRunner request;
    private static Connection connection;
    private static final String url = System.getProperty("urlDB");
    private static final String user = System.getProperty("userDB");
    private static final String password = System.getProperty("passwordDB");

    @SneakyThrows
    public static void setupSQL() {
        request = new QueryRunner();
        connection = DriverManager.getConnection(url, user, password);
    }

    @SneakyThrows
    public static String getIdPayment() {
        setupSQL();
        var data = "SELECT payment_id FROM order_entity ORDER BY created DESC limit 1";
        return request.query(connection, data, new ScalarHandler<>());
    }

    @SneakyThrows
    public static String getIdCreditPayment() {
        setupSQL();
        var data = "SELECT credit_id FROM order_entity ORDER BY created DESC limit 1";
        return request.query(connection, data, new ScalarHandler<>());
    }

    @SneakyThrows
    public static String getPaymentStatus(String paymentId) {
        setupSQL();
        var data = "SELECT status FROM payment_entity WHERE transaction_id = ?";
        return request.query(connection, data, new ScalarHandler<>(), paymentId);
    }

    @SneakyThrows
    public static String getCreditPaymentStatus(String paymentId) {
        setupSQL();
        var data = "SELECT status FROM credit_request_entity WHERE bank_id = ?";
        return request.query(connection, data, new ScalarHandler<>(), paymentId);
    }

    public static void assertStatusPaymentApproved() {
        String id = DBHelper.getIdPayment();
        String actual = DBHelper.getPaymentStatus(id);
        String expected = "APPROVED";
        assertEquals(expected, actual);
    }

    public static void assertStatusPaymentDeclined() {
        String id = DBHelper.getIdPayment();
        String actual = DBHelper.getPaymentStatus(id);
        String expected = "DECLINED";
        assertEquals(expected, actual);
    }

    public static void assertStatusCreditApproved() {
        String id = DBHelper.getIdCreditPayment();
        String actual = DBHelper.getCreditPaymentStatus(id);
        String expected = "APPROVED";
        assertEquals(expected, actual);
    }

    public static void assertStatusCreditDeclined() {
        String id = DBHelper.getIdCreditPayment();
        String actual = DBHelper.getCreditPaymentStatus(id);
        String expected = "DECLINED";
        assertEquals(expected, actual);
    }
}