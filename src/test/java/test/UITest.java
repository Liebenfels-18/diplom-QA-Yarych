package test;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import data.CardModel;
import helper.DataHelper;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import org.openqa.selenium.chrome.ChromeOptions;
import page.CreditPayment;
import page.Home;
import page.Payment;

import java.util.HashMap;
import java.util.Map;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.sleep;
import static helper.DataHelper.validInfo;

public class UITest {

    CardModel data;
    Home home;

    @BeforeEach
    public void prepare() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");

        Map<String, Object> prefs = new HashMap<String, Object>();
        prefs.put("credentials_enable_service", false);
        prefs.put("profile.password_manager_enabled", false);
        options.setExperimentalOption("prefs", prefs);
        Configuration.browserCapabilities=options;
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
    @DisplayName("Отправка формы с валидными данными")//1
    public void testValidInfo() {
        Payment payment = home.payment();
        payment.checkFullCardInfo(0, data.getMonth(), data.getYear(), data.getName(), data.getCvc());
        payment.checkAcceptedCardData();
    }

    @Test
    @DisplayName("Отправка формы с картой со статусом DECLINED")//2
    public void testDeclinedCard() {
        Payment payment = home.payment();
        payment.checkFullCardInfo(1, data.getMonth(),data.getYear(), data.getName(), data.getCvc());
        payment.checkDeclinedCardData();
    }

    @Test
    @DisplayName("Отправка пустой формы")//3
    public void testEmptyInfo() {
        Payment payment = home.payment();
        payment.checkAllFormsEmpty();
    }

    @Test
    @DisplayName("Отправка формы с незаполненным полем Месяц")//4
    public void testEmptyMonth() {
        Payment payment = home.payment();
        payment.checkFullCardInfo(0, null, data.getYear(), data.getName(), data.getCvc());
        payment.checkEmptyMonthField();
    }

    @Test
    @DisplayName("Отправка формы с незаполненным полем Год")//5
    public void testEmptyYear() {
        Payment payment = home.payment();
        payment.checkFullCardInfo(0, data.getMonth(), null, data.getName(), data.getCvc());
        payment.checkEmptyYearField();
    }

    @Test
    @DisplayName("Отправка формы с незаполненным полем Имя")//6
    public void testEmptyOwner() {
        Payment payment = home.payment();
        payment.checkFullCardInfo(0, data.getMonth(), data.getYear(), null, data.getCvc());
        payment.checkEmptyOwnerField();
    }

    @Test
    @DisplayName("Отправка формы с незаполненным полем CVC")//7
    public void testEmptyCVC() {
        Payment payment = home.payment();
        payment.checkFullCardInfo(0, data.getMonth(), data.getYear(), data.getName(), null);
        payment.checkEmptyCVCField();
    }

    @Test
    @DisplayName("Отправка формы с незаполненным полем Номер карты")//8
    public void testEmptyNumberCard() {
        Payment payment = home.payment();
        payment.checkFullCardInfo(3, data.getMonth(), data.getYear(), data.getName(), data.getCvc());
        payment.checkWrongNumberCardField();
    }

    @Test
    @DisplayName("Отправка формы с коротким номером карты")//9
    public void testShortCardNumber() {
        Payment payment = home.payment();
        payment.checkFullCardInfo(2, data.getMonth(), data.getYear(), data.getName(), data.getCvc());
        payment.checkWrongNumberCardField();
    }

    @Test
    @DisplayName("Отправка формы с неверно заполненным полем Месяц")//10
    public void testWrongMonth() {
        Payment payment = home.payment();
        payment.checkFullCardInfo(0, DataHelper.getMonth(-2), data.getYear(), data.getName(), data.getCvc());
        payment.checkWrongMonthField();
    }

    @Test
    @DisplayName("Отправка формы с неверно заполненным полем Год")//11
    public void testWrongYear() {
        Payment payment = home.payment();
        payment.checkFullCardInfo(0, data.getMonth(), DataHelper.getYear(-1), data.getName(), data.getCvc());
        payment.checkWrongYearField();
    }

    @Test
    @DisplayName("Отправка формы с тире в поле Имя")//12
    public void testNameWithDash() {
        Payment payment = home.payment();
        String name = "Petrova Anna-Maria";
        payment.checkFullCardInfo(0, data.getMonth(), data.getYear(), name, data.getCvc());
        payment.checkAcceptedCardData();
    }

    @Test
    @DisplayName("Отправка формы с полем Имя на кирилице")//13
    public void testCyrillikSymbolsInName() {
        Payment payment = home.payment();
        String name = "Никита Ярыч";
        payment.checkFullCardInfo(0, data.getMonth(), data.getYear(), name, data.getCvc());
        payment.checkWrongOwnerField();
    }

    @Test
    @DisplayName("Отправка формы с цифрами в поле Имя")//14
    public void testNameWithNumbers() {
        Payment payment = home.payment();
        String name = "Yarych9553";
        payment.checkFullCardInfo(0, data.getMonth(), data.getYear(), name, data.getCvc());
        payment.checkWrongOwnerField();
    }

    @Test
    @DisplayName("Отправка формы со спец.символами в поле Имя")//29
    public void testNameWithSpecSymbols() {
        Payment payment = home.payment();
        String name = "|/''#$%^&*(@#!_)(+=*";
        payment.checkFullCardInfo(0, data.getMonth(), data.getYear(), name, data.getCvc());
        payment.checkWrongOwnerField();
    }

