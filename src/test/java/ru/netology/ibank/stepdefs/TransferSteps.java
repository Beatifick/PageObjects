package ru.netology.ibank.stepdefs;

import io.cucumber.java.ru.Допустим;
import io.cucumber.java.ru.И;
import io.cucumber.java.ru.Когда;
import io.cucumber.java.ru.Тогда;
import ru.netology.ibank.page.DashboardPage;
import ru.netology.ibank.page.LoginPage;
import ru.netology.ibank.page.TransferPage;
import ru.netology.ibank.page.VerificationPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TransferSteps {

    private final LoginPage loginPage = new LoginPage();
    private final VerificationPage verificationPage = new VerificationPage();
    private final DashboardPage dashboardPage = new DashboardPage();
    private final TransferPage transferPage = new TransferPage();

    private int balanceBeforeFrom;
    private int balanceBeforeTo;
    private int transferAmount;
    private String fromCardNumber;
    private String toCardNumber;

    @Допустим("пользователь залогинен")
    public void userLoggedIn() {
        open("http://localhost:9999");
        loginPage.login("vasya", "qwerty123");
        verificationPage.verify("12345");
    }

    @Когда("пользователь переводит {int} с карты {string} на карту {string}")
    public void transferAmountFromCardToCard(int amount, String fromCard, String toCard) {
        fromCardNumber = fromCard;
        toCardNumber = toCard;
        transferAmount = amount;

        balanceBeforeFrom = dashboardPage.getCardBalance(fromCard);
        balanceBeforeTo = dashboardPage.getCardBalance(toCard);

        dashboardPage.selectCardToTransfer(toCard);
        transferPage.transfer(fromCard, amount);
    }

    @Тогда("баланс карты {string} уменьшается на {int}")
    public void balanceDecreases(String cardNumber, int expected) {
        int actual = dashboardPage.getCardBalance(cardNumber);
        if (cardNumber.equals(fromCardNumber)) {
            assertEquals(balanceBeforeFrom - transferAmount, actual);
        } else {
            throw new RuntimeException("Неправильная карта для уменьшения баланса: " + cardNumber);
        }
    }

    @И("баланс карты {string} увеличивается на {int}")
    public void balanceIncreases(String cardNumber, int expected) {
        int actual = dashboardPage.getCardBalance(cardNumber);
        if (cardNumber.equals(toCardNumber)) {
            assertEquals(balanceBeforeTo + transferAmount, actual);
        } else {
            throw new RuntimeException("Неправильная карта для увеличения баланса: " + cardNumber);
        }
    }

    @И("показываем баланс всех карт")
    public void showAllCardBalances() {
        dashboardPage.printAllBalances();
    }
}
