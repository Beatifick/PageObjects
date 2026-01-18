package ru.netology.ibank.stepdefs;

import io.cucumber.java.ru.*;
import ru.netology.ibank.data.DataHelper;
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

    private int fromBalanceBefore;
    private int toBalanceBefore;

    private final DataHelper.CardInfo fromCard = DataHelper.getFirstCard();
    private final DataHelper.CardInfo toCard = DataHelper.getSecondCard();

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
    public void selectCardToDeposit(String ignored) {
        fromBalanceBefore = dashboardPage.getCardBalance(fromCard.getId());
        toBalanceBefore = dashboardPage.getCardBalance(toCard.getId());

        transferPage = dashboardPage.selectCardToDeposit(toCard.getId());
    }

    @И("пользователь переводит {int} с карты {string}")
    public void transferAmount(int amount, String ignored) {
        dashboardPage = transferPage.transfer(amount, fromCard.getNumber());
    }

    @Тогда("баланс карты {string} уменьшается на {int}")
    public void balanceDecreases(String ignored, int amount) {
        int actual = dashboardPage.getCardBalance(fromCard.getId());
        assertEquals(fromBalanceBefore - amount, actual);
    }

    @И("баланс карты {string} увеличивается на {int}")
    public void balanceIncreases(String ignored, int amount) {
        int actual = dashboardPage.getCardBalance(toCard.getId());
        assertEquals(toBalanceBefore + amount, actual);
    }
}