    @Test
    @DisplayName("Отправка формы Кредит с валидными данными")//15
    public void testValidCreditData() {
        CreditPayment creditRequest = home.creditPayment();
        creditRequest.checkFullCardInfo(0, data.getMonth(), data.getYear(), data.getName(), data.getCvc());
        creditRequest.checkAcceptedCardData();
    }

    @Test
    @DisplayName("Отправка формы Кредит с картой со статусом DECLINED")//16
    public void testInvalidCreditData() {
        CreditPayment creditRequest = home.creditPayment();
        creditRequest.checkFullCardInfo(1, data.getMonth(), data.getYear(), data.getName(), data.getCvc());
        creditRequest.checkDeclinedCardData();
    }

    @Test
    @DisplayName("Отправка пустой формы Кредит")//17
    public void testEmptyCreditData() {
        CreditPayment creditRequest = home.creditPayment();
        creditRequest.checkAllFormsEmpty();
    }

    @Test
    @DisplayName("Отправка формы Кредит с незаполненным полем Месяц")//18
    public void testEmptyMonthFieldInCredit() {
        CreditPayment creditRequest = home.creditPayment();
        creditRequest.checkFullCardInfo(0, null, data.getYear(), data.getName(), data.getCvc());
        creditRequest.checkEmptyMonthField();
    }

    @Test
    @DisplayName("Отправка формы Кредит с незаполненным полем Год")//19
    public void testEmptyYearFieldInCredit() {
        CreditPayment creditRequest = home.creditPayment();
        creditRequest.checkFullCardInfo(0, data.getMonth(), null, data.getName(), data.getCvc());
        creditRequest.checkEmptyYearField();
    }

    @Test
    @DisplayName("Отправка формы Кредит с незаполненным полем Имя")//20
    public void testEmptyOwnerFieldInCredit() {
        CreditPayment creditRequest = home.creditPayment();
        creditRequest.checkFullCardInfo(0, data.getMonth(), data.getYear(), null, data.getCvc());
        creditRequest.checkEmptyOwnerField();
    }

    @Test
    @DisplayName("Отправка формы Кредит с незаполненным полем CVC")//21
    public void testEmptyCVCFieldInCredit() {
        CreditPayment creditRequest = home.creditPayment();
        creditRequest.checkFullCardInfo(0, data.getMonth(), data.getYear(), data.getName(), null);
        creditRequest.checkEmptyCVCField();
    }

    @Test
    @DisplayName("Отправка формы Кредит с незаполненным полем Номер карты")//22
    public void testEmptyCardNumberFieldInCredit() {
        CreditPayment creditRequest = home.creditPayment();
        creditRequest.checkFullCardInfo(3, data.getMonth(), data.getYear(), data.getName(), data.getCvc());
        creditRequest.checkWrongNumberCardField();
    }

    @Test
    @DisplayName("Отправка формы Кредит с коротким номером карты")//23
    public void testShortCardNumberInCredit() {
        CreditPayment creditRequest = home.creditPayment();
        creditRequest.checkFullCardInfo(2, data.getMonth(), data.getYear(), data.getName(), data.getCvc());
        creditRequest.checkWrongNumberCardField();
    }

    @Test
    @DisplayName("Отправка формы Кредит с неверно заполненным полем Месяц")//24
    public void testWrongMonthInCredit() {
        CreditPayment creditRequest = home.creditPayment();
        creditRequest.checkFullCardInfo(0, DataHelper.getMonth(-2), data.getYear(), data.getName(), data.getCvc());
        creditRequest.checkInvalidMonthField();
    }

    @Test
    @DisplayName("Отправка формы Кредит с неверно заполненным полем Год")//25
    public void testWrongYearInCredit() {
        CreditPayment creditRequest = home.creditPayment();
        creditRequest.checkFullCardInfo(0, data.getMonth(), DataHelper.getYear(-1), data.getName(), data.getCvc());
        creditRequest.checkInvalidYearField();
    }

    @Test
    @DisplayName("Отправка формы Кредит с тире в поле Имя")//26
    public void testNameWithDashInCredit() {
        CreditPayment creditRequest = home.creditPayment();
        String name = "Petrova Anna-Maria";
        creditRequest.checkFullCardInfo(0, data.getMonth(), data.getYear(), name, data.getCvc());
        creditRequest.checkAcceptedCardData();
    }

    @Test
    @DisplayName("Отправка формы Кредит с полем Имя на кирилице")//27
    public void testCyrillikNameInCredit() {
        CreditPayment creditRequest = home.creditPayment();
        String name = "Никита Ярыч";
        creditRequest.checkFullCardInfo(0, data.getMonth(), data.getYear(), name, data.getCvc());
        creditRequest.checkWrongOwnerField();
    }

    @Test
    @DisplayName("Отправка формы Кредит с цифрами в поле Имя")//28
    public void testNameWithNumberInCredit() {
        CreditPayment creditRequest = home.creditPayment();
        String name = "Yarych1243";
        creditRequest.checkFullCardInfo(0, data.getMonth(), data.getYear(), name, data.getCvc());
        creditRequest.checkWrongOwnerField();
    }

    @Test
    @DisplayName("Отправка формы Кредит со спец.символами в поле Имя")//30
    public void testNameWithSpecSymbolsInCredit() {
        CreditPayment creditRequest = home.creditPayment();
        String name = "|/''#$%^&*(@#!_)(+=*";
        creditRequest.checkFullCardInfo(0, data.getMonth(), data.getYear(), name, data.getCvc());
        creditRequest.checkWrongOwnerField();
    }
}