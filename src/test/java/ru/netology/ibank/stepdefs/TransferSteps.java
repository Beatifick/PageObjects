package ru.netology.ibank.stepdefs;

import io.cucumber.java.ru.*;
import ru.netology.ibank.data.DataHelper;
import ru.netology.ibank.data.DataHelper.CardInfo;
import ru.netology.ibank.page.DashboardPage;
import ru.netology.ibank.page.LoginPage;
import ru.netology.ibank.page.TransferPage;
import ru.netology.ibank.page.VerificationPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TransferSteps {

    private LoginPage loginPage;
    private VerificationPage verificationPage;
    private DashboardPage dashboardPage;
    private TransferPage transferPage;

    private int fromCardBalanceBefore;
    private int toCardBalanceBefore;

    private CardInfo firstCard = DataHelper.getFirstCard();
    private CardInfo secondCard = DataHelper.getSecondCard();

    @Дано("пользователь открывает страницу авторизации {string}")
    public void openLoginPage(String url) {
        loginPage = open(url, LoginPage.class);
    }

    @И("пользователь вводит логин {string} и пароль {string}")
    public void enterLoginAndPassword(String login, String password) {
        verificationPage = loginPage.login(login, password);
    }

    @И("пользователь вводит код подтверждения {string}")
    public void enterVerificationCode(String code) {
        dashboardPage = verificationPage.verify(code);
    }

    @Когда("пользователь выбирает карту {string} для пополнения")
    public void selectCardToDeposit(String cardName) {
        transferPage = dashboardPage.selectCardToDeposit(secondCard);
    }

    @И("пользователь переводит {int} с карты {string}")
    public void transferAmountFromCard(int amount, String fromCardName) {
        fromCardBalanceBefore = dashboardPage.getCardBalance(firstCard);
        toCardBalanceBefore = dashboardPage.getCardBalance(secondCard);

        transferPage.transfer(amount, firstCard.getNumber());

        dashboardPage = transferPage.returnToDashboard();
    }

    @Тогда("баланс карты {string} уменьшается на {int}")
    public void balanceDecreases(String cardName, int amount) {
        int actualBalance = dashboardPage.getCardBalance(firstCard);
        assertEquals(fromCardBalanceBefore - amount, actualBalance);
    }

    @И("баланс карты {string} увеличивается на {int}")
    public void balanceIncreases(String cardName, int amount) {
        int actualBalance = dashboardPage.getCardBalance(secondCard);
        assertEquals(toCardBalanceBefore + amount, actualBalance);
    }
}
