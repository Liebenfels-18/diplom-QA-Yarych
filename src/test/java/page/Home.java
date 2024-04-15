package page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$x;

public class Home {

    private SelenideElement payment = $x("//button[@role =\"button\"]//*[text() = \"Купить\"]");
    private SelenideElement creditPayment = $x("//button[@role =\"button\"]//*[text() = \"Купить в кредит\"]");

    public Payment payment() {
        payment.click();
        return new Payment();
    }

    public CreditPayment creditPayment() {
        creditPayment.click();
        return new CreditPayment();
    }
}