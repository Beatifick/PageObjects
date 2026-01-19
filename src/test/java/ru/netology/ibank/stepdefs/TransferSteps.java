package ru.netology.ibank.stepdefs;

import io.cucumber.java.ru.*;
import ru.netology.ibank.data.DataHelper;
import ru.netology.ibank.data.DataHelper.CardInfo;
import ru.netology.ibank.page.DashboardPage;
import ru.netology.ibank.page.TransferPage;
import ru.netology.ibank.page.LoginPage;
import ru.netology.ibank.page.VerificationPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TransferSteps {

    private LoginPage loginPage;
    private VerificationPage verificationPage;
    private DashboardPage dashboardPage;
    private TransferPage transferPage;

    private int transferAmount;

    private CardInfo firstCard = DataHelper.getFirstCard();
    private CardInfo secondCard = DataHelper.getSecondCard();



    @Дано("пользователь залогинен с именем {string} и паролем {string}")
    public void login(String login, String password) {
        loginPage = open("http://localhost:9999", LoginPage.class);
        loginPage.login(login, password);
        verificationPage = new VerificationPage();
        verificationPage.verify(DataHelper.getVerificationCode().getCode());
        dashboardPage = new DashboardPage();
    }

    @Когда("пользователь переводит {int} рублей с карты с номером {string} на свою первую карту с главной страницы")
    public void transferToFirstCard(int amount, String fromCardNumber) {
        transferAmount = amount;
        transferPage = dashboardPage.selectCardToDeposit(firstCard);
        transferPage.transfer(amount, fromCardNumber);
    }

    @Тогда("баланс его первой карты из списка на главной странице должен стать {int} рублей")
    public void firstCardBalanceShouldBe(int expectedBalance) {
        int actual = dashboardPage.getCardBalance(firstCard);
        assertEquals(expectedBalance, actual);
    }
}
