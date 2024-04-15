package test;

import com.codeborne.selenide.logevents.SelenideLogger;
import data.CardModel;
import helper.DBHelper;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import page.Home;
import page.Payment;

import static com.codeborne.selenide.Selenide.open;
import static helper.DataHelper.validInfo;

public class SQLTest {
    CardModel data;
    Home home;

    @BeforeEach
    public void connect() {
        open("http://localhost:8080/");
        data = validInfo();
        home = new Home();
    }

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll(){
        SelenideLogger.removeListener("allure");
    }

    @Test
    @DisplayName("APPROVED")
    public void checkPaymentApprovedStatus() {
        home.payment();
        Payment payment = new Payment();
        payment.checkFullCardInfo(0, data.getMonth(), data.getYear(), data.getName(), data.getCvc());
        payment.checkAcceptedCardData();
        DBHelper.assertStatusPaymentApproved();
    }

    @Test
    @DisplayName("DECLINED")
    public void checkPaymentDeclinedStatus() {
        home.payment();
        Payment payment = new Payment();
        payment.checkFullCardInfo(1, data.getMonth(), data.getYear(), data.getName(), data.getCvc());
        payment.checkAcceptedCardData();
        DBHelper.assertStatusPaymentDeclined();
    }

    @Test
    @DisplayName("APPROVED Кредит")
    public void checkCreditPaymentApprovedStatus() {
        home.payment();
        Payment payment = new Payment();
        payment.checkFullCardInfo(0, data.getMonth(), data.getYear(), data.getName(), data.getCvc());
        payment.checkAcceptedCardData();
        DBHelper.assertStatusCreditApproved();
    }

    @Test
    @DisplayName("DECLINED Кредит")
    public void checkCreditPaymentDeclinedStatus() {
        home.payment();
        Payment payment = new Payment();
        payment.checkFullCardInfo(1, data.getMonth(), data.getYear(), data.getName(), data.getCvc());
        payment.checkAcceptedCardData();
        DBHelper.assertStatusCreditDeclined();
    }
}